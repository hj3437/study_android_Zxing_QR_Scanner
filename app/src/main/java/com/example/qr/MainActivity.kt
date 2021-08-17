package com.example.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.Toast

import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var qrcodeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}