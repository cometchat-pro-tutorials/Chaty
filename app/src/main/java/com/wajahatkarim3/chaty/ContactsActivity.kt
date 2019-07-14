package com.wajahatkarim3.chaty

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_contacts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            R.id.menuProfile -> {
                var intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    fun loadAllUsers()
    {
        // Show Progress Bar
        progressLoading.visibility = View.VISIBLE
        recyclerContacts.visibility = View.GONE

        // Load All Users from Comet Chat
        Handler().postDelayed(Runnable {
            loadDummyData()

            // Hide Progress
            progressLoading.visibility = View.GONE
            recyclerContacts.visibility = View.VISIBLE

        }, 2*1000)      // 4 seconds dummy delay
    }

    fun loadDummyData()
    {
        contactsList.add(UserModel(name = "John Doe", status = "online", photoUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/07/man-156584_960_720.png"))
        contactsList.add(UserModel(name = "Alexa Johnson", status = "offline", photoUrl = "https://previews.123rf.com/images/juliasart/juliasart1704/juliasart170400022/75406270-vector-girl-icon-woman-avatar-face-icon-cartoon-style-.jpg"))
        contactsList.add(UserModel(name = "Robert Smith", status = "offline", photoUrl = "https://i.pinimg.com/originals/a7/0e/16/a70e1675c7bc001f1578aa76bb0a7819.png"))
        contactsList.add(UserModel(name = "Steve Boam", status = "online", photoUrl = "https://cdn1.iconfinder.com/data/icons/people-faces-2/512/11-512.png"))
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
                intent.putExtra("uid", user.uid)
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

                if (userModel.photoUrl != null && !userModel.photoUrl.isEmpty())
                {
                    // Load Avatar Image if any
                    Glide.with(context)
                        .asBitmap()
                        .load(userModel.photoUrl)
                        .into(imgContactPhoto)
                }
                else
                {
                    // Generate Letter Avatar
                    setAvatarImage(userModel.name[0].toString())
                }

                txtStatus.text = userModel.status
                if (userModel.status.equals("online"))
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
