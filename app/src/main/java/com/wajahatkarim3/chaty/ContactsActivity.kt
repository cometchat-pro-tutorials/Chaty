package com.wajahatkarim3.chaty

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide

class ContactsActivity : AppCompatActivity() {

    lateinit var recyclerContacts: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: ContactsRecyclerAdapter
    var contactsList = arrayListOf<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        setupViews()
        loadDummyData()
    }

    fun setupViews()
    {
        // RecyclerView
        recyclerContacts = findViewById(R.id.recyclerContacts)
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerContacts.layoutManager = layoutManager
        recyclerAdapter = ContactsRecyclerAdapter(this)
        recyclerContacts.adapter = recyclerAdapter
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
            holder.bindItem(contactsList[position])
        }

        inner class ContactViewHolder : RecyclerView.ViewHolder
        {
            var txtUsername: AppCompatTextView
            var txtStatus: AppCompatTextView
            var imgContactPhoto: AppCompatImageView

            constructor(itemView: View) : super(itemView)
            {
                txtUsername = itemView.findViewById(R.id.txtUsername)
                txtStatus = itemView.findViewById(R.id.txtStatus)
                imgContactPhoto = itemView.findViewById(R.id.imgContactPhoto)
            }

            fun bindItem(userModel: UserModel)
            {
                txtUsername.text = userModel.name
                txtStatus.text = userModel.status
                setAvatarImage(userModel.name[0].toString())
                if (userModel.status.equals("Online"))
                    txtStatus.setTextColor(context.resources.getColor(R.color.colorOnline))
                else
                    txtStatus.setTextColor(context.resources.getColor(R.color.colorOffline))
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
