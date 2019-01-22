package appsynth.galahad.mvvm.api.repo

import android.util.Log
import appsynth.galahad.mvvm.model.Message
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single

interface ChatRepo {
    fun getMessages(userId: String): Single<List<Message>>
    fun subscriptMessage(userId: String): Observable<Message>
    fun sendMessage(senderId: String, name: String, message: String): Single<Boolean>
}

class ChatRepoImpl(val firebaseDatabase: FirebaseDatabase): ChatRepo {

    companion object {
        private const val NODE_MESSAGE = "message"
        private const val ERROR_SEND_MESSAGE = "Cannot sent message."
    }

    override fun getMessages(userId: String): Single<List<Message>> {
        return Single.create<List<Message>> { emitter ->
            firebaseDatabase.getReference(NODE_MESSAGE)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {}

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val messageList = mutableListOf<Message>()
                            dataSnapshot.children.forEach { item ->
                                val message = item.getValue(Message::class.java)
                                message?.let { message ->
                                    message.isMine = userId == message.senderId
                                    messageList.add(message)
                                }
                            }
                            emitter.onSuccess(messageList)
                        }
                    })
        }
    }

    override fun subscriptMessage(userId: String): Observable<Message> {
        return Observable.create<Message> { emitter ->
            firebaseDatabase.getReference(NODE_MESSAGE)
                .addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {}

                    override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {}

                    override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {}

                    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                        val newMessage = dataSnapshot.getValue(Message::class.java)
                        newMessage?.let { message ->
                            message.isMine = userId == message.senderId
                            emitter.onNext(message)
                        }
                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                })
        }
    }

    override fun sendMessage(senderId: String, name: String, message: String): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            firebaseDatabase.getReference(NODE_MESSAGE).push()
                .setValue(Message().apply {
                    this.senderId = senderId
                    this.name = name
                    this.message = message
                })
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener {
                    emitter.onError(Throwable(ERROR_SEND_MESSAGE))
                }
        }
    }
}