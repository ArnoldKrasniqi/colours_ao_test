package com.example.colours_ao_test.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colours_ao_test.com.ColourEntity
import com.example.colours_ao_test.online_repo.OnlineWordRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.Math.random
import kotlin.collections.ArrayList


class ColoursActivityViewModel(
    private val onlineWordRepository: OnlineWordRepository
) : ViewModel() {

    val loadingState : LiveData<LoadingState>
        get() = mLoadingState
    private var mLoadingState = MutableLiveData<LoadingState>()

    fun getWords() {
        mLoadingState.value = LoadingState.Loading
        val listOfColourEntity = ArrayList<ColourEntity>()
         viewModelScope.launch {
            try {
                onlineWordRepository.getRandomNames().apply {
                    for (element in this){
                        listOfColourEntity.add(ColourEntity(
                            element,
                            generateRandomHexaDecimal()
                            )
                        )
                    }
                }
                mLoadingState.postValue(LoadingState.OnSuccess(listOfColourEntity))

            } catch (e : Exception){
                if (e.message != null) {
                    mLoadingState.postValue(LoadingState.OnFailure(e.message))
                } else {
                    mLoadingState.postValue(LoadingState.OnFailure("No Error Message!"))
                }
            }
        }

    }
   companion object RandomColourGenerator {
      fun generateRandomHexaDecimal() : String{
          val randomNumber = (random() * 0xffffff).toInt()
          return String.format("#%06x",randomNumber)
      }
   }

    sealed class LoadingState {
        object Loading : LoadingState()
        data class OnSuccess(val listOfWords : ArrayList<ColourEntity>) : LoadingState()
        data class OnFailure(val errorMessage : String?) : LoadingState()
    }
}