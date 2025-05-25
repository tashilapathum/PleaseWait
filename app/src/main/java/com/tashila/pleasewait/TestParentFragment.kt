package com.tashila.pleasewait

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tashila.pleasewait.sample.databinding.FragmentTestBinding

class TestParentFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        binding.showFragment.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainer.id, TestChildFragment(), "TestChildFragment")
                .commit()
        }
        binding.showDialog.setOnClickListener {
            (activity as TestFragmentActivity).loadingDialog.show()
        }
        return binding.root
    }

}