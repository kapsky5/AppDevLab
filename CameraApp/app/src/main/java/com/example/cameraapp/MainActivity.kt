package com.example.cameraapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 101
        const val REQUEST_VIDEO_CAPTURE = 102
        const val REQUEST_PERMISSION_WRITE_TO_EXTERNAL_STORAGE = 201
    }

    lateinit var mCapturePhotoButton: MaterialButton
    lateinit var mTakeVideoButton: MaterialButton
    lateinit var mNameEditText: TextInputEditText
    lateinit var mImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        mCapturePhotoButton = findViewById(R.id.capture_photo_button)
        mTakeVideoButton = findViewById(R.id.take_video_button)
        mNameEditText = findViewById(R.id.name_edit_text)
        mImageView = findViewById(R.id.image)

        mCapturePhotoButton.setOnClickListener {
            val imageName = mNameEditText.text.toString()
            if (imageName.isEmpty()) {
                mNameEditText.error = "Please input a image name.";
            } else {
                dispatchTakePictureIntent(MediaStore.ACTION_IMAGE_CAPTURE)
            }
        }

        mTakeVideoButton.setOnClickListener {
            val imageName = mNameEditText.text.toString()
            if (imageName.isEmpty()) {
                mNameEditText.error = "Please input a video name.";
            } else {
                dispatchTakePictureIntent(MediaStore.ACTION_VIDEO_CAPTURE)
            }
        }

        findViewById<ExtendedFloatingActionButton>(R.id.send_sms_fab).setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Message Sent Successfully",
                Toast.LENGTH_LONG
            ).show()
        }

        askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun askPermission(permission: String) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(permission)) {
                // Explain to the user why we need to read the contacts
                Toast.makeText(
                    applicationContext,
                    "Please grant permission to write to external storge to save the picture.",
                    Toast.LENGTH_LONG
                ).show()
            }
            requestPermissions(
                arrayOf(permission),
                REQUEST_PERMISSION_WRITE_TO_EXTERNAL_STORAGE
            )
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(fileType: String): File {
        // Create an image file name
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir?.mkdirs()
        return File(storageDir, "${mNameEditText.text}.$fileType").apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

    private fun dispatchTakePictureIntent(intentType: String) {
        Intent(intentType).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    if (intentType == MediaStore.ACTION_IMAGE_CAPTURE) {
                        createImageFile("jpg")
                    } else if (intentType == MediaStore.ACTION_VIDEO_CAPTURE) {
                        createImageFile("mp4")
                    } else {
                        throw RuntimeException("Invalid Intent")
                    }
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Toast.makeText(
                        applicationContext,
                        "Cannot create file due to $ex",
                        Toast.LENGTH_LONG
                    ).show()
                    null
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.cameraapp.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic()
            Toast.makeText(
                applicationContext,
                "Saved image to $currentPhotoPath",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}