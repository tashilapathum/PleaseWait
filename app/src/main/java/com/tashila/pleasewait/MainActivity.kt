package com.tashila.pleasewait

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            showCustom.setOnClickListener {
                showCustomDialog()
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

    companion object {
        const val TAG = "MainActivity"
    }
}