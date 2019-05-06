package com.wajahatkarim3.chaty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
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

        CometChat.login(userId, getString(R.string.comet_api_key), object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User?) {

                // Hide Progress Bar
                progressLoading.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                txtUsername.isEnabled = true

                if (user != null)
                {
                    // Go to Contacts screen
                    var intent = Intent(this@LoginActivity, ContactsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this@LoginActivity, "Couldn't find the user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(exception: CometChatException?) {

                // Hide Progress Bar
                progressLoading.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
                txtUsername.isEnabled = true

                Toast.makeText(this@LoginActivity, exception?.localizedMessage ?: "Unknown Error Occurred!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
