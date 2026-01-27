package com.example.eighteighttwod.ui.screen.dream
import android.R
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun DreamScreen() {
    val context = LocalContext.current
    // ပုံနာမည်တွေကို သိမ်းထားမယ့် List State
    var imageFiles by remember { mutableStateOf<List<String>>(emptyList()) }

    // Screen စပေါ်တာနဲ့ assets/dream ထဲက ဖိုင်တွေကို ဖတ်မယ်
    LaunchedEffect(Unit) {
        imageFiles = getImagesFromAssets(context, "dream")
    }

    // GridView (2 columns)
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
        columns = GridCells.Fixed(3), // တစ်တန်းမှာ ၂ ပုံ ပြမယ် (လိုသလို ပြင်ပါ)
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imageFiles) { fileName ->
            // ဖိုင်နာမည်မှာ extension (.jpg, .webp) တွေ ဖြုတ်ပြီး နာမည်သန့်သန့်ယူမယ်
            val nameWithoutExt = fileName.substringBeforeLast(".") // "007_508_ကျားခေါင်း" ရမယ်

// Underscore (_) တွေ့တိုင်း ခွဲမယ်၊ ဒါပေမယ့် ရှေ့ဆုံး ၂ ခု (007 နဲ့ 508) ကိုပဲ နံပါတ်ယူမှာမို့ limit = 3 ထားမယ်
            val parts = nameWithoutExt.split("_", limit = 3)

            var srNumber=""
            var number = ""
            var title = ""

            if (parts.size >= 3) {
                // parts[0] = "007"
                // parts[1] = "508"
                // parts[2] = "ကျားခေါင်း"

                // လိုချင်တဲ့ Number format (007_508)
                srNumber = parts[0]
                number = parts[1]

                // လိုချင်တဲ့ Title format (000_000_ကျားခေါင်း)
                // ရှေ့က နံပါတ်အစစ်ကို မယူဘဲ 000_000 နဲ့ အစားထိုးချင်တယ်ဆိုရင် -
                title = parts[2]

                // မှတ်ချက်။ ။ တကယ်လို့ နာမည်သန့်သန့် "ကျားခေါင်း" ပဲ လိုချင်ရင်တော့ title = parts[2] လို့ရေးပါ။
            } else {
                // Format မကိုက်တဲ့ ဖိုင်တွေပါလာရင် Error မတက်အောင် မူရင်းအတိုင်းပြမယ်
                title = nameWithoutExt
                number = "-"
            }

            // Path က file:///android_asset/dream/filename.jpg ဖြစ်ရမယ်
            val fullPath = "file:///android_asset/dream/$fileName"

            // သင်လိုချင်တဲ့ Card ဒီဇိုင်း
//            DreamImageCard(
//                imagePath = fullPath,
//                name = nameDisplay
//            )

            OmenItemCard(
                title = title,
                imgUrl = fullPath,
                srNumber = srNumber,
                number = number,
                modifier = Modifier
            )
        }
    }
}

// Assets ထဲက ပုံတွေကို ဖတ်ပေးမယ့် Function
fun getImagesFromAssets(context: Context, folder: String): List<String> {
    return try {
        // folder အောက်က ရှိသမျှဖိုင်တွေကို ယူမယ်၊ ပြီးရင် image extension တွေကိုပဲ စစ်ယူမယ်
        context.assets.list(folder)?.filter { file ->
            file.endsWith(".jpg") || file.endsWith(".jpeg") ||
                    file.endsWith(".png") || file.endsWith(".webp")
        } ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}

// ဒီနေရာမှာ သင့်ရဲ့ Card Design ကို အစားထိုးနိုင်ပါတယ်
@Composable
fun DreamImageCard(imagePath: String, name: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Card အမြင့်
    ) {
        Column {
            // ပုံပြမယ့်နေရာ
            AsyncImage(
                model = imagePath,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f) // နေရာအပြည့်ယူမယ်
                    .fillMaxWidth()
            )

            // စာပြမယ့်နေရာ
            Text(
                text = name,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun OmenItemCard(
    title: String,
    srNumber: String,
    number: String,
    imgUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, color = Color.Black.copy(3f), shape = RoundedCornerShape(12.dp))
            .height(200.dp)
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
                    .drawBehind{
                        val strokeWidth =1.dp.toPx()
                        val borderColor = Color.Black

                        drawLine(
                            color = borderColor,
                            start = Offset(0f,size.height),
                            end = Offset(size.width,size.height),
                            strokeWidth = strokeWidth
                        )
                    }
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 8.dp),
                    maxLines = 1
                )
            }

            AsyncImage(
                model = imgUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_menu_report_image)
            )

            HorizontalDivider(
                color = Color.Black.copy(0.8f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                text = srNumber,
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(8.dp)
            )
                Text(
                    text = number,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Red.copy(0.8f),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}