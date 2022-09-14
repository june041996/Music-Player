package com.example.musicplayer.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.ActivitySigninBinding
import com.example.musicplayer.vm.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var sharedpreferences: SharedPreferences
    private val SHARED_PREFS = "shared_prefs"
    private var check: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = FirebaseAuth.getInstance().currentUser

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        check = sharedpreferences.getString("check", "")
        if (check.equals("true")) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        //init viewmodel here
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        //neu nhu user da dang nhap thanh cong thi lay lai cai thong tin dang nhap lan truoc de fill vao textview
        binding.edtUser.setText(viewModel.getLoginInfo().mail)
        binding.edtPassword.setText(viewModel.getLoginInfo().password)

        binding.btnSignin.setOnClickListener {
            Log.d("login", "Login here")
            if (isValidData()) {
                //REQUEST login firebase here
                viewModel.requestLogin(
                    binding.edtUser.text.toString(),
                    binding.edtPassword.text.toString()
                )
            }

        }
        binding.txtSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        //khi dang nhap thanh cong hoac that bai
        viewModel.isSuccessful.observe(this, Observer {
            //handle
            var message = ""
            Log.d("edtUser", binding.edtUser.text.toString())
            if (it == true) {
                viewModel.getUser(binding.edtUser.text.toString())
                viewModel.user.observe(this) {
                    editor.putInt("id", it.idUser!!)
                    editor.putString("username", it.email)
                    if (binding.cbRemember.isChecked) {
                        editor.putString("check", "true")
                    }
                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
//                message = "Logged in successfully!"
            } else {
                showDialog("your account or password is wrong")
            }

        })

    }

    //check valid data
    //true valid - failed invalid
    private fun isValidData(): Boolean {

        if (TextUtils.isEmpty(binding.edtUser.text.toString())) {
            binding.edtUser.setError("Please enter Email")
            return false
        } else if (TextUtils.isEmpty(binding.edtPassword.text.toString())) {
            binding.edtPassword.setError("Please enter Password")
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


