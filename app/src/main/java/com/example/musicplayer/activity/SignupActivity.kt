package com.example.musicplayer.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.ActivitySignupBinding
import com.example.musicplayer.fragment.HomeFragment
import com.example.musicplayer.vm.AuthViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //init viewmodel here
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        // Initialising auth object
        binding.btnSignup.setOnClickListener {
            signUpuser()
            viewModel.register(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
            startActivity(Intent(this, HomeFragment::class.java))
            finish()
        }
        viewModel.isSuccessful.observe(this, Observer {
            //handle
            var message = ""
            if (it == true) {
                message = "Sign Up Success!"
            } else {
                message = "Sign Up Failed!"
            }

            Toast.makeText(application, message, Toast.LENGTH_LONG).show()
        })
    }

    private fun signUpuser() {

        //check pass
        if (binding.edtName.text.toString().isBlank() || binding.edtEmail.text.toString()
                .isBlank() || binding.edtPassword.text.toString()
                .isBlank() || binding.edtPasswordConfim.text.toString().isBlank()
        ) {
            Toast.makeText(this, "Email and Password can't be bank", Toast.LENGTH_LONG).show()
            return
        }
        if (binding.edtPassword.text.toString() != binding.edtPasswordConfim.text.toString()) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_LONG)
                .show()
            return
        }


    }

}