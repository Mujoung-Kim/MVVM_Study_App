package company.domain.mvvmstudyapp.ui.auth

import androidx.lifecycle.LiveData
import company.domain.mvvmstudyapp.data.databases.entity.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)

}