package company.domain.mvvmstudyapp.data.network.responses

import company.domain.mvvmstudyapp.data.databases.entity.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)