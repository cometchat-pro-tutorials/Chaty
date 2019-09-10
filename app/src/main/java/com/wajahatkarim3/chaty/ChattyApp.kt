package com.wajahatkarim3.chaty

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException

class ChattyApp : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()

        CometChat.init(this, getString(R.string.comet_app_id), object : CometChat.CallbackListener<String>() {
            override fun onSuccess(p0: String?) {
                Log.d("ChattyApp", "Initialization completed successfully!")
            }

            override fun onError(exception: CometChatException?) {
                Log.e("ChattyApp", "Initialization failed with !" + exception?.localizedMessage)
            }
        });

        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?)
    {
        if (activity is ChatActivity)
        {
            // Setting Chat On for notifications
            var editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
            editor.putBoolean("isChatOn", true)
            editor.commit()
        }
        else
        {
            // Setting Chat Off for notifications
            var editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
            editor.remove("isChatOn")
            editor.remove("chatUserId")
            editor.commit()
        }
    }

    override fun onActivityPaused(activity: Activity?)
    {
        // Setting Chat Off for notifications
        var editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.remove("isChatOn")
        editor.remove("chatUserId")
        editor.commit()
    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }
}