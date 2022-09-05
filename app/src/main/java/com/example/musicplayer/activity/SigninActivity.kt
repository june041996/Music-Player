package com.example.musicplayer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.ActivitySigninBinding
import com.example.musicplayer.vm.AuthViewModel


class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init viewmodel here
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        //neu nhu user da dang nhap thanh cong thi lay lai cai thong tin dang nhap lan truoc de fill vao textview
        binding.edtUser.setText(viewModel.getLoginInfo().mail)
        binding.edtPassword.setText(viewModel.getLoginInfo().password)


        binding.btnSignin.setOnClickListener {
            Log.d("login", "Login here")
            if (isValidData()) {
                //REQUEST login firebase here
                viewModel.requestLogin(binding.edtUser.text.toString(), binding.edtPassword.text.toString())
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(application, "Vui lòng nhập đúng!", Toast.LENGTH_LONG).show()
            }
        }

        //khi dang nhap thanh cong hoac that bai
        viewModel.isSuccessful.observe(this, Observer {
            //handle
            var message = ""
            if (it == true) {
                message = "Đăng nhập thành công!"
            } else {
                message = "Đăng nhập thất bại!"
            }

            Toast.makeText(application, message, Toast.LENGTH_LONG).show()
        })

//
//        val accessToken = AccessToken.getCurrentAccessToken()
//        val isLoggedIn = accessToken != null && !accessToken.isExpired


    }


    //check valid data
    //true valid - failed invalid
    private fun isValidData(): Boolean {
        if (binding.edtUser.text.isNullOrEmpty() || binding.edtPassword.text.isNullOrEmpty()) {
            return false
        }

        return true
    }
}