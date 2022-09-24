package com.example.twobufferpagination

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel : ViewModel() {

    private val filesReader = LogFilesReader()
    val fileTextWrapper = MutableLiveData<ArrayList<String?>>()

    init {
        viewModelScope.launch {
            filesReader.filesText.collect {
               fileTextWrapper.value = it
            }
        }
    }

    fun readFiles(context : Context) {
        viewModelScope.launch {
            filesReader.readFiles(context)
        }
    }
}