package com.example.eighteighttwod.ui.screen.twoDhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eighteighttwod.data.repository.TwoDHistoryRepository

class TwoDHistoryViewModelFactory (private val repository: TwoDHistoryRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(TwoDHistoryViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return TwoDHistoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknow ViewModel Class")
        }
    }
