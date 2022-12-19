package com.example.imagecup.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagecup.data.repository.Repository
import com.example.imagecup.model.GetPhotosResponse
import com.example.imagecup.model.Photo
import com.example.imagecup.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _photoUri: MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    val photoUri: StateFlow<List<Uri>>
        get() = _photoUri

    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photo: StateFlow<List<Photo>>
        get() = _photos

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    private val _labels: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val labels: StateFlow<List<Photo>>
        get() = _labels

    private val _albumPhotos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val albumPhotos: StateFlow<List<Photo>>
        get() = _albumPhotos

    private val _uploadPhoto: MutableStateFlow<Photo?> = MutableStateFlow(null)
    val uploadPhoto: StateFlow<Photo?>
        get() = _uploadPhoto

    private val _pageNum: MutableStateFlow<Int> = MutableStateFlow(0)
    val pageNum: StateFlow<Int>
        get() = _pageNum

    private val _pageRandomNum: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val pageRandomNum: StateFlow<List<Int>>
        get() = _pageRandomNum

    private val _photoResponse: MutableStateFlow<GetPhotosResponse?> = MutableStateFlow(null)
    val photoResponse: StateFlow<GetPhotosResponse?>
        get() = _photoResponse

    fun getAllLabels() {
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

    fun objectDetect(imageList: List<MultipartBody.Part>) {
        _loading.value = true
        viewModelScope.launch {
            repository.objectDetect("http://54.180.55.20:8080/ObjectDetect", imageList)
                .collectLatest {
                    runCatching {
                        for (i: Int in 0 until _photoUri.value.size) {
                            _photos.value =
                                _photos.value.plus(
                                    Photo(
                                        _photoUri.value[i].toString(),
                                        it[i].label
                                    )
                                )
                        }
                        insertPhoto(_photos.value)
                    }.onFailure {
                        Timber.e("$it")
                    }
                }
        }
    }

    private fun insertPhoto(photoList: List<Photo>) {
        viewModelScope.launch {
            repository.insertPhoto(photoList)
        }
        _loading.value = false
    }

    fun getAlbumPhotos(label: String) {
        viewModelScope.launch {
            _albumPhotos.value = repository.getAllPhotos(label)
        }
    }

    fun setUploadPhoto(photo: Photo?) {
        _uploadPhoto.value = photo
    }

    fun uploadPhoto(photo: MultipartBody.Part, label: String) {
        val requestBodyUid: RequestBody =
            PrefsManager.uid.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyLabel: RequestBody = label.toRequestBody("text/plain".toMediaTypeOrNull())
        viewModelScope.launch {
            repository.uploadFile(photo, requestBodyUid, requestBodyLabel).collect {
                runCatching {
                    Timber.d("파일 업로드 완료")
                }.onFailure {
                    Timber.e("$it")
                }
            }
        }
    }

    fun initGetPhoto(label: String) {
        viewModelScope.launch {
            repository.getPhotos(label, PrefsManager.uid, -1).collectLatest {
                runCatching {
                    _pageNum.value = it.photoId
                    setPageRandomNum()
                    Timber.d("pageNum : ${_pageNum.value}")
                }.onFailure {
                    Timber.e("$it")
                }
            }
        }
    }

    private fun setPageRandomNum() {
        val set = mutableSetOf<Int>()
        while (set.size < _pageNum.value) {
            set.add((0 until _pageNum.value).random())
        }
        Timber.d("array : $set")
        _pageRandomNum.value = set.toList()
    }

    fun getPhoto(label: String, pageNum: Int) {
        Timber.d("pagelll : $pageNum")
        val page = pageRandomNum.value[pageNum]
        viewModelScope.launch {
            repository.getPhotos(label, PrefsManager.uid, _pageRandomNum.value[pageNum])
                .collectLatest {
                    _photoResponse.value = it
                }
        }
    }

    fun ratePhoto(getPhotosResponse: GetPhotosResponse, score: Float) {
        viewModelScope.launch {
            repository.evaluationPhoto(getPhotosResponse.photoId, PrefsManager.uid, score)
                .collectLatest {
                    Timber.d("평가 완료")
                }
        }
    }
}
