package com.example.plantpal.growth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.plantpal.R

class AddGrowthNoteActivity : AppCompatActivity() {

    private lateinit var noteEditText: EditText
    private lateinit var imagePreview: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_growth_note)

        noteEditText = findViewById(R.id.note_edit_text)
        imagePreview = findViewById(R.id.image_preview)
        val attachImageButton: Button = findViewById(R.id.attach_image_button)
        val saveNoteButton: Button = findViewById(R.id.save_note_button)

        attachImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        saveNoteButton.setOnClickListener {
            val noteText = noteEditText.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("note", noteText)
            resultIntent.putExtra("imageUri", selectedImageUri.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data?.data
            imagePreview.setImageURI(selectedImageUri)
            imagePreview.visibility = ImageView.VISIBLE
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}
