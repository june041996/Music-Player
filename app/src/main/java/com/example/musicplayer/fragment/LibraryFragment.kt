package com.example.musicplayer.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentLibraryBinding
import com.example.musicplayer.utils.Contanst


class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lnOnDevice.setOnClickListener() {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToOnDeviceFragment())
        }
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(R.menu.library_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.addPlaylist -> {
                        Log.d(Contanst.TAG, "add")
                        createDialog(object : OnSubmitBtnClick {
                            override fun onClick(name: String) {
                                Log.d(Contanst.TAG, "name playlist: $name")
                            }
                        })
                        //findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun createDialog(onSubmitBtnClick: OnSubmitBtnClick) {
        val dialog = Dialog(requireContext()).apply {
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

    interface OnSubmitBtnClick {
        fun onClick(name: String)
    }
}