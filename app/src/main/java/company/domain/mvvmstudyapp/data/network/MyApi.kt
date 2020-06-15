package company.domain.mvvmstudyapp.data.network

import company.domain.mvvmstudyapp.data.network.responses.AuthResponse
import okhttp3.OkHttpClient

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("userlogin")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("createuser")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {
            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor).build()

            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl("http://192.168.10.85:8080/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(MyApi::class.java)

        }
//        https://api.simplifiedcoding.in/course-apis/mvvm/
    }
}
