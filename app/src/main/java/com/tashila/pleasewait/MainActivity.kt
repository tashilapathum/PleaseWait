package com.tashila.pleasewait

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.tashila.pleasewait.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.show.setOnClickListener {
            showProgressDialog()
        }
    }

    private fun showProgressDialog() {
        val oldProgressDialog = android.app.ProgressDialog(this)
        val progressDialog = PleaseWaitDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loading...")
        progressDialog.show()
    }
}