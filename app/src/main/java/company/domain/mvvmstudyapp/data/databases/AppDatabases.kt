package company.domain.mvvmstudyapp.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import company.domain.mvvmstudyapp.data.databases.entity.Quote

import company.domain.mvvmstudyapp.data.databases.entity.User

@Database(entities = [User::class, Quote::class], version = 1)
abstract class AppDatabases : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getQuoteDao(): QuoteDao

    companion object {

        @Volatile
        private var instance: AppDatabases? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it

            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabases::class.java,
            "MyDatabase.db"
        ).build()

    }
}