package com.example.eighteighttwod.ui.screen.twoDhistory



import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eighteighttwod.data.remote.ApiService
import com.example.eighteighttwod.data.remote.dto.TwoDChildDto
import com.example.eighteighttwod.data.remote.dto.TwoDHistoryResponseDto
import com.example.eighteighttwod.data.repository.TwoDHistoryRepositoryImp
import java.time.LocalDate
import java.util.Calendar




@Composable
fun HistoryScreen(
    viewModel: TwoDHistoryViewModel = viewModel(
        factory = TwoDHistoryViewModelFactory(
            repository = TwoDHistoryRepositoryImp(
                api = ApiService.twoDHistoryDApiService
            )
        )
    )
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Box ကိုသုံးမှ Loading ကို List ရဲ့အပေါ် (သို့) အလယ်တည့်တည့်မှာ တင်လို့ရပါမယ်
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // အတွင်းက အရာတွေကို အလယ်ပို့မယ်
    ) {

        // ၁။ Data ပြမယ့် List
        // Loading ဖြစ်နေပေမယ့် List ကို မဖျောက်ဘဲထားတာက User Experience ပိုကောင်းစေပါတယ် (Refresh လုပ်ချိန်မျိုးမှာ)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(
                items = state.twoDList,
                key = { it.id }
            ) { historyItem ->
                HistoryItemCard(historyItem = historyItem)
            }
        }

        // ၂။ Loading ပြမည့်အပိုင်း
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), // Screen အလယ်တည့်တည့်
                color = Color.Blue // လိုချင်သော အရောင်ပြောင်းနိုင်သည်
            )
        }

        // (Optional) အကယ်၍ Error ရှိရင် ပြဖို့အတွက်ပါ ထည့်ထားလို့ရပါတယ်
        if (!state.error.isNullOrEmpty() && !state.isLoading) {
            Text(
                text = state.error!!,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun HistoryChildRow(
    child: TwoDChildDto,
    index: Int // အရောင် အနုအရင့် ခွဲဖို့ index သုံးပါမယ်
) {
    val backgroundColor = if (index % 2 == 0) {
        Color.LightGray.copy(0.3f) // စုံဂဏန်း အကြောင်းဆို မီးခိုးရောင်
    } else {
        Color.White // မ ဂဏန်း အကြောင်းဆို အဖြူရောင်
    }

    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(45.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = child.time,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
        )
        Text(
            text = child.set,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
        )
        Text(
            text = child.value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
        )
        Text(
            text = child.twoD,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
        )
    }
}

@Composable
fun HistoryItemCard(
    historyItem: TwoDHistoryResponseDto // Data လက်ခံမည့် parameter
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // vertical padding နည်းနည်းထပ်ထည့်
            .border(1.dp, color = Color.Black.copy(0.1f), shape = RoundedCornerShape(12.dp)), // color copy value adjusted
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // နေ့စွဲ (Header)
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
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = historyItem.date, // Data ထဲက Date ကို ယူသုံးထားသည်
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
                )
            }

            // Child List ကို Loop ပတ်ပြီး Row ထုတ်ခြင်း
            historyItem.child.forEachIndexed { index, childDto ->
                HistoryChildRow(child = childDto, index = index)
            }
        }
    }
}
//
//@Composable
//fun HistoryItemCard() {
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp)
//            .border(1.dp, color = Color.Black.copy(3f), shape = RoundedCornerShape(12.dp)),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(
//            modifier = Modifier,
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Column() {
//                Box(
//                    modifier = Modifier
//                        .background(Color(0xFFD4E0FC))
//                        .drawBehind{
//                            val strokeWidth =1.dp.toPx()
//                            val borderColor = Color.Black
//
//                            drawLine(
//                                color = borderColor,
//                                start = Offset(0f,size.height),
//                                end = Offset(size.width,size.height),
//                                strokeWidth = strokeWidth
//                            )
//                        }
//                        .fillMaxWidth()
//                        .height(60.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = date,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                }
//                //11:00
//                Row(
//                    modifier= Modifier
//                        .background(Color.LightGray.copy(0.3f))
//                        .fillMaxWidth()
//                        .height(45.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    Text(
//                        text = time,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = set,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = value,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = twoD,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                }
//                //12:01
//                Row(
//                    modifier= Modifier
//                        .background(Color.White)
//                        .fillMaxWidth()
//                        .height(45.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    Text(
//                        text = time,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = set,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = value,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = twoD,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                }
//                //3:00
//                Row(
//                    modifier= Modifier
//                        .background(Color.LightGray.copy(0.3f))
//                        .fillMaxWidth()
//                        .height(45.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    Text(
//                        text = time,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = set,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = value,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = twoD,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                }
//                //4:30
//                Row(
//                    modifier= Modifier
//                        .background(Color.White)
//                        .fillMaxWidth()
//                        .height(45.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    Text(
//                        text = time,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = set,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = value,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                    Text(
//                        text = twoD,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier
//                            .padding(vertical = 3.dp, horizontal = 8.dp)
//                    )
//                }
//            }
//        }
//    }
//}

















//@RequiresApi(Build.VERSION_CODES.O)
//@SuppressLint("UnrememberedMutableState")
//@Composable
//fun HistoryScreen(
//    viewModel: TwoDHistoryViewModel = viewModel(
//        factory = TwoDHistoryViewModelFactory(
//            repository = TwoDHistoryRepositoryImp(
//                api = ApiService.twoDHistoryDApiService
//            )
//        )
//    )
//) {
//    val state by viewModel.state.collectAsStateWithLifecycle()
//    val selectedYear by viewModel.selectedYear.collectAsStateWithLifecycle()
//    val selectedMonth by viewModel.selectedMonth.collectAsStateWithLifecycle()
//
//    val monthYearString by derivedStateOf {
//        viewModel.getCurrentMonthYearString(selectedMonth, selectedYear)
//    }
//
//    val calendarData by derivedStateOf {
//        viewModel.getCalendarData(selectedMonth, selectedYear)
//    }
//
//    if (state.isLoading) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//        return
//    }
//
//    state.error?.let { error ->
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text(text = "Error: $error", color = Color.Red, fontSize = 18.sp)
//        }
//        return
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Header - Month/Year with arrows
//        Row(
//
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { viewModel.changeMonth(-1) }) {
//                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
//            }
//
//            Text(
//                text = monthYearString,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            IconButton(onClick = { viewModel.changeMonth(1) }) {
//                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Days of week header
//        Row(modifier = Modifier.fillMaxWidth()) {
//            listOf("MON", "TUE", "WED", "THU", "FRI").forEach { dayName ->
//                Text(
//                    text = dayName,
//                    modifier = Modifier.weight(1f),
//                    textAlign = TextAlign.Center,
//                    color = Color.Gray,
//                    fontSize = 14.sp
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Calendar grid - MON to FRI only (Sat & Sun ရက်တွေ လုံးဝ မပါတော့ဘူး)
//        val calendar = Calendar.getInstance().apply {
//            set(Calendar.YEAR, selectedYear)
//            set(Calendar.MONTH, selectedMonth - 1)
//            set(Calendar.DAY_OF_MONTH, 1)
//        }
//
//        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//
//        // တနင်္လာ ကနေ သောကြာ ပဲ ရှိတဲ့ ရက်တွေ စုမယ်
//        val weekdayDays = mutableListOf<Int>()
//        for (d in 1..maxDays) {
//            calendar.set(Calendar.DAY_OF_MONTH, d)
//            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
//                weekdayDays.add(d)
//            }
//        }
//
//        // ပထမ ရက်ရဲ့ weekday ရှာ (တနင်္လာ မတိုင်ခင် empty cells ဘယ်နှစ်ခု လိုမလဲ)
//        calendar.set(Calendar.DAY_OF_MONTH, 1)
//        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//        val initialEmptyCells = when (firstDayOfWeek) {
//            Calendar.MONDAY -> 0
//            Calendar.TUESDAY -> 1
//            Calendar.WEDNESDAY -> 2
//            Calendar.THURSDAY -> 3
//            Calendar.FRIDAY -> 4
//            Calendar.SATURDAY -> 5  // စနေ ဆို တနင်္လာ မတိုင်ခင် 5 empty
//            Calendar.SUNDAY -> 6    // တနင်္ဂနွေ ဆို 6 empty
//            else -> 0
//        }
//
//        val totalCells = weekdayDays.size + initialEmptyCells
//        val rows = (totalCells + 4) / 5  // 5 columns (MON-FRI)
//
//        Column {
//            for (rowIndex in 0 until rows) {
//                Row(modifier = Modifier.fillMaxWidth()) {
//                    for (colIndex in 0 until 5) {
//                        val cellIndex = rowIndex * 5 + colIndex
//
//                        val day = if (cellIndex < initialEmptyCells) {
//                            0  // empty cell
//                        } else {
//                            val weekdaysIndex = cellIndex - initialEmptyCells
//                            if (weekdaysIndex < weekdayDays.size) {
//                                weekdayDays[weekdaysIndex]
//                            } else {
//                                0  // နောက်ဆုံး row မှာ မလုံလောက်ရင် empty
//                            }
//                        }
//
//                        if (day > 0) {
//                            val data = calendarData[day]
//                            CalendarDayItem(
//                                day = day,
//                                data = data,
//                                selectedMonth = selectedMonth,
//                                selectedYear = selectedYear
//                            )
//                        } else {
//                            Box(modifier = Modifier.weight(1f))  // empty space
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun RowScope.CalendarDayItem(day: Int, data: Pair<String, String>?, selectedMonth: Int, selectedYear: Int) {
//    val currentDate = LocalDate.now()
//    val isFutureDay = remember(selectedYear, selectedMonth, day) {
//        val thisDayDate = LocalDate.of(selectedYear, selectedMonth, day)
//        thisDayDate.isAfter(currentDate)
//    }
//
//    val backgroundColor = when {
//        data != null && data.first.isNotEmpty() -> Color.Black
//        isFutureDay -> Color(0xFFF5F5F5)
//        else -> Color(0xFFEEEEEE)
//    }
//
//    Box(
//        modifier = Modifier
//            .weight(1f)
//            .aspectRatio(0.8f)
//            .padding(6.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(RoundedCornerShape(12.dp))
//                .background(backgroundColor)
//                .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
//        )
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Text(
//                text = day.toString(),
//                color = when {
//                    data != null && data.first.isNotEmpty() -> Color.White
//                    isFutureDay -> Color.Gray.copy(alpha = 0.6f)
//                    else -> Color.Gray
//                },
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium
//            )
//
//            if (data == null) {
//                if (!isFutureDay) {
//                    Text("X", color = Color.Red, fontSize = 40.sp, fontWeight = FontWeight.Bold)
//                } else {
//                    Spacer(modifier = Modifier.height(40.dp))
//                }
//            } else {
//                val (twelveOClock, fourOClock) = data
//                Text(
//                    text = twelveOClock.ifEmpty { "--" },
//                    color = Color.White,
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = fourOClock.ifEmpty { "--" },
//                    color = Color.White.copy(alpha = 0.9f),
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//        }
//    }
//}
//
