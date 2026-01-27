package com.example.eighteighttwod.ui.screen.twoDhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryComponent(){
    HistoryItemCard()
}

@Composable
fun HistoryItemCard(
    date: String="21.01.2025",
    time: String ="11:00",
    twoD: String="21",
    set: String="123456",
    value: String="1234567",
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .border(1.dp, color = Color.Black.copy(3f), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Column() {
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
                            .height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                    }
                    //11:00
                    Row(
                        modifier= Modifier
                            .background(Color.LightGray.copy(0.3f))
                            .fillMaxWidth()
                            .height(45.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = time,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = set,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = value,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = twoD,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                    }
                    //12:01
                    Row(
                        modifier= Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(45.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = time,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = set,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = value,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = twoD,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                    }
                    //3:00
                    Row(
                        modifier= Modifier
                            .background(Color.LightGray.copy(0.3f))
                            .fillMaxWidth()
                            .height(45.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = time,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = set,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = value,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = twoD,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                    }
                    //4:30
                    Row(
                        modifier= Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(45.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = time,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = set,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = value,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                        Text(
                            text = twoD,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(vertical = 3.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
    }
}

@Composable
@Preview
fun HistoryComponentPreview(){
    HistoryComponent()
}
