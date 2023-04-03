package com.tashila.pleasewait

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        //val progressDialog = android.app.ProgressDialog(this)
        val progressDialog = ProgressDialog()
        binding.show.setOnClickListener {
            progressDialog.show(this)
        }
    }
}