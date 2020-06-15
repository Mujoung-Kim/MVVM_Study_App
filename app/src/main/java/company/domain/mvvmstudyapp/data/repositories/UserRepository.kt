package company.domain.mvvmstudyapp.data.repositories

import company.domain.mvvmstudyapp.data.databases.AppDatabases
import company.domain.mvvmstudyapp.data.databases.entity.User
import company.domain.mvvmstudyapp.data.network.MyApi
import company.domain.mvvmstudyapp.data.network.SafeApiRequest
import company.domain.mvvmstudyapp.data.network.responses.AuthResponse

import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val database: AppDatabases
    ) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse =
        apiRequest { api.userLogin(email, password) }

    suspend fun userSingup(name: String, email: String, password: String): AuthResponse =
        apiRequest { api.userSignup(name, email, password) }

    suspend fun saveUser(user: User) = database.getUserDao().upsert(user)

    fun getUser() = database.getUserDao().getUser()

}