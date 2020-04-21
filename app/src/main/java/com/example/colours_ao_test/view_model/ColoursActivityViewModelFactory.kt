package com.example.colours_ao_test.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.colours_ao_test.online_repo.OnlineWordRepository
import javax.inject.Inject


class ColoursActivityViewModelFactory @Inject constructor(
    private val onlineWordRepository: OnlineWordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ColoursActivityViewModel(onlineWordRepository) as T
    }
}