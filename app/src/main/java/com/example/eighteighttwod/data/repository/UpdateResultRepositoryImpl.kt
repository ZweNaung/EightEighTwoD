package com.example.eighteighttwod.data.repository

import android.util.Log
import com.example.eighteighttwod.data.remote.api.UpdateResultApiService
import com.example.eighteighttwod.utils.Resource
import com.example.eighteighttwod.data.remote.dto.UpdateResultDto
import io.socket.client.Socket
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import kotlinx.coroutines.launch

class UpdateResultRepositoryImpl @Inject constructor(
    private val socket: Socket,
    private val json: Json,
    private val apiService: UpdateResultApiService
): UpdateResultRepository {

    override fun getTodayResults(): Flow<Resource<List<UpdateResultDto>>> = callbackFlow {
        // ၁။ Loading State အရင်ပို့မယ်
        trySend(Resource.Loading())

        // Data သိမ်းထားမယ့် ယာယီ List (Update ဖြစ်တိုင်း ဒါကိုပဲ ပြန်ပို့မယ်)
        val currentList = ArrayList<UpdateResultDto>()

        // ⭐ Function သပ်သပ်ထုတ်လိုက်တယ် (API ခေါ်ပြီး List update လုပ်ဖို့)
        // Socket ပြန်ချိတ်တိုင်း ဒါကိုခေါ်ပေးရင် Data လွတ်သွားတာ မရှိတော့ဘူး
        suspend fun fetchLatestData() {
            try {
                Log.d("UpdateRepo", "🔄 Fetching latest data from API...")
                val response = apiService.getTodayResults()

                // Response success ဖြစ်ပြီး Data ပါလာရင် List ကို Update လုပ်မယ်
                if (response.success && response.data != null) {
                    currentList.clear()
                    currentList.addAll(response.data)

                    // UI ကို List အသစ်ပို့မယ်
                    trySend(Resource.Success(currentList.toList()))
                    Log.d("UpdateRepo", "✅ API Data Synced: ${currentList.size} items")
                }
            } catch (e: Exception) {
                // API error တက်ရင် Log ထုတ်မယ် (Socket က ဆက်အလုပ်လုပ်နေမှာမို့ Error state မပို့တော့ဘူး)
                Log.e("UpdateRepo", "⚠️ API Fetch Failed: ${e.message}")
            }
        }

        // ၂။ Flow စစချင်းမှာ API ကို အရင်ဆုံး လှမ်းခေါ်မယ် (App ဖွင့်တာနဲ့ Data မြင်ရအောင်)
        fetchLatestData()

        // ၃။ Socket မချိတ်ရသေးရင် ချိတ်မယ်
        if (!socket.connected()) {
            socket.connect()
        }

        // ၄။ Socket Connected ဖြစ်တိုင်း (စချိတ်ချိန် + ပြုတ်ပြီးပြန်ချိတ်ချိန်) API ပြန်ခေါ်မယ်
        socket.on(Socket.EVENT_CONNECT) {
            Log.d("SocketCheck", "✅ Connected / Reconnected to Server!")
            // Callback အထဲကနေ Suspend function ခေါ်ဖို့ launch သုံးရတယ်
            launch {
                fetchLatestData()
            }
        }

        // ၅။ Error တက်ရင် Log ထုတ်မယ်
        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.e("SocketCheck", "❌ Connection Error: ${args.firstOrNull()}")
        }

        // ၆။ Real-time Data ဝင်လာရင် UI update မယ်
        socket.on("new_2d_result") { args ->
            if (args.isNotEmpty()) {
                val dataString = args[0].toString()
                try {
                    // Socket ကလာတဲ့ Object ကို Parse လုပ်မယ်
                    val newDto = json.decodeFromString<UpdateResultDto>(dataString)

                    // List ထဲမှာ Session တူတာရှိရင် Update လုပ်၊ မရှိရင် အသစ်ထည့်
                    val existingIndex = currentList.indexOfFirst { it.session == newDto.session }

                    if (existingIndex != -1) {
                        currentList[existingIndex] = newDto
                    } else {
                        currentList.add(newDto)
                    }

                    // UI ကို Update ဖြစ်သွားတဲ့ List ပြန်ပို့မယ်
                    trySend(Resource.Success(currentList.toList()))
                    Log.d("SocketCheck", "⚡ Realtime Update: ${newDto.twoD}")

                } catch (e: Exception) {
                    Log.e("SocketCheck", "Parsing Error", e)
                }
            }
        }

        socket.on("daily_clear_event") {
            Log.d("UpdateRepo", "🧹 Received daily_clear_event from Server")

            // ၁။ List ထဲက Data အကုန်ဖျက်မယ်
            currentList.clear()

            // ၂။ UI ကို List အလွတ် (Empty List) ပို့လိုက်မယ်
            // ViewModel က ဒါကိုမြင်တာနဲ့ Screen ပေါ်က List ကို ရှင်းပစ်လိုက်ပါလိမ့်မယ်
            trySend(Resource.Success(currentList.toList()))
        }



        // ၇။ Flow ပြီးဆုံးသွားရင် (ViewModel cleared) Socket Listener တွေ ဖြုတ်မယ်
        awaitClose {
            socket.off("new_2d_result")
            socket.off(Socket.EVENT_CONNECT)
            socket.off(Socket.EVENT_CONNECT_ERROR)
        }
    }
}