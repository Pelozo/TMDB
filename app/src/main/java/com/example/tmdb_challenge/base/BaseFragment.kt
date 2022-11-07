package com.example.tmdb_challenge.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    var baseViewModel: BaseViewModel? = null

    private var _binding: T? = null
    val binding get() = _binding!!

    abstract fun getViewModel(): BaseViewModel

    protected open fun setUp(arguments: Bundle?) = Unit

    abstract fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBindingView(inflater, container, savedInstanceState)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel = getViewModel()
        setUp(arguments)
    }

    protected open fun initView() = Unit

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




