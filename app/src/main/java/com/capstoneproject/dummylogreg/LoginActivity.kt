package com.capstoneproject.dummylogreg

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private var editTextUsername: TextInputEditText? = null
    private var editTextPassword: TextInputEditText? = null
    private var buttonLogin: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var textView: TextView? = null

    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        editTextPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.registerNow)
        FirebaseApp.initializeApp(this)


        textView?.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin?.setOnClickListener(View.OnClickListener {
            progressBar?.visibility = View.VISIBLE
            val username: String?
            val password: String?
            username = editTextUsername!!.text.toString()
            password = editTextPassword!!.text.toString()
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this@LoginActivity, "Enter username", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Enter Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            mAuth!!.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        progressBar?.visibility = View.VISIBLE
                        Toast.makeText(
                            this@LoginActivity, "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        progressBar?.visibility = View.VISIBLE
                        Toast.makeText(
                            this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
}