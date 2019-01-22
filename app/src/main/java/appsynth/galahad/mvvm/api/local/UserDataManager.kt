package appsynth.galahad.mvvm.api.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

interface UserDataManager {
    fun getUserId(): String
    fun getUserName(): String
    fun setUserId(id: String)
    fun setUserName(name: String)
}

class UserDataManagerImpl private constructor(): UserDataManager {

    companion object {
        @JvmStatic
        val instance by lazy {
            UserDataManagerImpl()
        }

        private const val USER_ID = "USER_ID"
        private const val USER_NAME = "USER_NAME"
    }

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyPref", MODE_PRIVATE)
    }

    override fun getUserId(): String = sharedPreferences?.getString(USER_ID, "") ?: ""

    override fun getUserName(): String = sharedPreferences?.getString(USER_NAME, "") ?: ""

    override fun setUserId(id: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(USER_ID, id)
        editor?.commit()
    }

    override fun setUserName(name: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(USER_NAME, name)
        editor?.commit()
    }
}