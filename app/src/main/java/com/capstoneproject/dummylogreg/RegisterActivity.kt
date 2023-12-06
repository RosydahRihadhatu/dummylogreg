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
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var editTextEmail: TextInputEditText? = null
    private var editTextUsername: TextInputEditText? = null
    private var editTextPassword: TextInputEditText? = null
    private var buttonReg: Button? = null
    private var progressBar: ProgressBar? = null
    private var textView: TextView? = null

    override fun onStart() {
        super.onStart()
        mAuth?.currentUser?.let {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.email)
        editTextUsername = findViewById(R.id.username)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.loginNow)

        textView?.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg?.setOnClickListener {
            progressBar?.visibility = View.VISIBLE
            val email = editTextEmail?.text.toString()
            val username = editTextUsername?.text.toString()
            val password = editTextPassword?.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@RegisterActivity, "All fields must be filled", Toast.LENGTH_SHORT).show()
                progressBar?.visibility = View.GONE
                return@setOnClickListener
            }

            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    progressBar?.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity, "Account Created",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
