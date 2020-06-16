package company.domain.mvvmstudyapp.ui.auth

import androidx.lifecycle.ViewModel

import company.domain.mvvmstudyapp.data.databases.entity.User
import company.domain.mvvmstudyapp.data.repositories.UserRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(email: String, password: String) =
        withContext(Dispatchers.IO) { repository.userLogin(email, password) }

    suspend fun userSignup(name: String, email: String, password: String) =
        withContext(Dispatchers.IO) { repository.userSingup(name, email, password) }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

}