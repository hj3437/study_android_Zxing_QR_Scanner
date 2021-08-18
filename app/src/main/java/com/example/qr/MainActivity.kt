package com.example.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.Toast

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    private lateinit var qrcodeButton: Button
    private lateinit var qrcodeScannedImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        qrcodeScannedImageView = findViewById(R.id.qr_reader_scanned_image_view)

        qrcodeButton = findViewById(R.id.qr_reader_button)
        qrcodeButton.setOnClickListener {
            // `this` is the current Activity
            IntentIntegrator(this).initiateScan()
        }
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

                Log.d("TAG", "onActivityResult: >>>> YES")
            }

            if (result.barcodeImagePath != null) {
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                setBitmapImageView(qrcodeScannedImageView, bitmap)
                Log.d("TAG", "onActivityResult: >>>> YES")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // Custom QR Reader Button
    fun onClickCustomQRReader(view: View) {
        val integrator = IntentIntegrator(this).apply {
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setPrompt("Scan a barcode")
            setCameraId(0) // Use a specific camera of the device, 0 = Back
            setBeepEnabled(false) // Sound
            setBarcodeImageEnabled(true)
        }
        integrator.initiateScan()
    }

    // Image Wrapper
    private fun setBitmapImageView(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

    fun onClickCustomActivityQRReader(view: View) {
        val integrator = IntentIntegrator(this)
        integrator.setBarcodeImageEnabled(true)
        integrator.captureActivity = CustomBarcodeActivity::class.java
        integrator.initiateScan()
    }
}