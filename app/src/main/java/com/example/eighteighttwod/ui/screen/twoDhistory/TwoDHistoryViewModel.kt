package com.example.eighteighttwod.ui.screen.twoDhistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.TwoDHistoryRepository
import com.example.eighteighttwod.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TwoDHistoryViewModel(private val repository: TwoDHistoryRepository) : ViewModel() {

    private val _state = MutableStateFlow(TwoDHistoryState())
    val state: StateFlow<TwoDHistoryState> = _state.asStateFlow()

    private val _selectedYear = MutableStateFlow(2026)  // စတင်က January 2026
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    private val _selectedMonth = MutableStateFlow(1)
    val selectedMonth: StateFlow<Int> = _selectedMonth.asStateFlow()

    init {
        getAllHistory()
    }

    fun getAllHistory() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            when (val result = repository.getAllTwoDHistory()) {
                is Resource.Success -> {
                    val dataList = result.data ?: emptyList()
                    dataList.take(5).forEach { item ->
                        item.child.forEach { child ->
                            Log.d("TwoDHistoryVM", "   - twoD: ${child.twoD}")
                        }
                    }
                    _state.value = _state.value.copy(
                        isLoading = false,
                        twoDList = dataList,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                else -> Unit
            }
        }
    }

    fun changeMonth(delta: Int) {
        val currentMonth = _selectedMonth.value
        val currentYear = _selectedYear.value

        var newMonth = currentMonth + delta
        var newYear = currentYear

        if (newMonth > 12) {
            newMonth = 1
            newYear++
        } else if (newMonth < 1) {
            newMonth = 12
            newYear--
        }

        _selectedMonth.value = newMonth
        _selectedYear.value = newYear

        Log.d("TwoDHistoryVM", "Month changed to: $newMonth/$newYear")
    }

    fun getCurrentMonthYearString(month: Int, year: Int): String {
        val monthNames = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return "${monthNames[month - 1]}, $year"
    }

    fun getCalendarData(month: Int, year: Int): Map<Int, Pair<String, String>?> {
        val map = mutableMapOf<Int, Pair<String, String>?>()

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

        for (item in _state.value.twoDList) {
            try {
                val date = dateFormat.parse(item.date) ?: continue
                val cal = Calendar.getInstance().apply { time = date }

                val itemYear = cal.get(Calendar.YEAR)
                val itemMonth = cal.get(Calendar.MONTH) + 1

                Log.d(
                    "TwoDHistoryVM",
                    "Parsing date: ${item.date} -> Year: $itemYear, Month: $itemMonth (target: $year-$month)"
                )

                if (itemYear == year && itemMonth == month) {
                    val day = cal.get(Calendar.DAY_OF_MONTH)

                    var twelveOClockTwoD = ""
                    var fourOClockTwoD = ""

                    for (child in item.child) {

                        when (child.time.trim()) {
                            "12:00" -> twelveOClockTwoD = child.twoD
                            "4:00", "4:30" -> fourOClockTwoD = child.twoD
                        }
                    }

                    if (twelveOClockTwoD.isEmpty() && item.child.isNotEmpty()) {
                        twelveOClockTwoD = item.child.getOrNull(1)?.twoD
                            ?: item.child.firstOrNull()?.twoD.orEmpty()
                    }

                    if (fourOClockTwoD.isEmpty() && item.child.isNotEmpty()) {
                        fourOClockTwoD = item.child.lastOrNull()?.twoD.orEmpty()
                    }

                    val pair = if (twelveOClockTwoD.isNotEmpty() || fourOClockTwoD.isNotEmpty()) {
                        Pair(twelveOClockTwoD, fourOClockTwoD)
                    } else {
                        null
                    }

                    map[day] = pair

                    Log.d(
                        "TwoDHistoryVM",
                        "✓ Day $day populated: 12:00 = '$twelveOClockTwoD', 4:00 = '$fourOClockTwoD'"
                    )
                }
            } catch (e: Exception) {
                Log.e("TwoDHistoryVM", "Parse failed for date: ${item.date}", e)
            }
        }

        Log.d(
            "TwoDHistoryVM",
            "Final calendar data: ${map.size} days populated → ${map.keys.sorted()}"
        )
        return map
    }
}
