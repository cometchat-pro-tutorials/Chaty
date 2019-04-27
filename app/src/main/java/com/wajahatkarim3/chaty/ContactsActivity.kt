package com.wajahatkarim3.chaty

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.UsersRequest
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User

class ContactsActivity : AppCompatActivity() {

    lateinit var recyclerContacts: RecyclerView
    lateinit var progressLoading: ProgressBar
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: ContactsRecyclerAdapter
    var contactsList = arrayListOf<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        setupViews()
        loadAllUsers()
    }

    fun setupViews()
    {
        // progress bar
        progressLoading = findViewById(R.id.progressLoading)

        // RecyclerView
        recyclerContacts = findViewById(R.id.recyclerContacts)
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerContacts.layoutManager = layoutManager
        recyclerAdapter = ContactsRecyclerAdapter(this)
        recyclerContacts.adapter = recyclerAdapter
    }

    fun loadAllUsers()
    {
        // Show Progress Bar
        progressLoading.visibility = View.VISIBLE
        recyclerContacts.visibility = View.GONE

        // Load All Users from Comet Chat
        var usersRequest = UsersRequest.UsersRequestBuilder().setLimit(10).build()
        usersRequest.fetchNext(object : CometChat.CallbackListener<List<User>>() {
            override fun onSuccess(usersList: List<User>?) {
                if (usersList != null)
                {
                    var loggedInUser = CometChat.getLoggedInUser()
                    for (user in usersList)
                    {
                        // Don't add yourself (logged in user) in the list
                        if (loggedInUser.uid != user.uid)
                            contactsList.add(user.convertToUserModel())
                    }

                    // Update the Recycler Adapter
                    recyclerAdapter.notifyDataSetChanged()
                }
                else
                {
                    Toast.makeText(this@ContactsActivity, "Couldn't load the users!", Toast.LENGTH_SHORT).show()
                }

                // Hide Progress
                progressLoading.visibility = View.GONE
                recyclerContacts.visibility = View.VISIBLE
            }

            override fun onError(exception: CometChatException?) {

                // Hide Progress
                progressLoading.visibility = View.GONE
                recyclerContacts.visibility = View.VISIBLE

                Toast.makeText(this@ContactsActivity, exception?.localizedMessage ?: "Unknown error occurred!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun loadDummyData()
    {
        contactsList.add(UserModel("John Doe", "Online", "https://cdn.pixabay.com/photo/2013/07/13/10/07/man-156584_960_720.png"))
        contactsList.add(UserModel("Alexa Johnson", "Offline", "https://previews.123rf.com/images/juliasart/juliasart1704/juliasart170400022/75406270-vector-girl-icon-woman-avatar-face-icon-cartoon-style-.jpg"))
        contactsList.add(UserModel("Robert Smith", "Online", "https://i.pinimg.com/originals/a7/0e/16/a70e1675c7bc001f1578aa76bb0a7819.png"))
        contactsList.add(UserModel("Steve Boam", "Online", "https://cdn1.iconfinder.com/data/icons/people-faces-2/512/11-512.png"))
        recyclerAdapter.notifyDataSetChanged()
    }

    inner class ContactsRecyclerAdapter(val context: Context) : RecyclerView.Adapter<ContactsRecyclerAdapter.ContactViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            return ContactViewHolder(LayoutInflater.from(this@ContactsActivity).inflate(R.layout.contact_item_layout, parent, false))
        }

        override fun getItemCount(): Int = contactsList.size

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            var user = contactsList[position]
            holder.bindItem(user) {
                var intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("name", user.name)
                intent.putExtra("status", user.status)
                intent.putExtra("photo", user.photoUrl)
                context.startActivity(intent)
            }
        }

        inner class ContactViewHolder : RecyclerView.ViewHolder
        {
            var txtUsername: AppCompatTextView
            var txtStatus: AppCompatTextView
            var imgContactPhoto: AppCompatImageView
            var userItemClickCallback: (() -> Unit)? = null

            constructor(itemView: View) : super(itemView)
            {
                txtUsername = itemView.findViewById(R.id.txtUsername)
                txtStatus = itemView.findViewById(R.id.txtStatus)
                imgContactPhoto = itemView.findViewById(R.id.imgContactPhoto)

                var cardRootLayout = itemView.findViewById<ConstraintLayout>(R.id.cardRootLayout)
                cardRootLayout.setOnClickListener {
                    userItemClickCallback?.invoke()
                }
            }

            fun bindItem(userModel: UserModel, callback: () -> Unit)
            {
                userItemClickCallback = callback
                txtUsername.text = userModel.name
                txtStatus.text = userModel.status
                if (userModel.photoUrl != null && !userModel.photoUrl.isEmpty())
                {
                    // Load Avatar Image if any
                    Glide.with(context)
                        .asBitmap()
                        .load(userModel.photoUrl)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                imgContactPhoto.setImageBitmap(resource)
                            }

                            override fun onLoadFailed(errorDrawable: Drawable?) {
                                super.onLoadFailed(errorDrawable)
                                Log.e("Chaty", "Avatar Image Loading Failed")
                            }
                        })
                }
                else
                {
                    // Generate Letter Avatar
                    setAvatarImage(userModel.name[0].toString())
                    if (userModel.status.equals("Online"))
                        txtStatus.setTextColor(context.resources.getColor(R.color.colorOnline))
                    else
                        txtStatus.setTextColor(context.resources.getColor(R.color.colorOffline))
                }
            }

            fun setAvatarImage(letter: String)
            {
                var generator = ColorGenerator.MATERIAL
                var color = generator.randomColor

                var drawable = TextDrawable.builder().buildRect(letter, color)
                imgContactPhoto.setImageDrawable(drawable)
            }
        }
    }

}
