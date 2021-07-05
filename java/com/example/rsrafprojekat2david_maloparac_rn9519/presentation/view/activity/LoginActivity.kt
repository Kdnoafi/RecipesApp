package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rsrafprojekat2david_maloparac_rn9519.MainActivity
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.ActivityLoginBinding
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val username: String = "username"
    private val password: String = "password"

    private lateinit var binding: ActivityLoginBinding

    companion object {
        var ulogovan: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val isLoggedIn = sharedPref.getInt("ulogovan", 0)

        if(isLoggedIn == 1) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        initView()
    }

    private fun initView() {
        binding.btnLogIn.setOnClickListener {
            val user = binding.txtUsername.text.toString()
            val pass = binding.txtPassword.text.toString()

            if (user.isBlank()) {
                Toast.makeText(this, "Username cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.isBlank()) {
                Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(user == username && pass == password) {
                val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                with (sharedPref.edit()) {
                    putInt("ulogovan", 1)
                    apply()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}