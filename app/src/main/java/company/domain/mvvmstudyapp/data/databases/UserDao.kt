package company.domain.mvvmstudyapp.data.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import company.domain.mvvmstudyapp.data.databases.entity.CURRENT_USER_ID
import company.domain.mvvmstudyapp.data.databases.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User) : Long

    @Query("select * from User where uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>

}