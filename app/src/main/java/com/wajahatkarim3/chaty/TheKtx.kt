package com.wajahatkarim3.chaty

import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.models.BaseMessage
import com.cometchat.pro.models.TextMessage
import com.cometchat.pro.models.User

fun User.convertToUserModel() : UserModel
{
    return UserModel (
        uid = uid,
        name = name,
        photoUrl = avatar,
        status = status
    )
}

fun TextMessage.convertToMessageModel() : MessageModel
{
    return MessageModel(
        message = text,
        isMine = sender.uid == CometChat.getLoggedInUser().uid
    )
}

fun Any.getCombinedID(id1: String, id2:String) : String
{
    var newid = ""

    var list = ArrayList<String>()
    list.add(id1)
    list.add(id2)
    list.sort()

    newid = list[0] + "-" + list[1]

    return newid
}

fun Any.getUniqueListenerId(uid: String) : String
{
    return getCombinedID(CometChat.getLoggedInUser().uid, uid)
}

fun Any.searchUserWithId(usersList: List<UserModel>, uid: String) : Int?
{
    var i = 0
    for (user in usersList)
    {
        if (user.uid == uid)
            return i
        i++
    }
    return null
}
