package com.example.eighteighttwod.ui.screen.omen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.OmenRepository
import com.example.eighteighttwod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OmenViewModel @Inject constructor(
    private val repository: OmenRepository
) : ViewModel(){

    private val _state = MutableStateFlow<OmenState>(OmenState())
    val state : StateFlow<OmenState> =_state

    init {
        getOmen()
    }

    fun getOmen(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when(val result = repository.getOmen()){
                is Resource.Success ->{
                    Log.d("omeApi","Success : ${result.data}")
                    _state.update { it.copy(isLoading = false, liveData = result.data ?: emptyList(),error = null) }
                }
                is Resource.Error ->{
                    Log.d("omeApi","Success : ${result.message}")
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