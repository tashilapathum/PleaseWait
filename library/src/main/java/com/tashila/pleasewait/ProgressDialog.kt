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

public class ProgressDialog() : DialogFragment() {
    private lateinit var binding: DialogProgressBinding
    private var context: Context? = null
    public var title = ""
    public var message = ""

    constructor(context: Context) : this() {
        this.context = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogProgressBinding.inflate(layoutInflater)

        //build dialog
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)
            .create()

        // Restore saved state
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(ARG_TITLE) ?: ""
            message = savedInstanceState.getString(ARG_MESSAGE) ?: ""
        }

        //set params
        if (title.isEmpty() and message.isEmpty())
            binding.textsLayout.visibility = GONE
        else {
            binding.title.text = title
            binding.message.text = message
        }

        return dialog
    }

    public fun show() {
        val fragmentManager = (context as? FragmentActivity)?.supportFragmentManager
        show(fragmentManager!!, null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_TITLE, title)
        outState.putString(ARG_MESSAGE, message)
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
    }

}