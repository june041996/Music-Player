package com.example.musicplayer.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.musicplayer.R

class CustomDialog(val context: Context) {
    fun createInputDialog(onSubmitBtnClick: OnSubmitBtnClick) {
        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_create_playlist)
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.gravity = Gravity.CENTER
            }
            setCancelable(true)
        }
        dialog.show()
        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener() {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btnOk).setOnClickListener() {
            onSubmitBtnClick.onClick(dialog.findViewById<EditText>(R.id.edtName).text.toString())
            dialog.dismiss()
        }
    }

    fun createEditDialog(oldName: String, onSubmitBtnClick: OnSubmitBtnClick) {
        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_create_playlist)
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.gravity = Gravity.CENTER
            }
            setCancelable(true)
        }
        val edt = dialog.findViewById<EditText>(R.id.edtName)
        edt.setText(oldName)
        dialog.show()
        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener() {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btnOk).setOnClickListener() {
            onSubmitBtnClick.onClick(edt.text.toString())
            dialog.dismiss()
        }
    }

    fun createConfirmDialog(onSubmitBtnClick: OnSubmitBtnClick) {
        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_confirm_delete_favourite)
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.gravity = Gravity.CENTER
            }
            setCancelable(true)
        }
        dialog.show()
        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener() {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btnOk).setOnClickListener() {
            onSubmitBtnClick.onClick("")
            dialog.dismiss()
        }
    }

    interface OnSubmitBtnClick {
        fun onClick(name: String)
    }
}