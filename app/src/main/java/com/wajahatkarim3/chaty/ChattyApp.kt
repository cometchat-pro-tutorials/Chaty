package com.wajahatkarim3.chaty

import android.app.Application
import android.util.Log
import com.cometchat.pro.core.AppSettings
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException

class ChattyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val appSettings = AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(resources.getString(R.string.comet_app_region)).build()
        CometChat.init(this, getString(R.string.comet_app_id),appSettings, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(p0: String?) {
                Log.d("ChattyApp", "Initialization completed successfully!")
            }

            override fun onError(exception: CometChatException?) {
                Log.e("ChattyApp", "Initialization failed with !" + exception?.localizedMessage)
            }
        });
    }
}