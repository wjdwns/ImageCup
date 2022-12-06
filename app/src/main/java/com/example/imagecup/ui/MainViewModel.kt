package com.example.imagecup.ui

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagecup.data.repository.Repository
import com.example.imagecup.model.Photo
import com.example.imagecup.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _photoUri : MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    val photoUri : StateFlow<List<Uri>>
        get() = _photoUri

    private val _photos : MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photo : StateFlow<List<Photo>>
        get() = _photos

    private val _loading : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading : StateFlow<Boolean>
        get() = _loading

    private val _labels : MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val labels : StateFlow<List<Photo>>
        get() = _labels

    private val _albumPhotos : MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val albumPhotos : StateFlow<List<Photo>>
        get() = _albumPhotos

    private val _uploadPhoto : MutableStateFlow<Photo?> = MutableStateFlow(null)
    val uploadPhoto : StateFlow<Photo?>
        get() = _uploadPhoto



    fun getAllLabels(){
        viewModelScope.launch {
            _labels.value = repository.getAllLabels()
        }
    }

    fun updatePhoto(photo: Uri, isSelect: Boolean) {
        when {
            isSelect -> if (!_photoUri.value.contains(photo)) {
                _photoUri.value = _photoUri.value.plus(photo)
            }
            else -> if (_photoUri.value.contains(photo)) {
                _photoUri.value = _photoUri.value.minus(photo)
            }
        }
    }
    fun objectDetect(imageList : List<MultipartBody.Part>){
        _loading.value = true
        viewModelScope.launch {
            repository.objectDetect(imageList).collectLatest {
                runCatching {
                    for(i: Int in 0 until _photoUri.value.size){
                        _photos.value = _photos.value.plus(Photo(_photoUri.value[i].toString(),it[i].label))
                    }
                    insertPhoto(_photos.value)
                }.onFailure {
                    Timber.e("$it")
                }
            }
        }
    }

    private fun insertPhoto(photoList: List<Photo>){
        viewModelScope.launch {
            repository.insertPhoto(photoList)
        }
        _loading.value = false
    }

    fun getAlbumPhotos(label : String){
        viewModelScope.launch {
            _albumPhotos.value = repository.getAllPhotos(label)
        }
    }

    fun setUploadPhoto(photo: Photo?){
        _uploadPhoto.value = photo
    }

    fun uploadPhoto(photo:MultipartBody.Part, label : String){
        viewModelScope.launch {
            repository.uploadFile(photo,PrefsManager.uid,label).collectLatest {

            }
        }
    }

}