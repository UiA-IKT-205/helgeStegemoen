package com.example.superpiano

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.superpiano.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private val TAG:String = "SuperPiano.MainActivity"
    // needed for upload functionality (Firebase):
    private lateinit var piano:PianoLayout

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        signInAnonymously()

        // Interested in the fragment, not the layout
        piano = supportFragmentManager.findFragmentById(binding.piano.id) as PianoLayout
        piano.onSave = {
            this.upload(it)
            print(it.toString())
        }

    }

    private fun upload(file: Uri) {
        // Need to create a reference before uploading to storage
        val ref = FirebaseStorage.getInstance().reference.child(
            "melodies/${file.lastPathSegment}")
        var uploadTask = ref.putFile(file)
        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Saved file to fb ${it.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Error saving file to fb", it)
        }
    }

    private fun signInAnonymously(){

        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login success: ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Login failed: ", it)
        }
    }
}