package com.tashila.pleasewait

import android.os.Bundle
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
        val progressDialog = ProgressDialog(this)
        binding.show.setOnClickListener {
            progressDialog.show()
        }
    }
}