package com.tashila.pleasewait

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tashila.pleasewait.databinding.FragmentTestBinding

class TestParentFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private val loadingDialog by lazy { PleaseWaitDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        binding.showFragment.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainer.id, TestChildFragment(), "TestFragment2")
                .commit()
        }
        binding.showDialog.setOnClickListener { loadingDialog.show() }
        return binding.root
    }

}