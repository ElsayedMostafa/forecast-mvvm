package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.ApixWeatherApiService
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl (private val apixWeatherApiService: ApixWeatherApiService): WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
           try {
               val fetchedCurrentWeather = apixWeatherApiService
                   .getCurrentWeather(location,languageCode)
                   .await()
                _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
           }
           catch (e:NoConnectivityException){
               Log.e("connectivity","No internet connection.", e)
           }

    }
}