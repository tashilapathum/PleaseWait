package com.tashila.pleasewait

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tashila.pleasewait.databinding.FragmentTest2Binding

class TestChildFragment : Fragment() {
    private lateinit var binding: FragmentTest2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTest2Binding.inflate(inflater, container, false)
        binding.show.setOnClickListener {
            (activity as TestFragmentActivity).loadingDialog.show()
        }
        return binding.root
    }

}