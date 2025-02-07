package com.example.venturoimageoperation.screens.imageoperation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.example.venturoimageoperation.CMPImageCropDialog
import com.example.venturoimageoperation.ImageCropResult
import com.example.venturoimageoperation.ImageCropper
import com.example.venturoimageoperation.R
import com.example.venturoimageoperation.rememberImageCropper
import com.example.venturoimageoperation.screens.components.views.LoadingOverlay
import com.example.venturoimageoperation.ui.theme.Primary400
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImageOperationScreen(
    viewModel: ImageOperationViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val imageCropper = rememberImageCropper()
    val selectedImage by viewModel.selectedImage.collectAsState()
    val croppedImage by viewModel.croppedImage.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.setImage(context, uri) }
    )

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    LaunchedEffect(selectedImage) {
        if (selectedImage != null) {
            when (val imageCropResult = imageCropper.cropImage(bmp = selectedImage!!)) {
                is ImageCropResult.Error -> {
                    Toast.makeText(context, "Gagal memotong gambar", Toast.LENGTH_SHORT).show()
                }
                is ImageCropResult.Success -> {
                    viewModel.updateCroppedImage(imageCropResult.bitmap)
                }
                else -> {}
            }
        }
    }

    ImageOperationContent(
        croppedImage = croppedImage,
        isLoading = isLoading,
        imageCropper = imageCropper,
        onPickImage = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    )
}

@Composable
fun ImageOperationContent(
    modifier: Modifier = Modifier,
    croppedImage: ImageBitmap? = null,
    isLoading: Boolean = false,
    imageCropper: ImageCropper = rememberImageCropper(),
    onPickImage: () -> Unit = {}
) {
    // Image cropper dialog
    CMPImageCropDialog(imageCropper = imageCropper)
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.background_replace_24px),
            contentDescription = "doodle background"
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // If croppedImage exists, show image composable
            croppedImage?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
            // Only show text if croppedImage is null
            if (croppedImage == null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Pilih Gambar Terlebih Dahulu!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Primary400
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Action to pick image
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Primary400),
                onClick = onPickImage
            ) {
                Text("Pilih Gambar")
            }
        }
        // Overlay loading indicator
        LoadingOverlay(isLoading = isLoading)
    }
}

@Preview(showBackground = true)
@Composable
fun ImageOperationContentPreview() {
    val imageCropper = rememberImageCropper()
    val sampleBitmap = android.graphics.Bitmap.createBitmap(100, 100, android.graphics.Bitmap.Config.ARGB_8888)
    val sampleImageBitmap = sampleBitmap.asImageBitmap()
    ImageOperationContent(
        croppedImage = sampleImageBitmap,
        imageCropper = imageCropper
    )
}