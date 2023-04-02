package com.tashila.pleasewait

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.View.GONE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tashila.pleasewait.databinding.DialogProgressBinding

class ProgressDialog : DialogFragment() {
    private lateinit var binding: DialogProgressBinding
    var title = ""
    var message = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogProgressBinding.inflate(layoutInflater)

        //build dialog
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)
            .create()

        //set params
        if (title.isEmpty() and message.isEmpty())
            binding.textsLayout.visibility = GONE
        else {
            binding.title.text = title
            binding.message.text = message
        }

        return dialog
    }

    fun show(context: Context) {
        val fragmentManager = (context as? FragmentActivity)?.supportFragmentManager
        show(fragmentManager!!, null)
    }

}