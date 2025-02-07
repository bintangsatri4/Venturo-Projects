package com.example.venturoimageoperation.screens.imageoperation

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venturoimageoperation.utils.helpers.Convertor
import com.example.venturoimageoperation.FileCompress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImageOperationViewModel : ViewModel() {
    private val _selectedImage: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)
    val selectedImage: StateFlow<ImageBitmap?> get() = _selectedImage

    private val _croppedImage: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)
    val croppedImage: StateFlow<ImageBitmap?> get() = _croppedImage

    private val _toastMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val toastMessage: StateFlow<String?> get() = _toastMessage

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun setImage(context: Context, uri: Uri?) {
        if (uri == null) return

        _isLoading.value = true
        viewModelScope.launch {
            val imageBitmap = Convertor.uriToImageBitmap(context, uri) ?: run {
                _toastMessage.value = "Gagal memuat gambar"
                _isLoading.value = false
                return@launch
            }

            val compressedImage = FileCompress.compressImageUntil(image = imageBitmap)
            if (compressedImage != null) {
                val downSizeImage = FileCompress.resizeImageBitmap(image = compressedImage)
                _selectedImage.value = downSizeImage
            } else {
                _toastMessage.value = "Ukuran gambar anda melebihi batas ukuran 2MB"
            }
            _isLoading.value = false
        }
    }

    fun updateCroppedImage(image: ImageBitmap?) {
        _croppedImage.value = image
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}