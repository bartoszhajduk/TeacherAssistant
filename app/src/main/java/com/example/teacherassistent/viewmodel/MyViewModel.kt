package com.example.teacherassistent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private var _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

    val text : LiveData<String> = _text


}