package com.example.recipefinder.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ConnectivityReceiver : BroadcastReceiver() {

    companion object {
        // action
        const val CONNECTIVITY_ACTION =
            "com.example.recipefinder.utils.ConnectivityReceiver.CONNECTIVITY_ACTION"
        const val EXTRA_IS_ONLINE = "EXTRA_IS_ONLINE"
    }

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    override fun onReceive(context: Context?, intent: Intent?) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetwork
            val capability = cm.getNetworkCapabilities(networkInfo)
            val isOnline =
                capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            Log.d("ConnectivityReceiver", "isOnline: $isOnline")
            _isConnected.update {
                isOnline
            }

            // send broadcast
            val broadcastIntent = Intent(CONNECTIVITY_ACTION)
            broadcastIntent.putExtra(EXTRA_IS_ONLINE, isOnline)
            context.sendBroadcast(broadcastIntent)
        }
    }

}