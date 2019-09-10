package com.wajahatkarim3.chaty.fcm

import android.preference.PreferenceManager
import android.util.Log
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.BaseMessage
import com.cometchat.pro.models.TextMessage
import com.cometchat.pro.pushnotifications.core.PNExtension
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wajahatkarim3.chaty.R
import io.karn.notify.Notify
import org.json.JSONObject

class FcmListenerService : FirebaseMessagingService()
{
    val notificationId = 10034

    override fun onNewToken(token: String)
    {
        Log.w("onNewToken", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("onMessageReceived", "Message Received -  " + remoteMessage.from)

        // Send the notification
        PNExtension.getMessageFromJson(JSONObject(remoteMessage.data["message"]), object : CometChat.CallbackListener<BaseMessage>(){
            override fun onSuccess(baseMessage: BaseMessage?) {
                when (baseMessage)
                {
                    is TextMessage -> {

                        // Generate Text message received push notification
                        var textMessage = baseMessage

                        var prefs = PreferenceManager.getDefaultSharedPreferences(this@FcmListenerService)
                        if ( (prefs.getBoolean("isChatOn", false) && prefs.getString("chatUserId", "").equals(textMessage.sender.uid) )
                            || prefs.getBoolean("mute-${textMessage.sender.uid}", false))
                        {
                            // Don't send the notification as the chat is already active or the contact is muted for notifications

                        }
                        else
                        {
                            // Send the notification
                            Notify
                                .with(this@FcmListenerService)
                                .content {
                                    title = textMessage.sender.name
                                    text = textMessage.text
                                }
                                .alerting("high_priority_notification") {
                                    channelImportance = Notify.IMPORTANCE_HIGH
                                }
                                .show(notificationId)
                        }
                    }
                }
            }

            override fun onError(exception: CometChatException?) {
                exception?.printStackTrace()
            }
        })
    }
}