package appsynth.galahad.mvvm.view.chatroom

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.api.local.UserDataManagerImpl
import appsynth.galahad.mvvm.api.repo.ChatRepoImpl
import appsynth.galahad.mvvm.usecase.ChatUseCaseImpl
import appsynth.galahad.mvvm.extenstions.getViewModel
import appsynth.galahad.mvvm.view.chatroom.adapter.ChatRoomMessageAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_chat_room.*

class ChatRoomFragment: Fragment() {

    companion object {
        const val TAG = "ChatRoomFragment"
    }

    private val viewModel: ChatRoomViewModel by lazy {
        getViewModel {
            ChatRoomViewModel(
                chatUseCase = ChatUseCaseImpl(
                    chatRepo = ChatRepoImpl(firebaseDatabase = FirebaseDatabase.getInstance())
                ),
                userDataManager = UserDataManagerImpl.instance
            )
        }
    }

    private val chatMessageAdapter: ChatRoomMessageAdapter by lazy {
        ChatRoomMessageAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingViewModel()
        initView()

        viewModel.getMessages()
        viewModel.subscriptMessageNode()
    }

    private fun initView() {
        messageRecycleView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = chatMessageAdapter
        }

        sendButton.setOnClickListener {
            if (messageEditText.text.isNotEmpty()) {
                viewModel.sendMessage(message = messageEditText.text.toString())
                messageEditText.setText("")
            }
        }

        messageEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                messageRecycleView.scrollToPosition(chatMessageAdapter.itemCount - 1)
            }
        }

        messageEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.trypingMessage(count = count)
            }
        })
    }

    private fun bindingViewModel() {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                showLoadingView()
            } else {
                hideLoadingView()
            }
        })

        viewModel.isContentReady.observe(this, Observer { isContentReady ->
            if (isContentReady == true) {
                showContentView()
            } else {
                hideContentView()
            }
        })

        viewModel.messageList.observe(this, Observer { messageList ->
            messageList?.let {
                chatMessageAdapter.setListData(list = it)
                chatMessageAdapter.notifyDataSetChanged()
                messageRecycleView.scrollToPosition(chatMessageAdapter.itemCount - 1)
            }
        })

        viewModel.toastMessage.observe(this, Observer { toastMessage ->
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.isCanSendMessage.observe(this, Observer { isCanSendMessage ->
            if (isCanSendMessage == true) {
                sendButton.isEnabled = true
                sendButton.background = ContextCompat.getDrawable(context!!, R.drawable.send_button_background)
            } else {
                sendButton.isEnabled = false
                sendButton.background = ContextCompat.getDrawable(context!!, R.drawable.send_button_disable_background)
            }
        })
    }

    private fun showLoadingView() {
        progressView.visibility = View.VISIBLE
    }

    private fun hideLoadingView() {
        progressView.visibility = View.GONE
    }

    private fun showContentView() {
        contentView.visibility = View.VISIBLE
    }

    private fun hideContentView() {
        contentView.visibility = View.GONE
    }
}