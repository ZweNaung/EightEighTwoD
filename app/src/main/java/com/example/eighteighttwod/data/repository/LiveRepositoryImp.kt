package com.example.eighteighttwod.data.repository
import android.util.Log
import com.example.eighteighttwod.data.remote.api.LiveApiService
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
    override fun getRealTimeLiveData(): Flow<Resource<LiveDataDto>> = callbackFlow{

        trySend(Resource.Loading())


        if(!socket.connected()){
            socket.connect()
        }

        socket.on(Socket.EVENT_CONNECT) {
            Log.d("SocketCheck", "✅ Connected to Server!")
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            // Error တက်ရင် ဘာကြောင့်လဲ သိရအောင်
            Log.d("SocketCheck", "❌ Connection Error: ${args.firstOrNull()}")
        }

        socket.on("live_2d_data"){args ->
            if (args.isNotEmpty()){
                    val dataString = args[0].toString()
                val rawData = args[0].toString()
                Log.d("SocketCheck", "📩 Raw Data Received: $rawData") // ဒီမှာ Data ပေါ်လာရမယ်

                try {
                    val dto = json.decodeFromString<LiveDataDto>(dataString)
                    Log.d("SocketCheck", "✅ Parsing Success: ${dto.twoD}") // Parsing အောင်မြင်လား
                    trySend(Resource.Success(dto))

                }catch (e: Exception) {
                    Log.d("SocketCheck", "⚠️ Parsing Error: ${e.message}")
                    trySend(Resource.Error(message = "Parsing Error: ${e.message}"))
                }
            }else {
                Log.d("SocketCheck", "⚠️ Received empty args")
            }

        }

        if (!socket.connected()) {
            Log.d("SocketCheck", "🔄 Connecting...")
            socket.connect()
        }


        awaitClose {
            socket.off("live_2d_data") // Listener ဖြုတ်မယ်
            // socket.disconnect() // App တစ်ခုလုံးမှာ Socket တစ်ခုတည်းသုံးရင် disconnect မလုပ်တာ ပိုကောင်းတယ်
        }
    }
}