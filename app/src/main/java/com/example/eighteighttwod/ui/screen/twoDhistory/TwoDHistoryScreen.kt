package com.example.eighteighttwod.ui.screen.twoDhistory



import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eighteighttwod.data.remote.ApiService
import com.example.eighteighttwod.data.repository.TwoDHistoryRepositoryImp
import java.time.LocalDate
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun HistoryScreen(
    viewModel: TwoDHistoryViewModel = viewModel(
        factory = TwoDHistoryViewModelFactory(
            repository = TwoDHistoryRepositoryImp(
                api = ApiService.twoDHistoryDApiService
            )
        )
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedYear by viewModel.selectedYear.collectAsStateWithLifecycle()
    val selectedMonth by viewModel.selectedMonth.collectAsStateWithLifecycle()

    val monthYearString by derivedStateOf {
        viewModel.getCurrentMonthYearString(selectedMonth, selectedYear)
    }

    val calendarData by derivedStateOf {
        viewModel.getCalendarData(selectedMonth, selectedYear)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    state.error?.let { error ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error: $error", color = Color.Red, fontSize = 18.sp)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header - Month/Year with arrows
        Row(

            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.changeMonth(-1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
            }

            Text(
                text = monthYearString,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = { viewModel.changeMonth(1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Days of week header
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("MON", "TUE", "WED", "THU", "FRI").forEach { dayName ->
                Text(
                    text = dayName,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar grid - MON to FRI only (Sat & Sun ရက်တွေ လုံးဝ မပါတော့ဘူး)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonth - 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // တနင်္လာ ကနေ သောကြာ ပဲ ရှိတဲ့ ရက်တွေ စုမယ်
        val weekdayDays = mutableListOf<Int>()
        for (d in 1..maxDays) {
            calendar.set(Calendar.DAY_OF_MONTH, d)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                weekdayDays.add(d)
            }
        }

        // ပထမ ရက်ရဲ့ weekday ရှာ (တနင်္လာ မတိုင်ခင် empty cells ဘယ်နှစ်ခု လိုမလဲ)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val initialEmptyCells = when (firstDayOfWeek) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5  // စနေ ဆို တနင်္လာ မတိုင်ခင် 5 empty
            Calendar.SUNDAY -> 6    // တနင်္ဂနွေ ဆို 6 empty
            else -> 0
        }

        val totalCells = weekdayDays.size + initialEmptyCells
        val rows = (totalCells + 4) / 5  // 5 columns (MON-FRI)

        Column {
            for (rowIndex in 0 until rows) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (colIndex in 0 until 5) {
                        val cellIndex = rowIndex * 5 + colIndex

                        val day = if (cellIndex < initialEmptyCells) {
                            0  // empty cell
                        } else {
                            val weekdaysIndex = cellIndex - initialEmptyCells
                            if (weekdaysIndex < weekdayDays.size) {
                                weekdayDays[weekdaysIndex]
                            } else {
                                0  // နောက်ဆုံး row မှာ မလုံလောက်ရင် empty
                            }
                        }

                        if (day > 0) {
                            val data = calendarData[day]
                            CalendarDayItem(
                                day = day,
                                data = data,
                                selectedMonth = selectedMonth,
                                selectedYear = selectedYear
                            )
                        } else {
                            Box(modifier = Modifier.weight(1f))  // empty space
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RowScope.CalendarDayItem(day: Int, data: Pair<String, String>?, selectedMonth: Int, selectedYear: Int) {
    val currentDate = LocalDate.now()
    val isFutureDay = remember(selectedYear, selectedMonth, day) {
        val thisDayDate = LocalDate.of(selectedYear, selectedMonth, day)
        thisDayDate.isAfter(currentDate)
    }

    val backgroundColor = when {
        data != null && data.first.isNotEmpty() -> Color.Black
        isFutureDay -> Color(0xFFF5F5F5)
        else -> Color(0xFFEEEEEE)
    }

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(0.8f)
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = day.toString(),
                color = when {
                    data != null && data.first.isNotEmpty() -> Color.White
                    isFutureDay -> Color.Gray.copy(alpha = 0.6f)
                    else -> Color.Gray
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            if (data == null) {
                if (!isFutureDay) {
                    Text("X", color = Color.Red, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                } else {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            } else {
                val (twelveOClock, fourOClock) = data
                Text(
                    text = twelveOClock.ifEmpty { "--" },
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = fourOClock.ifEmpty { "--" },
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

