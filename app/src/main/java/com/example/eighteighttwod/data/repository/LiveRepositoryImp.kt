package com.example.eighteighttwod.data.repository
import android.util.Log
import com.example.eighteighttwod.data.remote.dto.LiveDataDto
import com.example.eighteighttwod.utils.Resource
import io.socket.client.Socket
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LiveRepositoryImp @Inject constructor(
    private val socket: Socket,
    private val json: Json
): LiveRepository {
    override fun getRealTimeLiveData(): Flow<Resource<LiveDataDto>> = callbackFlow {

        var lastTwoD: String? = null

        // Flow စစချင်း Loading ပြမယ်
        trySend(Resource.Loading())

        // ၁။ Socket မချိတ်ရသေးရင် ချိတ်မယ်
        if (!socket.connected()) {
            socket.connect()
        } else {
            // ချိတ်ပြီးသားဆိုရင် Connected ဖြစ်ကြောင်း log ထုတ်မယ် (Optional)
            Log.d("SocketCheck", "✅ Already Connected")
        }

        // ၂။ Connection အောင်မြင်တဲ့အခါ
        socket.on(Socket.EVENT_CONNECT) {
            Log.d("SocketCheck", "✅ Connected to Server!")
            // Server ဘက်က ပထမဆုံး connect မှာ data မပို့ရင် ဒီကနေ တောင်းလို့ရတယ် (Optional)
        }

        // ၃။ Connection ပြတ်တောက်သွားတဲ့အခါ (အရေးကြီးတယ်)
        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d("SocketCheck", "❌ Disconnected from Server")
            // UI ကို Loading အခြေအနေ ပြန်ပြောင်းမယ်၊ ဒါမှ User သိမယ်
            trySend(Resource.Loading())
            lastTwoD = null // Reset လုပ်လိုက်မယ်
        }

        // ၄။ Connection Error တက်တဲ့အခါ
        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.d("SocketCheck", "❌ Connection Error: ${args.firstOrNull()}")
            // Error အခြေအနေပို့မယ် (သို့) Loading ပြထားမယ်
            trySend(Resource.Error(message = "Connection Error... Reconnecting"))
        }

        // ၅။ Data ဝင်လာတဲ့အခါ
        socket.on("live_2d_data") { args ->
            if (args.isEmpty()) return@on

            val rawData = args[0].toString()
            // Log.d("SocketCheck", "📩 Raw Data: $rawData")

            try {
                val dto = json.decodeFromString<LiveDataDto>(rawData)

                // ⭐ SAME DATA FILTER
                // Data တူနေရင် Skip မယ်၊ ဒါပေမယ့် ပထမဆုံးအကြိမ်ဆိုရင်တော့ ပို့မယ်
                if (dto.twoD == lastTwoD) {
                    return@on
                }

                lastTwoD = dto.twoD
                Log.d("SocketCheck", "✅ New 2D Update: ${dto.twoD}")

                // Success Data ပို့မယ်
                trySend(Resource.Success(dto))

            } catch (e: Exception) {
                Log.d("SocketCheck", "⚠️ Parsing Error: ${e.message}")
                // Parsing Error တက်ရင် အရင် Data အတိုင်းထားတာ ပိုကောင်းတယ်၊ Error မပို့တော့ဘူး
            }
        }

        socket.on("daily_clear_event") {
            Log.d("SocketCheck", "🧹 Received daily_clear_event")
            val resetDto = LiveDataDto(
                set = "0.00",
                value = "0.00",
                twoD = "--",
                updatedAt = System.currentTimeMillis()
            )
            lastTwoD = "--"
            trySend(Resource.Success(resetDto))
        }

        awaitClose {
            // Flow ပိတ်ရင် Listener တွေဖြုတ်မယ်
            // Socket ကိုတော့ disconnect မလုပ်ဘူး (Singleton မို့လို့ နောက် screen တွေမှာ သုံးလို့ရအောင်)
            socket.off("live_2d_data")
            socket.off("daily_clear_event")
            socket.off(Socket.EVENT_CONNECT)
            socket.off(Socket.EVENT_DISCONNECT)
            socket.off(Socket.EVENT_CONNECT_ERROR)
        }
    }
}


//ocket.on("live_2d_data"){args ->
//            if (args.isNotEmpty()){
//                    val dataString = args[0].toString()
//                val rawData = args[0].toString()
//                Log.d("SocketCheck", "📩 Raw Data Received: $rawData") // ဒီမှာ Data ပေါ်လာရမယ်
//
//                try {
//                    val dto = json.decodeFromString<LiveDataDto>(dataString)
//                    Log.d("SocketCheck", "✅ Parsing Success: ${dto.twoD}") // Parsing အောင်မြင်လား
//                    trySend(Resource.Success(dto))
//
//                }catch (e: Exception) {
//                    Log.d("SocketCheck", "⚠️ Parsing Error: ${e.message}")
//                    trySend(Resource.Error(message = "Parsing Error: ${e.message}"))
//                }
//            }else {
//                Log.d("SocketCheck", "⚠️ Received empty args")
//            }
//
//        }

//        socket.on("daily_clear_event") {
//            Log.d("SocketCheck", "🧹 Received daily_clear_event from Server")
//
//            // Server က ဖျက်လိုက်ပြီလို့ ပြောတာနဲ့
//            // App ဘက်က UI မှာပြဖို့ "Reset Data" (အလွတ်) တစ်ခု ဖန်တီးပြီး ပို့လိုက်မယ်
//            val resetDto = LiveDataDto(
//                set = "0.00",
//                value = "0.00",
//                twoD = "--",
//                updatedAt = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
//            )
//
//            // ViewModel ဆီကို ပို့မယ် (UI မှာ 0.00 နဲ့ -- ဖြစ်သွားမယ်)
//            trySend(Resource.Success(resetDto))
//        }


