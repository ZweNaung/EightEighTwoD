package com.example.eighteighttwod.ui.screen.omen

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage

@Composable
fun OmenScreen(
    viewModel: OmenViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Error ရှိရင် အရင်ပြမယ်
        if (state.error != null) {
            Text(
                text = state.error!!,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        // 2. Loading ဖြစ်နေရင် ပြမယ် (Error မရှိမှ)
        else if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        // 3. Data ရှိရင် ပြမယ်
        else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.liveData) { omen ->
                    OmenItemCard(
                        name = omen?.name ?: "--",
                        imgUrl = omen?.imgUrl ?: "",
                        onClick = {
                            selectedImageUrl = omen?.imgUrl
                        }
                    )
                }
            }
        }
    }
    if (selectedImageUrl != null) {
        OmenFullScreenDialog(
            imageUrl = selectedImageUrl!!,
            onDismiss = { selectedImageUrl = null } // ပိတ်ရင် null ပြန်လုပ်မယ်
        )
    }
}


//fullScreenDialog
@Composable
fun OmenFullScreenDialog(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    // Zoom အတိုးအလျှော့ (Scale) နဲ့ နေရာရွှေ့တာ (Offset) အတွက် State တွေ
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Screen အပြည့်
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                // နောက်ခံ အမည်းနေရာလွတ်ကို နှိပ်ရင် ပိတ်မယ် (Optional)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { onDismiss() })
                }
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    // ၁. Zoom နဲ့ Pan အလုပ်လုပ်ဖို့ (GraphicsLayer)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    // ၂. လက်ချောင်း အထိအတွေ့ (Gestures) ကို ဖမ်းမယ်
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            // Zoom ဆွဲတဲ့အခါ ၁ ဆ ထက် မငယ်သွားအောင် တားမယ် (maxOf)
                            // ၃ ဆ ထက် မကြီးအောင် တားမယ် (minOf - လိုရင်ထည့်ပါ)
                            scale = maxOf(1f, scale * zoom)

                            // Zoom ချဲ့ထားမှ ပုံကို ဘယ်ညာ ရွှေ့ခွင့်ပေးမယ်
                            if (scale > 1f) {
                                val newOffset = offset + pan
                                // (ဒီနေရာမှာ Boundary check ထည့်လို့ရတယ်၊ လောလောဆယ် ရိုးရိုးထားပေးမယ်)
                                offset = newOffset
                            } else {
                                offset = Offset.Zero // ပုံသေးသွားရင် အလယ်ပြန်ပို့မယ်
                            }
                        }
                    }
            )

            // Close Button
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

//Show Card
@Composable
fun OmenItemCard(
    name: String,
    imgUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, color = Color.Black.copy(0.3f), shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFD4E0FC))
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val borderColor = Color.Black

                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                    }
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 8.dp)
                )
            }
            SubcomposeAsyncImage(
                model = imgUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            ) {
                // 👇 SubcomposeAsyncImage အထဲရောက်မှ painter ကို သိတာပါ
                val state = painter.state

                when (state) {
                    // ၁။ Loading
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFF0F0F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp),
                                color = Color.Gray,
                                strokeWidth = 3.dp
                            )
                        }
                    }

                    // ၂။ Error
                    is AsyncImagePainter.State.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFEEEEEE)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_report_image),
                                contentDescription = "Error",
                                tint = Color.Red,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "ပုံမပေါ်လျှင်\nVPN ဖွင့်သုံးပေးပါ။",
                                textAlign = TextAlign.Center,
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // ၃။ Success
                    else -> {
                        Image(
                            painter = painter, // 👈 ဒီမှာ imgUrl မဟုတ်ဘဲ painter ကို ထည့်ရပါတယ်
                            contentDescription = name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
