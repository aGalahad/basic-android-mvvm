package appsynth.galahad.mvvm.view.profile

import android.arch.lifecycle.ViewModel
import appsynth.galahad.mvvm.api.local.UserDataManager

class ProfileViewModel(private val userDataManager: UserDataManager): ViewModel() {
    fun setUserId(id: String) {
        userDataManager.setUserId(id = id)
    }

    fun setUserName(name: String) {
        userDataManager.setUserName(name = name)
    }
}