package com.tashila.pleasewait

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tashila.pleasewait.databinding.PleaseWaitDialogBinding

@SuppressLint("UseRequireInsteadOfGet")
public class PleaseWaitDialog() : DialogFragment() {
    private lateinit var binding: PleaseWaitDialogBinding
    private var context: Context? = null
    private var title = ""
    private var message = ""

    constructor(context: Context) : this() {
        this.context = context
        title = context.getString(R.string.please_wait_dialog_default_title)
        message = context.getString(R.string.please_wait_dialog_default_message)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PleaseWaitDialogBinding.inflate(layoutInflater)

        //build dialog
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)
            .create()

        // Restore saved state
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(ARG_TITLE) ?: ""
            message = savedInstanceState.getString(ARG_MESSAGE) ?: ""
        }

        //hide unnecessary views
        if (title.isEmpty() and message.isEmpty())
            binding.textsLayout.visibility = GONE
        if (title.isEmpty())
            binding.title.visibility = GONE
        if (message.isEmpty())
            binding.message.visibility = GONE

        //set params
        binding.title.text = title
        binding.message.text = message

        return dialog
    }

    public fun setTitle(title: String) {
        this.title = title
    }

    public fun setMessage(message: String) {
        this.message = message
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