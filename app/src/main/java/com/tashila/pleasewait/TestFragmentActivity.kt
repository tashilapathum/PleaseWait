package com.tashila.pleasewait

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tashila.pleasewait.databinding.ActivityTestFragmentBinding

class TestFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestFragmentBinding
    val loadingDialog by lazy { PleaseWaitDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestFragmentBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer.id, TestParentFragment())
            .commit()
    }
}