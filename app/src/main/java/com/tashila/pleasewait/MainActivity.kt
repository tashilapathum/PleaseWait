package com.tashila.pleasewait

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
        binding.show.setOnClickListener {
            showProgressDialog()
            updateProgress()
        }
    }

    private fun showProgressDialog() {
        progressDialog = PleaseWaitDialog(this)
        progressDialog.setTitle(getTitleText())
        progressDialog.setMessage(getMessageText())
        progressDialog.setProgressStyle(getProgressStyle())
        progressDialog.setIndeterminate(getIndeterminate())
        progressDialog.show()
    }

    private fun getTitleText(): String {
        return binding.title.editText!!.text.toString().trim()
    }

    private fun getMessageText(): String {
        return binding.message.editText!!.text.toString().trim()
    }

    private fun getIndeterminate(): Boolean {
        return binding.indeterminate.isChecked
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

    private fun updateProgress() {
        if (binding.showProgress.isChecked) {
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
            handler.postDelayed(runnable, 2000)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}