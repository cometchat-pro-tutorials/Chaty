package com.wajahatkarim3.chaty

import android.app.Application
import android.util.Log
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException

class ChattyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        CometChat.init(this, Constants.COMET_APP_ID, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(p0: String?) {
                Log.d("ChattyApp", "Initialization completed successfully!")
            }

            override fun onError(exception: CometChatException?) {
                Log.e("ChattyApp", "Initialization failed with !" + exception?.localizedMessage)
            }
        });
    }
}