package com.example.tmdb_challenge.modules.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.tmdb_challenge.base.BaseFragment
import com.example.tmdb_challenge.databinding.FragmentSplashBinding
import com.example.tmdb_challenge.util.observeEvent
import com.example.tmdb_challenge.util.withViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initView() {
        super.initView()
        getViewModel().downloadConfigurations()
    }

    override fun getViewModel(): SplashViewModel = withViewModel {
        observeEvent(downloadFinished) { navigateToHome() }
    }

    private fun navigateToHome() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
    }
}