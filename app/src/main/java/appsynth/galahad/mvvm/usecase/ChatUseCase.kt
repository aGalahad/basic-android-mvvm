package appsynth.galahad.mvvm.usecase

import appsynth.galahad.mvvm.api.repo.ChatRepo
import appsynth.galahad.mvvm.model.Message
import io.reactivex.Observable
import io.reactivex.Single

interface ChatUseCase {
    fun getMessages(userId: String): Single<List<Message>>
    fun subscriptMessage(userId: String): Observable<Message>
    fun sendMessage(senderId: String, name: String, message: String): Single<Boolean>
}

class ChatUseCaseImpl(val chatRepo: ChatRepo) :
    ChatUseCase {
    override fun subscriptMessage(userId: String): Observable<Message> =
        chatRepo.subscriptMessage(userId = userId)

    override fun getMessages(userId: String): Single<List<Message>> =
        chatRepo.getMessages(userId = userId)

    override fun sendMessage(senderId: String, name: String, message: String): Single<Boolean> =
        chatRepo.sendMessage(senderId = senderId, name = name, message = message)
}