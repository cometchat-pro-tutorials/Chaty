package com.wajahatkarim3.chaty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity()
{
    lateinit var inputUsername: TextInputLayout
    lateinit var txtUsername: TextInputEditText
    lateinit var btnLogin: MaterialButton

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

        btnLogin.setOnClickListener {
            // Clear previous errors if any
            inputUsername.error = null

            // Username Validation
            if (txtUsername.text.toString().isEmpty())
            {
                inputUsername.error = "Username cannot be empty!"
                return@setOnClickListener
            }

            // Go to Contacts screen
            var intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
