package com.mehedi.tlecevideo.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


@Suppress("DEPRECATION")
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB


    @get:LayoutRes
    abstract val layoutId: Int

    lateinit var loading: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        loading = ProgressDialog(requireContext())
        return binding.root
    }


}

