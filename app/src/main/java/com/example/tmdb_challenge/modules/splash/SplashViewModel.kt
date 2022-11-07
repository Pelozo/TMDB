package com.example.tmdb_challenge.modules.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tmdb_challenge.base.BaseViewModel
import com.example.tmdb_challenge.modules.splash.SplashConstants.Companion.SPLASH_SCREEN_DELAY_MILLISECONDS
import com.example.tmdb_challenge.usecases.GetConfigurationUseCase
import com.example.tmdb_challenge.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getConfigurationsUseCase: GetConfigurationUseCase
) : BaseViewModel() {
    private val downloadConfigurationsMLD = MutableLiveData<Event<Unit>>()

    fun downloadConfigurations() {
        println("viewmodel")
        viewModelScope.launch(Dispatchers.IO) {
            getConfigurationsUseCase.invoke()
            downloadConfigurationsMLD.postValue(Event(Unit))
        }
    }

    val downloadFinished: LiveData<Event<Unit>> = downloadConfigurationsMLD
}