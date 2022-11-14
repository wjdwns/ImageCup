package com.example.imagecup.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
    private val _photoUri : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val photoUri : MutableStateFlow<List<String>>
        get() = _photoUri

    fun updatePhoto(photo: String, isSelect: Boolean) {
        when {
            isSelect -> if (!_photoUri.value.contains(photo)) {
                _photoUri.value = _photoUri.value.plus(photo)
            }
            else -> if (_photoUri.value.contains(photo)) {
                _photoUri.value = _photoUri.value.minus(photo)
            }
        }
    }
}