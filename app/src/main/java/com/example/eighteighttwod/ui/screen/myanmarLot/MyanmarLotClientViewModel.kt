package com.example.eighteighttwod.ui.screen.myanmarLot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.MyanmarLotRepository
import com.example.eighteighttwod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyanmarLotClientViewModel @Inject constructor(
    private val repository: MyanmarLotRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MyanmarLotClientState())
    val state: StateFlow<MyanmarLotClientState> = _state.asStateFlow()

    init {
        fetchMyanmarLots()
    }

    fun fetchMyanmarLots() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = repository.getMyanmarLots()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            lotteryList = result.data ?: emptyList()
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Something went wrong"
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
                else -> Unit
            }
        }
    }
}