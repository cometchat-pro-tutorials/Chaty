package com.wajahatkarim3.chaty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity()
{
    lateinit var inputUsername: TextInputLayout
    lateinit var txtUsername: TextInputEditText
    lateinit var btnLogin: MaterialButton
    lateinit var progressLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupViews()
    }

    fun setupViews()
    {
        inputUsername = findViewById(R.id.inputUsername)
        txtUsername = findViewById(R.id.txtUsername)
        btnLogin = findViewById(R.id.btnLogin)
        progressLoading = findViewById(R.id.progressLoading)

        btnLogin.setOnClickListener {
            // Clear previous errors if any
            inputUsername.error = null

            // Username Validation
            if (txtUsername.text.toString().isEmpty())
            {
                inputUsername.error = "Username cannot be empty!"
                return@setOnClickListener
            }

            performLogin(txtUsername.text.toString())
        }
    }

    private fun performLogin(userId: String) {
        // Show Progress Bar
        progressLoading.visibility = View.VISIBLE
        btnLogin.visibility = View.GONE
        txtUsername.isEnabled = false

        perfomDummyLogin()
    }

    fun perfomDummyLogin()
    {
        Handler().postDelayed(Runnable {
            // Go to Contacts screen
            var intent = Intent(this@LoginActivity, ContactsActivity::class.java)
            startActivity(intent)
            finish()
        }, 2*1000)                  // 2 seconds dummy delay
    }
}
