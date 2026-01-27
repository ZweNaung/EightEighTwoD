package com.example.eighteighttwod.ui.screen.threeD

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.ThreeDRepository
import com.example.eighteighttwod.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThreeDViewModel(
    private val repository: ThreeDRepository
): ViewModel() {

    private val _state = MutableStateFlow(ThreeDState())
    val state = _state.asStateFlow()

    init {
        getAllThreeD()
    }
    fun getAllThreeD(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when(val result = repository.getAllThreeD()){
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            threeDList = result.data ?: emptyList(),
                            error = null
                        )
                    }
                }
                is Resource.Error ->{
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Unknow Error"
                        )
                    }
                }
                else -> Unit
            }
        }
    }




}