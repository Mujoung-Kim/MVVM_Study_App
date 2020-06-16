package company.domain.mvvmstudyapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import company.domain.mvvmstudyapp.util.NoInternetException

import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")

        return chain.proceed(chain.request())

    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        var result = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork).apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                        else -> false

                    }
                }
            }
            return result

            //  the other way
           /* val netWork = connectivityManager?.activeNetwork ?: return false
            val activityNetwork =
                connectivityManager.getNetworkCapabilities(netWork) ?: return false

            return when {
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false

            }*/
        } else {
            connectivityManager?.activeNetworkInfo.also {
                return it != null && it.isConnected

            }
        }
    }
}