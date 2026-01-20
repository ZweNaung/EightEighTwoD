package com.example.eighteighttwod.ui.screen.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eighteighttwod.data.repository.UpdateResultRepository
import com.example.eighteighttwod.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateResultViewModel @Inject constructor(
    private val repository: UpdateResultRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UpdateResultState())
    val state: StateFlow<UpdateResultState> = _state.asStateFlow()

    init {
        updateResult()
    }

    private fun updateResult() {
        viewModelScope.launch {
            repository.getTodayResults()
                .collect { result->
                    when(result){
                    is Resource.Success<*> -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                updateResult = result.data ?: emptyList(),
                                error = null
                            )
                        }
                    }
                        is Resource.Error<*> -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                        is Resource.Loading<*> ->{
                            _state.update { it.copy(isLoading = true) }
                        }
                        else -> Unit

                    }
                }

        }
    }
    }
