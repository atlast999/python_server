package vn.com.vti.smartta.service.cloudmessaging

import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class SmartTaCloudMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("Refreshed token: $token")
        //TODO send new token to server
    }

}