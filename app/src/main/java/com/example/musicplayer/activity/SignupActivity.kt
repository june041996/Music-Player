package com.example.musicplayer.activity


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.ActivitySignupBinding
import com.example.musicplayer.model.User
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
            if (isValidData()) {
                viewModel.register(
                    binding.edtEmail.text.toString(),
                    binding.edtAddress.text.toString(),
                    )
            }
        }
        viewModel.isSuccessful.observe(this, Observer {
            //handle
            var message = ""
            if (it == true) {
                viewModel.insertUser(
                    User(
                        null,
                        binding.edtEmail.text.toString(),
                        binding.edtPassword.text.toString(),
                        binding.edtName.text.toString(),
                        binding.edtAddress.text.toString()
                    )
                )
                message = "Sign Up Success!"
                startActivity(Intent(this, SigninActivity::class.java))
                finish()
            } else {
                message = "Sign Up Failed!"
            }
            Toast.makeText(application, message, Toast.LENGTH_LONG).show()
        })
    }


    private fun isValidData(): Boolean {
        //check pass
        if (TextUtils.isEmpty(binding.edtName.text.toString())) {
            binding.edtName.setError("Please enter Email")
            return false
        } else if (TextUtils.isEmpty(binding.edtAddress.text.toString())) {
            binding.edtAddress.setError("Please enter Password")
            return false
        } else if (TextUtils.isEmpty(binding.edtEmail.text.toString())) {
            binding.edtEmail.setError("Please enter Password")
            return false
        } else if (TextUtils.isEmpty(binding.edtPassword.text.toString())) {
            binding.edtPassword.setError("Please enter Password")
            return false
        } else if (TextUtils.isEmpty(binding.edtPasswordConfim.text.toString())) {
            binding.edtPasswordConfim.setError("Please enter Password")
            return false
        }


        if (binding.edtPassword.text.toString() != binding.edtPasswordConfim.text.toString()) {
//            showDialog("Password and Confirm Password do not match")
            binding.edtPassword.setError("Password and Confirm Password do not match")
            binding.edtPasswordConfim.setError("Password and Confirm Password do not match")
            return false
        }
        return true

    }

    //showdialog
    fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login")
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}