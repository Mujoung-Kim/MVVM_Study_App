package company.domain.mvvmstudyapp.data.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import company.domain.mvvmstudyapp.data.databases.entity.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllQuotes(quotes: List<Quote>)

    @Query("select * from quote")
    fun getQuotes(): LiveData<List<Quote>>

}