package com.wajahatkarim3.chaty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    lateinit var imgContactPhoto: AppCompatImageView
    lateinit var txtUsername: AppCompatTextView
    lateinit var txtEmail: AppCompatTextView
    lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupViews()
        setupProfile()
    }

    fun setupViews()
    {
        imgContactPhoto = findViewById(R.id.imgContactPhoto)
        txtUsername = findViewById(R.id.txtUsername)
        txtEmail = findViewById(R.id.txtEmail)
        btnLogout = findViewById(R.id.btnLogout)

        // Toolbar
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        // Logout Button
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId)
        {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setupProfile()
    {
        // Setting Up Dummy User Data
        var name = "My Name"
        var email = "my.name@domain.com"

        txtUsername.text = name
        txtEmail.text = email

        // Generate Letter Avatar
        var generator = ColorGenerator.MATERIAL
        var color = generator.randomColor

        var drawable = TextDrawable.builder().buildRect(name, color)
        imgContactPhoto.setImageDrawable(drawable)
    }

    fun logoutUser()
    {
        // Starting Login screen
        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
