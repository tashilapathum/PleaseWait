package com.tashila.pleasewait

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.tashila.pleasewait.databinding.PleaseWaitDialogBinding
import java.util.Timer
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor

public open class PleaseWaitDialog() : DialogFragment() {
    private var context: Context? = null
    private lateinit var binding: PleaseWaitDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var progressCircle: CircularProgressIndicator

    //values set by the user
    private var title = ""
    private var message = ""
    private var progress = 0
    private var showDelay = 0L
    private var dismissDelay = 0L
    private var showCalled = false
    private var dismissCalled = false
    private var isIndeterminate = true
    private var isLinearIndeterminate = true
    private var isCircularIndeterminate = true
    private var progressStyle = ProgressStyle.CIRCULAR

    //timers to track dismiss delay and show delay
    private var showTimer: CountDownTimer? = null
    private var dismissTimer: CountDownTimer? = null

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
        setParams()

        return dialog
    }

    private fun init() {
        //find views
        progressBar = binding.progressBar
        progressCircle = binding.progressCircle
        if (progress > 0) setProgress(progress) //when called onRestoreSavedInstanceState

        //Hide unnecessary view so the remaining content get centered properly
        if (title.isEmpty() and message.isEmpty())
            binding.textsLayout.visibility = GONE
        if (title.isEmpty())
            binding.title.visibility = GONE
        if (message.isEmpty())
            binding.message.visibility = GONE
    }

    private fun setParams() {
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
        when (progressStyle) {
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
     * are indeterminate by default.*/
    public fun setIndeterminate(which: Int, isIndeterminate: Boolean) {
        when (which) {
            ProgressStyle.CIRCULAR -> isCircularIndeterminate = isIndeterminate
            ProgressStyle.LINEAR -> isLinearIndeterminate = isIndeterminate
            ProgressStyle.BOTH -> this.isIndeterminate = isIndeterminate
        }
    }

    /** Sets the progress bar style. Default value is [ProgressStyle.CIRCULAR].*/
    public fun setProgressStyle(progressStyle: Int) {
        this.progressStyle = progressStyle
    }

    /**Shows the dialog after the delay specified in milliseconds.
     * - If you set this value and called [dismiss] before the [showDelay] has elapsed,
     * the dialog won't be shown.
     * - The countdown starts after calling [show]*/
    public fun setShowDelay(showDelay: Long) {
        this.showDelay = showDelay
        setShowTimer()
    }

    /**Dismisses the dialog after the delay specified in milliseconds.
     * - If you set this value and called [dismiss], the dialog won't be dismissed until
     * the [dismissDelay] has elapsed.
     * - If a [showDelay] has been set, [dismissDelay] timer starts after [showDelay] has finished.
     * - The countdown starts after calling [show].*/
    public fun setDismissDelay(dismissDelay: Long) {
        if (showDelay == 0L)
            this.dismissDelay = dismissDelay
        else
            this.dismissDelay = dismissDelay + showDelay
        setDismissTimer()
    }

    /** Shows the dialog.
     * If a [showDelay] has been set, the dialog isn't shown and the [showTimer] starts.*/
    public fun show() {
        val fragmentManager = (context as? FragmentActivity)?.supportFragmentManager ?: return
        val existing = fragmentManager.findFragmentByTag(TAG) as? DialogFragment
        existing?.dismissAllowingStateLoss() // Remove if already shown

        if (showDelay == 0L) {
            try {
                show(fragmentManager, TAG)
            } catch (e: IllegalStateException) {
                Log.e(TAG, "show: FragmentManager has been destroyed", e)
            }
        } else {
            showCalled = true
            showTimer?.start()
        }
    }

    /** Dismisses the dialog.
     * If a [dismissDelay] has been set, the dialog doesn't get dismissed and the [dismissTimer] starts.*/
    override fun dismiss() {
        if (dismissDelay == 0L) {
            try {
                showTimer?.cancel()
                dismissTimer?.cancel()
                super.dismiss()
            } catch (e: IllegalStateException) {
                Log.e(TAG, "dismiss: not associated with a fragment manager", e)
            }
        } else {
            dismissCalled = true
            dismissTimer?.start()
        }
    }

    private fun setShowTimer() {
        showTimer = object : CountDownTimer(showDelay, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(TAG, "Showing dialog in $millisUntilFinished...")
            }

            override fun onFinish() {
                showDelay = 0L
                if (!dismissCalled) show()
            }
        }
    }

    private fun setDismissTimer() {
        dismissTimer = object : CountDownTimer(dismissDelay, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(TAG, "Dismissing dialog in $millisUntilFinished...")
            }

            override fun onFinish() {
                dismissDelay = 0L
                dismiss()
            }
        }
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
        private const val TAG = "PleaseWaitDialog"
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
        /**Shows only the circular progress bar*/
        const val CIRCULAR = 0
        /**Shows only the linear progress bar*/
        const val LINEAR = 1
        /**Shows both circular and linear progress bars*/
        const val BOTH = 2
        /**Hides both progress bars and shows only the title and message texts*/
        const val NONE = 3
    }

}