package appsynth.galahad.mvvm.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import appsynth.galahad.mvvm.R
import appsynth.galahad.mvvm.api.local.UserDataManagerImpl
import appsynth.galahad.mvvm.view.chatroom.ChatRoomFragment
import appsynth.galahad.mvvm.view.movie.MovieFragment
import appsynth.galahad.mvvm.view.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserDataManagerImpl.instance.initialize(context = this)

        if (savedInstanceState == null) {
//            if (UserDataManagerImpl.instance.getUserId().isEmpty()) {
//                val profileFragment = ProfileFragment()
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.fragment_container, profileFragment, ChatRoomFragment.TAG)
//                    .addToBackStack("home").commit()
//            } else {
//                val chatRoomFragment = ChatRoomFragment()
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.fragment_container, chatRoomFragment, ChatRoomFragment.TAG).commit()
//            }
        }

        val movieFragment = MovieFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, movieFragment, MovieFragment.TAG).commit()
    }
}
