package appsynth.galahad.mvvm.view.profile

import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.api.local.UserDataManagerImpl
import appsynth.galahad.mvvm.extenstions.getViewModel
import appsynth.galahad.mvvm.view.chatroom.ChatRoomFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    private val viewModel: ProfileViewModel by lazy {
        getViewModel {
            ProfileViewModel(
                userDataManager = UserDataManagerImpl.instance
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener {
            val deviceId = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
            viewModel.setUserId(id = deviceId)
            viewModel.setUserName(name = nameEditText.text.toString())

            val chatRoomFragment = ChatRoomFragment()
            fragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.fragment_container, chatRoomFragment, ChatRoomFragment.TAG)
                ?.commit()
        }
    }
}