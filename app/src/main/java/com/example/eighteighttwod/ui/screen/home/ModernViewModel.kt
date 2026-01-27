package com.example.eighteighttwod.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.ModernRepository
import com.example.eighteighttwod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModernViewModel @Inject constructor(
    private val repository: ModernRepository
): ViewModel() {

    private val _state = MutableStateFlow(ModernState())
    val state: StateFlow<ModernState> = _state

    init {
        getModernData()
    }

    // Refresh နှိပ်ရင် ပြန်ခေါ်လို့ရအောင် ဒီ Function ကို public ထားပါတယ်
    fun getModernData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // 9:30 နဲ့ 2:00 ကို ပြိုင်တူဆွဲမယ် (Parallel)
                val morningJob = async { repository.getModern("9:30") }
                val eveningJob = async { repository.getModern("2:00") }

                val morningResult = morningJob.await()
                val eveningResult = eveningJob.await()

                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        // Data ရှိရင်ထည့်၊ မရှိရင် အဟောင်းအတိုင်းထား
                        morningData = if (morningResult is Resource.Success) morningResult.data else currentState.morningData,
                        eveningData = if (eveningResult is Resource.Success) eveningResult.data else currentState.eveningData,

                        // Error တစ်ခုခုတက်ရင် Error message မှတ်မယ်
                        error = if (morningResult is Resource.Error || eveningResult is Resource.Error) "Connection Error" else null
                    )
                }
            } catch (e: Exception) {
                // Internet မရှိရင် ဒီကိုရောက်မယ်
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No Internet Connection"
                    )
                }
            }
        }
    }
}