package com.wajahatkarim3.chaty

import com.cometchat.pro.models.User

fun User.convertToUserModel() : UserModel
{
    return UserModel (
        name = name,
        photoUrl = avatar
    )
}