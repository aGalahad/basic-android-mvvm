package appsynth.galahad.mvvm.view.chatroom.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.model.Message
import kotlinx.android.synthetic.main.item_left_chat_room.view.*
import kotlinx.android.synthetic.main.item_right_chat_room.view.*

/**
 * Created by armtimus on 10/21/2017 AD.
 */
class ChatRoomMessageAdapter :
        RecyclerView.Adapter<ChatRoomMessageAdapter.MessageViewHolder>() {

    companion object {
        private const val LEFT = 0
        private const val RIGHT = 1
    }

    private val messageList = mutableListOf<Message>()

    open class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bindView(context: Context, message: Message?) {}
    }

    class LeftViewHolder(itemView: View) : MessageViewHolder(itemView) {
        val nameTextView = itemView.leftNameTextView!!
        val messageTextView = itemView.leftMessageTextView!!

        override fun bindView(context: Context, message: Message?) {
            super.bindView(context, message)

            nameTextView.text = message?.name ?: ""
            messageTextView.text = message?.message ?: ""
        }
    }

    class RightViewHolder(itemView: View) : MessageViewHolder(itemView) {
        val nameTextView = itemView.rightNameTextView!!
        val messageTextView = itemView.rightMessageTextView!!

        override fun bindView(context: Context, message: Message?) {
            super.bindView(context, message)

            nameTextView.text = message?.name ?: ""
            messageTextView.text = message?.message ?: ""
        }
    }

    fun setListData(list: List<Message>) {
        messageList.clear()
        messageList.addAll(list)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (holder is LeftViewHolder) {
            holder.bindView(holder.itemView.context, messageList[position])
        } else {
            holder.bindView(holder.itemView.context, messageList[position])
        }
    }

    override fun getItemCount() = messageList.size

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].isMine) {
            RIGHT
        } else {
            LEFT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == LEFT) {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_left_chat_room,
                parent,
                false)
            LeftViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_right_chat_room,
                parent,
                false)
            RightViewHolder(itemView)
        }
    }
}