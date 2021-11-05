package com.softocorp.priscriptosender_v2.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.softocorp.priscriptosender_v2.R
import com.softocorp.priscriptosender_v2.dashboard.DashboardActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.from_message_recycler_view.view.*
import kotlinx.android.synthetic.main.from_message_recycler_view.view.txtFromChat
import kotlinx.android.synthetic.main.to_message_recycler_view.view.*

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.chatRecyclerView)

        val chatAdapter = GroupAdapter<ViewHolder>()
        chatAdapter.add(FromChat("Hey I want to place an order for Paracetamol.", "8:59 AM"))
        chatAdapter.add(ToChat("Sure you can place it, may I know how much you wanted?", "9:00 AM"))
        chatAdapter.add(FromChat("Oh sorry I forgot to mention it, I need 10 tablets", "9:01 AM"))
        chatAdapter.add(ToChat("Sure sir, total amount will be 100 rs. including delivery charges", "9:01 AM"))
        chatAdapter.add(FromChat("Oh, okay send it to my location, I'll receive it and pay in cash", "9:02 AM"))
        chatAdapter.add(ToChat("Sound Great!, I'm sending my delivery personnel there", "9:03 AM"))
        chatAdapter.add(FromChat("Hey, received the order and paid the cash to your delivery guy.", "9:20 AM"))
        chatAdapter.add(ToChat("Thanks for placing your order with us....", "9:40 AM"))
        recyclerView.adapter = chatAdapter

        setUpToolbar()
        toolbar.title = "Rahul Jha"

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ChatActivity, DashboardActivity::class.java))
        finish()
    }

}

class FromChat(private val message: String, private val time: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.from_message_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtFromChat.text = message
        viewHolder.itemView.txtFromTime.text = time
    }

}

class ToChat(private val message: String, private val time: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.to_message_recycler_view
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtToChat.text = message
        viewHolder.itemView.txtToTime.text = time
    }

}
