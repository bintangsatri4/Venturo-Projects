package com.example.venturoimageoperation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

abstract class FileCompress {
    companion object {
        /**
         * Compress image until the size is under the maxSizeInMB.
         * If the size after compression loop is still above maxSizeInMB, return null.
         *
         * @param image ImageBitmap to compress
         * @param maxSizeInMB Maximum size in MB to compress
         * @param minQuality Minimum image quality, if quality lower than this, return null
         * @param sizeThresholdInMB If image size larger than this automatically return null
         */
        suspend fun compressImageUntil(
            image: ImageBitmap,
            maxSizeInMB: Double = 2.0,
            minQuality: Int = 50,
            sizeThresholdInMB: Double = 10.0
        ): ImageBitmap? = withContext(Dispatchers.IO) {
            val maxFileSizeInMB: Double = maxSizeInMB * 1024 * 1024
            val bitmap: Bitmap = image.asAndroidBitmap()
            var quality = 100
            var compressedImageBytes: ByteArray
            do {
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
                compressedImageBytes = outputStream.toByteArray()
                // Check if size is larger than the threshold
                if (compressedImageBytes.size > sizeThresholdInMB) break
                quality -= 5
            } while (compressedImageBytes.size > maxFileSizeInMB && quality > minQuality)
            // Check if the compressedImageBytes size is under the maximum size or return null
            return@withContext if (compressedImageBytes.size <= maxFileSizeInMB) {
                val compressedBitmap = BitmapFactory.decodeByteArray(compressedImageBytes, 0, compressedImageBytes.size)
                compressedBitmap.asImageBitmap()
            } else {
                null
            }
        }

        fun resizeImageBitmap(
            image: ImageBitmap,
            maxWidth: Int = 1920,
            maxHeight: Int = 1080
        ): ImageBitmap {
            // Convert ImageBitmap to Bitmap
            val originalBitmap: Bitmap = image.asAndroidBitmap()
            // Calculate image aspect ratio
            val aspectRatio: Float = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
            val newWidth: Int
            val newHeight: Int
            if (originalBitmap.width > maxWidth || originalBitmap.height > maxHeight) {
                if (aspectRatio > 1) {
                    newWidth = maxWidth
                    newHeight = (maxWidth / aspectRatio).toInt()
                } else {
                    newHeight = maxHeight
                    newWidth = (maxHeight * aspectRatio).toInt()
                }
                // Downsize bitmap
                val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)
                // Convert Bitmap back to ImageBitmap
                return resizedBitmap.asImageBitmap()
            }
            // Return original if already under the size requirement
            return image
        }
    }
}