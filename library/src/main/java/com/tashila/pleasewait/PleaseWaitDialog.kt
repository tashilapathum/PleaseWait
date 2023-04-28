package com.tashila.pleasewait

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.tashila.pleasewait.databinding.PleaseWaitDialogBinding

public class PleaseWaitDialog() : DialogFragment() {
    private var context: Context? = null
    private lateinit var binding: PleaseWaitDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var progressCircle: CircularProgressIndicator

    //values set by the user
    private var title = ""
    private var message = ""
    private var progress = 0
    private var isIndeterminate = true
    private var isLinearIndeterminate = true
    private var isCircularIndeterminate = true
    private var progressStyle = ProgressStyle.CIRCULAR

    constructor(context: Context) : this() {
        this.context = context
        title = context.getString(R.string.please_wait_dialog_default_title)
        message = context.getString(R.string.please_wait_dialog_default_message)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PleaseWaitDialogBinding.inflate(layoutInflater)

        // Restore saved state
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(ARG_TITLE) ?: ""
            message = savedInstanceState.getString(ARG_MESSAGE) ?: ""
            progress = savedInstanceState.getInt(ARG_PROGRESS)
            progressStyle = savedInstanceState.getInt(ARG_PROGRESS_STYLE)
            isIndeterminate = savedInstanceState.getBoolean(ARG_IS_INDETERMINATE)
            isLinearIndeterminate = savedInstanceState.getBoolean(ARG_IS_LINEAR_INDETERMINATE)
            isCircularIndeterminate = savedInstanceState.getBoolean(ARG_IS_CIRCULAR_INDETERMINATE)
        }

        //build dialog
        dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)
            .create()

        init()

        return dialog
    }

    private fun init() {
        //find views
        progressBar = binding.progressBar
        progressCircle = binding.progressCircle
        if (progress > 0) setProgress(progress) //from onRestoreSavedInstanceState

        //Hide unnecessary view so the remaining content get centered properly
        if (title.isEmpty() and message.isEmpty())
            binding.textsLayout.visibility = GONE
        if (title.isEmpty())
            binding.title.visibility = GONE
        if (message.isEmpty())
            binding.message.visibility = GONE

        /*set params*/
        //title
        binding.title.text = title
        //message
        binding.message.text = message
        //indeterminate state
        progressBar.isIndeterminate = isIndeterminate
        progressCircle.isIndeterminate = isIndeterminate
        progressBar.isIndeterminate = isLinearIndeterminate
        progressCircle.isIndeterminate = isCircularIndeterminate
        //progress style
        when(progressStyle) {
            ProgressStyle.LINEAR -> {
                progressCircle.visibility = GONE
                progressBar.visibility = VISIBLE
            }
            ProgressStyle.BOTH -> {
                progressBar.visibility = VISIBLE
            }
            ProgressStyle.NONE -> {
                progressCircle.visibility = GONE
                progressBar.visibility = GONE
            }
        }
    }

    /* Setters to customize the behaviour and the appearance */

    /**Sets the larger top text*/
    public fun setTitle(title: String) {
        this.title = title
    }

    /**Sets the smaller bottom text*/
    public fun setMessage(message: String) {
        this.message = message
    }

    /**Sets a progress value between 0-100 to show the progress on one or both of circular and linear progress bars.
     * Use setProgressCompat to smoothly transition from indeterminate mode to determinate mode*/
    public fun setProgress(progress: Int) {
        this.progress = progress //for savedInstanceState

        if (!isCircularIndeterminate) { //only circular is determinate
            progressCircle.setProgressCompat(progress, true)
            return
        }

        if (!isLinearIndeterminate) { //only linear is determinate
            progressBar.setProgressCompat(progress, true)
            return
        }

        if (!isIndeterminate) { //both are determinate
            progressCircle.setProgressCompat(progress, true)
            progressBar.setProgressCompat(progress, true)
            return
        }
    }

    /** Sets shown progress bar(s) as indeterminate or not. Default value is true.*/
    public fun setIndeterminate(isIndeterminate: Boolean) {
        this.isIndeterminate = isIndeterminate
    }

    /**Sets the the specifies progressbar(s) as determinate or indeterminate. Both progress bars
     * are indeterminate by default. This method takes precedence over [setIndeterminate]*/
    public fun setIndeterminate(which: Int, isIndeterminate: Boolean) {
        when(which) {
            ProgressStyle.CIRCULAR -> isCircularIndeterminate = isIndeterminate
            ProgressStyle.LINEAR -> isLinearIndeterminate = isIndeterminate
            ProgressStyle.BOTH -> this.isIndeterminate = isIndeterminate
        }
    }

    /** Sets the progress bar style. Default value is [ProgressStyle.CIRCULAR].*/
    public fun setProgressStyle(progressStyle: Int) {
        this.progressStyle = progressStyle
    }

    /** Shows the progress dialog */
    public fun show() {
        val fragmentManager = (context as? FragmentActivity)?.supportFragmentManager
        show(fragmentManager!!, null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_TITLE, title)
        outState.putString(ARG_MESSAGE, message)
        outState.putInt(ARG_PROGRESS, progress)
        outState.putSerializable(ARG_PROGRESS_STYLE, progressStyle)
        outState.putBoolean(ARG_IS_INDETERMINATE, isIndeterminate)
        outState.putBoolean(ARG_IS_LINEAR_INDETERMINATE, isLinearIndeterminate)
        outState.putBoolean(ARG_IS_CIRCULAR_INDETERMINATE, isCircularIndeterminate)
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_PROGRESS = "progress"
        private const val ARG_PROGRESS_STYLE = "progressStyle"
        private const val ARG_IS_INDETERMINATE = "isIndeterminate"
        private const val ARG_IS_LINEAR_INDETERMINATE = "isLinearIndeterminate"
        private const val ARG_IS_CIRCULAR_INDETERMINATE = "isCircularIndeterminate"
    }

    /** Determines the style of the progress bar shown */
    public object ProgressStyle {
        /**Shows only the indeterminate circular progress spinner*/
        const val CIRCULAR = 0
        /**Shows only the linear horizontal progress bar*/
        const val LINEAR = 1
        /**Shows both circular and linear progress bars*/
        const val BOTH = 2
        /**Hides both progress bars and shows only the two texts*/
        const val NONE = 3
    }

}