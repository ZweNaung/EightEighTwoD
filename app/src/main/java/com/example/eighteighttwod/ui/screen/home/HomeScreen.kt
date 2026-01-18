package com.example.eighteighttwod.ui.screen.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen(
    liveViewModel: LiveViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val headerHeight = screenHeight * 0.25f


    val liveState by liveViewModel.state.collectAsStateWithLifecycle()
    val liveData = liveState.liveData

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            //First Row CircleBg & Live
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {
                CircleBoxConstraint(
                    modifier = Modifier.fillMaxSize()
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Live 2D
                    Box(
                        modifier = Modifier
                            .weight(0.2f)
                            .padding(start = 50.dp, top = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = liveData?.twoD ?: "--",
                                fontSize = 100.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold,
                            )
                            Text(
                                text = "Time : ${liveData?.updatedAt?.toFormattedTime() ?: "--"}")
                        }
                    }
                    //Live Set & Value
                    Box(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(start = 20.dp, top = 10.dp, bottom = 5.dp, end = 10.dp)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "Set",
                                fontSize = 24.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = liveData?.set ?: "--",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Normal
                            )
                            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                            Text(
                                "Value",
                                fontSize = 24.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = liveData?.value ?: "--",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            //12:00 and 4:30
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //12:00
                Box(
                    modifier = Modifier.weight(0.1f)
                ){
                    NumberCardWidget(
                        time = "12:01 AM",
                        towD = "28",
                        set = "567268",
                        value = "9766145",
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                //4:30
                Box(
                    modifier = Modifier.weight(0.1f)
                ){
                    NumberCardWidget(
                        time = "4:30 PM",
                        towD = "08",
                        set = "490838",
                        value = "953245",
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            //ads
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.height(10.dp))
            //Modern and internet
            TableCardDesign(
                nineModern = "45",
                nineInternet = "16",
                twoModern = "83",
                twoInternet = "23"
            )
        }

    }
}



//Modern and Internet
@Composable
fun TableCardDesign(
    nineModern: String,
    nineInternet: String,
    twoModern: String,
    twoInternet: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = Color.Gray.copy(alpha = 0.5f)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFD4E0FC) // အပြာနုရောင် Background
            )
        ) {
            Column(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 24.dp)
            ) {
                // --- Header Row (Modern | Internet) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    // Modern Text
                    Text(
                        text = "Modern",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    // Internet Text
                    Text(
                        text = "Internet",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp, bottom = 6.dp),
                    thickness = 2.dp,
                    color = Color.Black
                )

                // --- Row 1 (9:30) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "9:30",
                        color = Color(0xFF444444),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = nineModern,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = nineInternet,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }

                // ကြားခံ မျဉ်းပါးပါးလေး
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 6.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f)
                )

                // --- Row 2 (2:00) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "2:00",
                        color = Color(0xFF444444),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = twoModern,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = twoInternet,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}


//hh:mm:ss
@RequiresApi(Build.VERSION_CODES.O)
fun String.toFormattedTime(): String {
    return try {
        // 1. String ကို UTC အချိန်အဖြစ် ဖတ်မယ်
        val instant = Instant.parse(this)

        // 2. Format သတ်မှတ်မယ် (hh = 12နာရီ, HH = 24နာရီ, a = AM/PM)
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH)
            .withZone(ZoneId.systemDefault()) // ဖုန်းရဲ့ Local Time (Myanmar Time) ကို ယူမယ်

        formatter.format(instant)
    } catch (e: Exception) {
        "Unknown Time" // Error တက်ရင် ပြမယ့်စာ
    }
}

//12:00 and 4:30
@Composable
fun NumberCardWidget(
    time: String,
    towD: String,
    set: String,
    value: String
) {
    val mintGreen = Color(0xFFAFEFEB)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Column {
            // -------------------------------------------------
            // 1. Header Section (Time)
            // -------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 8.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // -------------------------------------------------
            // 2. Middle Section (Big Number)
            // -------------------------------------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mintGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = towD,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // -------------------------------------------------
            // 3. Footer Section (Set & Value Rows combined here)
            // -------------------------------------------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp,)
            ) {
                // First Row: Set
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Set",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = set,
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        letterSpacing = 1.sp
                    )
                }

                // Second Row: Value
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Value",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = value,
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}




//Live Background
@Composable
fun CircleBg(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFFAFEFEB))
    )
}

@Composable
fun CircleBoxConstraint(
    modifier: Modifier= Modifier
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val circleWidth = maxWidth * 1f
        val circleHeight = maxHeight * 0.30f

        CircleBg(
            modifier = Modifier
                .width(circleWidth)
                .fillMaxHeight()
                .align(Alignment.TopStart)
                .offset(
                    x = -circleWidth * 0.35f,
                )
                .shadow(10.dp, CircleShape)
        )

    }
}
