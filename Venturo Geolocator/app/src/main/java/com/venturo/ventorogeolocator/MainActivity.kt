package com.venturo.ventorogeolocator

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.venturo.ventorogeolocator.ui.theme.VentoroGeolocatorTheme
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            VentoroGeolocatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationScreen(
                        fusedLocationClient = fusedLocationClient,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LocationScreen(fusedLocationClient: FusedLocationProviderClient, modifier: Modifier = Modifier) {
    var location by remember { mutableStateOf("Fetching location...") }
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isLocationEnabled(context)) {
                getLocation(context, fusedLocationClient) { loc ->
                    location = loc
                }
            } else {
                showPopup = true
            }
        } else {
            location = "Permission denied"
        }
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            if (isLocationEnabled(context)) {
                getLocation(context, fusedLocationClient) { loc ->
                    location = loc
                }
            } else {
                showPopup = true
            }
        }
    }

    if (showPopup) {
        showLocationSettingsPopup(context) {
            showPopup = false
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = location, modifier = Modifier.padding(16.dp))
    }
}

@SuppressLint("MissingPermission")
fun getLocation(context: Context, fusedLocationClient: FusedLocationProviderClient, onLocationReceived: (String) -> Unit) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val address = addresses?.firstOrNull()
            val addressLine = address?.getAddressLine(0) ?: "Address not available"
            val placeName = address?.featureName ?: "Place name not available"
            val buildingName = address?.subThoroughfare ?: "Building name not available"
            val neighborhood = address?.subLocality ?: "Neighborhood not available"
            onLocationReceived("Place: $placeName\nBuilding: $buildingName\nNeighborhood: $neighborhood\nLat: ${it.latitude}, Lon: ${it.longitude}\nAddress: $addressLine")
        } ?: run {
            onLocationReceived("Location not available")
        }
    }
}

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

@Composable
fun showLocationSettingsPopup(context: Context, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Location Services Disabled") },
        text = { Text("Please enable location services to continue.") },
        confirmButton = {
            TextButton(onClick = {
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                onDismiss()
            }) {
                Text("Settings")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    VentoroGeolocatorTheme {
        LocationScreen(fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current))
    }
}