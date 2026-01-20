package com.example.eighteighttwod.ui.screen.lucky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eighteighttwod.data.repository.LuckyRepository
import com.example.eighteighttwod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LuckyViewModel @Inject constructor(
    private val repository: LuckyRepository
): ViewModel(){
    private val _state = MutableStateFlow(LuckyState())
    val state: StateFlow<LuckyState> =_state

    init {
        getLuck()
    }

    fun getLuck(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when(val result = repository.getLucky()){
                is Resource.Success ->{
                _state.update { it.copy(isLoading = false, liveData = result.data ?: emptyList(),error = null) }
                }
                is Resource.Error ->{
                    _state.update {
                        it.copy(isLoading = false, error = result.message)}
                }
                is Resource.Loading ->{
                         _state.update { it.copy(isLoading = true) }
                }
                else -> Unit
            }
        }
    }
}