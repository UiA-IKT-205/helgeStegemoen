package com.example.superpiano

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.superpiano.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val TAG:String = "SuperPiano.MainActivity"

    // All variabler som du ikke setter med konstruktoer maa vaere lateinit
    // Kan unngaaes ved a gjoere nullable (legge til ? = null paa etter deklerasjonen)
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        signInAnonymously()
    }

    private fun signInAnonymously(){

        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "Login success: ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Login failed: ", it)
        }
    }
}