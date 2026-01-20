package com.example.eighteighttwod.ui.screen.lucky


import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun LuckyScreen(
    viewModel: LuckyViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    val dayList = state.liveData.filter { it?.section =="day" }
    val weekList = state.liveData.filter { it?.section =="week" }

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    if (selectedImageUrl != null) {
        Dialog(
            onDismissRequest = { selectedImageUrl = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { selectedImageUrl = null },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = selectedImageUrl,
                    contentDescription = "Full Screen Image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Day
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                if(dayList.isNotEmpty()){
                    LazyColumn(
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(dayList){item->

                            LuckyItemCard(
                                name = item?.name ?: "--",
                                section = item?.section ?: "--",
                                imgUrl = item?.imgUrl ?: "--",
                                onImageClick = {url -> selectedImageUrl = url},
                                modifier = Modifier
                            )
                        }
                    }
                }else{
                    Text(
                        text = "No Day Items",
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))
            //Week
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Column {

                    if (weekList.isNotEmpty()) {
                        LazyColumn (
                            verticalArrangement = Arrangement.Center
                        ) {
                            items(weekList) { item ->
                                LuckyItemCard(
                                    name = item?.name ?: "--",
                                    section = item?.section ?: "--",
                                    imgUrl = item?.imgUrl ?: "--",
                                    onImageClick = {url -> selectedImageUrl = url},
                                    modifier = Modifier
                                )
                            }
                        }
                    } else {
                        Text("No Week Items", color = Color.Gray)
                    }
                }
            }
            }
        }
    }



@Composable
fun LuckyItemCard(
    name: String,
    section: String,
    imgUrl: String,
    modifier: Modifier = Modifier,
    onImageClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .border(1.dp, color = Color.Black.copy(3f), shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
        ,

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
                    .fillMaxWidth()
                    .height(30.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 8.dp)
                )
            }

            AsyncImage(
                model = imgUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { onImageClick(imgUrl)},

                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_menu_report_image)
            )
        }
    }
}