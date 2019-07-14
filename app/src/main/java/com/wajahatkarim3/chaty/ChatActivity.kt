package com.wajahatkarim3.chaty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ChatActivity : AppCompatActivity()
{
    var user: UserModel? = null
    lateinit var txtMessageBox: AppCompatEditText
    lateinit var btnSend: AppCompatImageView
    lateinit var recyclerMessages: RecyclerView
    lateinit var progressLoading: ProgressBar

    val messagesList = arrayListOf<MessageModel>()
    var messagesAdapter: ChatMessagesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        if (intent.extras != null)
        {
            user = UserModel (
                uid = intent.getStringExtra("uid"),
                name = intent.getStringExtra("name"),
                status = intent.getStringExtra("status"),
                photoUrl = intent.getStringExtra("photo")
            )

            setupViews()
            loadConversationMessages()
        }
        else
        {
            finish()
        }
    }

    fun setupViews()
    {
        // Get Views
        txtMessageBox = findViewById(R.id.txtMessageBox)
        btnSend = findViewById(R.id.imgSend)
        recyclerMessages = findViewById(R.id.recyclerMessages)
        progressLoading = findViewById(R.id.progressLoading)

        // Toolbar
        supportActionBar?.apply {
            title = user?.name
            subtitle = user?.status
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        setUserStatus(user?.status == "online")

        // Recycler View
        messagesAdapter = ChatMessagesAdapter()
        recyclerMessages.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerMessages.adapter = messagesAdapter

        // Message Box
        txtMessageBox.setOnEditorActionListener { v, actionId, event ->
            when(actionId)
            {
                EditorInfo.IME_ACTION_GO -> {
                    sendMessage(txtMessageBox.text.toString())
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }

        // Send Button
        btnSend.setOnClickListener {
            sendMessage(txtMessageBox.text.toString())
        }
    }

    private fun setUserStatus(isOnline: Boolean)
    {
        if (isOnline)
        {
            supportActionBar?.subtitle = Html.fromHtml("<font color='#149214'>online</font>")
        }
        else
        {
            supportActionBar?.subtitle = Html.fromHtml("<font color='#575757'>offline</font>")
        }
    }

    private fun sendMessage(message: String) {
        if (!message.isEmpty())
        {
            user?.let {
                val receiverID: String = it.uid
                val messageText = message

                var messageModel = MessageModel(message, true)
                messagesList.add(messageModel)
                messagesAdapter?.notifyItemInserted(messagesList.size-1)
                recyclerMessages.scrollToPosition(messagesList.size-1)

                // Clear the message box
                txtMessageBox.setText("")
            }
        }
    }

    fun loadConversationMessages()
    {
        user?.let {

            // Show Progress Bar
            progressLoading.visibility = View.VISIBLE
            recyclerMessages.visibility = View.GONE

            Handler().postDelayed(Runnable {
                loadDummyMessages()

                // Hide Progress bar
                progressLoading.visibility = View.GONE
                recyclerMessages.visibility = View.VISIBLE

            }, 1*1000)          // 1 seconds dummy delay
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId)
        {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadDummyMessages()
    {
        messagesList.add(MessageModel("Hi", true))
        messagesList.add(MessageModel("How are you?", true))
        messagesList.add(MessageModel("I'm fine", false))
        messagesList.add(MessageModel("How about ya?", false))
        messagesList.add(MessageModel("Fine", true))
        messagesList.add(MessageModel("What ya upto these days?", true))
        messagesList.add(MessageModel("Same ol' job dude!", false))
        messagesList.add(MessageModel("Same ol' job dude! asfa sfasf asfd asfd asf asdf asfd asf asfasf asf asf asf sf safasfdasdf asf asfd asf asdfasdf", false))
        messagesList.add(MessageModel("Same ol' job dude! asfa sfasf asfd asfd asf asdf asfd asf asfasf asf asf asf sf safasfdasdf asf asfd asf asdfasdf", true))
        messagesAdapter?.notifyDataSetChanged()
    }

    // Recycler Adapter
    inner class ChatMessagesAdapter : RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            var view = LayoutInflater.from(this@ChatActivity).inflate(R.layout.chat_message_item_layout, parent, false)
            return MessageViewHolder(view)
        }

        override fun getItemCount(): Int = messagesList.size

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            var messageModel = messagesList[position]
            holder.bindItem(messageModel)
        }

        // View Holder
        inner class MessageViewHolder : RecyclerView.ViewHolder
        {
            var txtMyMessage: AppCompatTextView
            var txtOtherMessage: AppCompatTextView
            var cardMyMessage: MaterialCardView
            var cardOtherMessage: MaterialCardView

            constructor(itemView: View) : super(itemView)
            {
                txtMyMessage = itemView.findViewById(R.id.txtMyMessage)
                txtOtherMessage = itemView.findViewById(R.id.txtOtherMessage)
                cardMyMessage = itemView.findViewById(R.id.cardChatMyMessage)
                cardOtherMessage = itemView.findViewById(R.id.cardChatOtherMessage)
            }


            fun bindItem(messageModel: MessageModel)
            {
                if (messageModel.isMine)
                {
                    cardMyMessage.visibility = View.VISIBLE
                    cardOtherMessage.visibility = View.GONE
                    txtMyMessage.text = messageModel.message
                }
                else
                {
                    cardMyMessage.visibility = View.GONE
                    cardOtherMessage.visibility = View.VISIBLE
                    txtOtherMessage.text = messageModel.message
                }
            }
        }
    }
}
