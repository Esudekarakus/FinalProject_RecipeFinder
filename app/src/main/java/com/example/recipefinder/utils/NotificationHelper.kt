package com.example.recipefinder.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import com.example.recipefinder.R
import com.example.recipefinder.models.RecipeModel

object NotificationHelper {
    private const val CHANNEL_ID = "recipe_finder_channel"
    private const val NOTIFICATION_ID = 123

    fun showDeleteNotification(context: Context, navController: NavController, recipe: RecipeModel) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Recipe Removed")
            .setContentText("The recipe has been successfully removed from list")
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, builder.build())

        // Yönlendirme işlemi
        navController.popBackStack()
    }
}
