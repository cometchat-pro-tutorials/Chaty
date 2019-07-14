package com.wajahatkarim3.chaty

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
