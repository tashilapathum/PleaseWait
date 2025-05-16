package com.tashila.pleasewait

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tashila.pleasewait.PleaseWaitDialog.ProgressStyle
import com.tashila.pleasewait.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressDialog: PleaseWaitDialog
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var progress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        init()
    }

    private fun init() {
        //to show progress
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (progress == 100) {
                    try {
                        progressDialog.dismiss()
                    } catch (e: IllegalStateException) {
                        Log.e(TAG, "DialogFragment not associated with a fragment manager")
                    }
                    progress = 0
                    handler.removeCallbacks(this)
                } else {
                    progress += 10
                    progressDialog.setProgress(progress)
                    handler.postDelayed(this, 2000)
                }
            }
        }

        //onClick listeners
        binding.apply {
            show.setOnClickListener {
                showProgressDialog()
                updateProgress()
            }
            showLive.setOnClickListener {
                showDialogWithUpdates()
            }
            showCustom.setOnClickListener {
                showCustomDialog()
            }
            fragment.setOnClickListener {
                startActivity(Intent(this@MainActivity, TestFragmentActivity::class.java))
            }
            javaActivity.setOnClickListener {
                startActivity(Intent(this@MainActivity, JavaMainActivity::class.java))
            }
        }
    }

    private fun showProgressDialog() {
        handler.removeCallbacks(runnable)

        progressDialog = PleaseWaitDialog(this)
        progressDialog.setTitle(getTitleText())
        progressDialog.setMessage(getMessageText())
        progressDialog.setProgressStyle(getProgressStyle())
        progressDialog.setIndeterminate(getIndeterminateProgressStyle(), false)
        progressDialog.setShowDelay(getShowDelay())
        progressDialog.setDismissDelay(getDismissDelay())
        progressDialog.show()
        //progressDialog.dismiss() //call dismiss to test DismissDelay
    }

    private fun getTitleText(): String {
        return binding.title.editText!!.text.toString().trim()
    }

    private fun getMessageText(): String {
        return binding.message.editText!!.text.toString().trim()
    }

    private fun getIndeterminateProgressStyle(): Int {
        if (binding.chipBoth.isChecked)
            return PleaseWaitDialog.ProgressStyle.BOTH
        if (binding.chipCircular.isChecked)
            return PleaseWaitDialog.ProgressStyle.CIRCULAR
        if (binding.chipLinear.isChecked)
            return PleaseWaitDialog.ProgressStyle.LINEAR

        return PleaseWaitDialog.ProgressStyle.NONE
    }

    private fun getProgressStyle(): Int {
        if (binding.circular.isChecked and binding.linear.isChecked)
            return PleaseWaitDialog.ProgressStyle.BOTH
        if (binding.circular.isChecked)
            return PleaseWaitDialog.ProgressStyle.CIRCULAR
        if (binding.linear.isChecked)
            return PleaseWaitDialog.ProgressStyle.LINEAR

        return PleaseWaitDialog.ProgressStyle.NONE
    }

    private fun getShowDelay(): Long {
        val text = binding.showDelay.editText!!.text
        return if (text.isNotEmpty())
            text.toString().toLong()
        else
            0L
    }

    private fun getDismissDelay(): Long {
        val text = binding.dismissDelay.editText!!.text
        return if (text.isNotEmpty())
            text.toString().toLong()
        else
            0L
    }

    private fun updateProgress() {
        if (binding.showProgress.isChecked)
            handler.postDelayed(runnable, 2000)
    }

    private fun showCustomDialog() {
        val customDialog = CustomProgressDialog(this)
        customDialog.show()
    }

    fun showDialogWithUpdates() {
        val dialog = PleaseWaitDialog(this).apply {
            // Initial setup
            setTitle("Initial Title")
            setMessage("Initial Message")
            setProgressStyle(ProgressStyle.BOTH)
            setIndeterminate(true)
            setProgress(0)

            // Show the dialog immediately
            show()
        }

        // Update values while dialog is showing
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.apply {
                setTitle("Updated Title 1")
                setMessage("Updated Message 1")
                setProgress(25)
                setIndeterminate(false)
            }
        }, 1000)

        // Change progress style and update again
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.apply {
                setProgressStyle(ProgressStyle.LINEAR)
                setTitle("Updated Title 2")
                setMessage("Updated Message 2")
                setProgress(50)
            }
        }, 2000)

        // Test indeterminate states
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.apply {
                setIndeterminate(ProgressStyle.CIRCULAR, true)
                setIndeterminate(ProgressStyle.LINEAR, false)
                setProgress(75)
            }
        }, 3000)

        // Test hiding progress bars completely
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.apply {
                setProgressStyle(ProgressStyle.NONE)
                setTitle("Updated Title 3")
                setMessage("Updated Message 3")
            }
        }, 4000)

        // Show progress bars back again
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.apply {
                setProgressStyle(ProgressStyle.BOTH)
            }
        }, 5000)

        // Hide all the text
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.apply {
                setTitle("")
                setMessage("")
            }
        }, 6000)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}