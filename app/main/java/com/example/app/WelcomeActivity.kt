package com.example.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.io.IOException

class WelcomeActivity : AppCompatActivity() {
    private lateinit var petNameInput: TextInputEditText
    private lateinit var yearsInput: TextInputEditText
    private lateinit var monthsInput: TextInputEditText
    private lateinit var photoImageView: ImageView
    private lateinit var addPhotoButton: MaterialButton
    private lateinit var submitButton: MaterialButton
    
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private val PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        petNameInput = findViewById(R.id.petNameInput)
        yearsInput = findViewById(R.id.yearsInput)
        monthsInput = findViewById(R.id.monthsInput)
        photoImageView = findViewById(R.id.photoImageView)
        addPhotoButton = findViewById(R.id.addPhotoButton)
        submitButton = findViewById(R.id.submitButton)
    }

    private fun setupClickListeners() {
        addPhotoButton.setOnClickListener {
            checkPermissionAndPickImage()
        }

        submitButton.setOnClickListener {
            if (validateInputs()) {
                // TODO: Implement actual profile creation logic
                Toast.makeText(this, "Профиль питомца успешно создан!", Toast.LENGTH_SHORT).show()
                // Navigate to next screen
                // startActivity(Intent(this, NextActivity::class.java))
                // finish()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val name = petNameInput.text.toString()
        val years = yearsInput.text.toString()
        val months = monthsInput.text.toString()

        if (name.isEmpty()) {
            petNameInput.error = "Введите имя питомца"
            return false
        }

        if (years.isEmpty() || months.isEmpty()) {
            Toast.makeText(this, "Укажите возраст питомца", Toast.LENGTH_SHORT).show()
            return false
        }

        if (years == "0" && months == "0") {
            Toast.makeText(this, "Возраст не может быть 0 лет и 0 месяцев", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Добавьте фото питомца", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun checkPermissionAndPickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                Toast.makeText(this, "Требуется разрешение для выбора фото", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                photoImageView.setImageBitmap(bitmap)
                photoImageView.visibility = ImageView.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Ошибка при загрузке изображения", Toast.LENGTH_SHORT).show()
            }
        }
    }
} 