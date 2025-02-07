package com.example.venturoimageoperation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.graphics.createBitmap

sealed class ImageCropResult {
    data class Success(val bitmap: ImageBitmap) : ImageCropResult()
    object Error : ImageCropResult()
}

class ImageCropper {
    fun cropImage(bmp: ImageBitmap): ImageCropResult {
        return try {
            val androidBitmap = bmp.asAndroidBitmap()
            val width = androidBitmap.width
            val height = androidBitmap.height
            val cropRect = Rect(width / 4, height / 4, 3 * width / 4, 3 * height / 4)
            val croppedBitmap = createBitmap(
                cropRect.width(),
                cropRect.height(),
                androidBitmap.config ?: Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(croppedBitmap)
            canvas.drawBitmap(androidBitmap, cropRect, Rect(0, 0, cropRect.width(), cropRect.height()), Paint())
            ImageCropResult.Success(croppedBitmap.asImageBitmap())
        } catch (e: Exception) {
            ImageCropResult.Error
        }
    }
}

@Composable
fun rememberImageCropper(): ImageCropper {
    return remember { ImageCropper() }
}