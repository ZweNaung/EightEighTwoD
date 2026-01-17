package com.example.eighteighttwod.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.LiveRepository
import com.example.eighteighttwod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveViewModel @Inject constructor(
    private val repository: LiveRepository
    ): ViewModel() {

    private val _state = MutableStateFlow(LiveState())
    val state: StateFlow<LiveState> = _state.asStateFlow()

    init {
        getLive()
    }


    fun getLive(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when(val result =repository.getLiveData()){
                is Resource.Success ->{
                    _state.update {
                        it.copy(isLoading = false, liveData = result.data, error = null)
                    }
                }
                is Resource.Error ->{
                    _state.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading ->{
                    _state.update { it.copy(isLoading = true) }
                }
                else -> Unit
            }
        }
    }
}
