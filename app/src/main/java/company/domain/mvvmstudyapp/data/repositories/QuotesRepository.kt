package company.domain.mvvmstudyapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import company.domain.mvvmstudyapp.data.databases.AppDatabases
import company.domain.mvvmstudyapp.data.databases.entity.Quote
import company.domain.mvvmstudyapp.data.network.MyApi
import company.domain.mvvmstudyapp.data.network.SafeApiRequest
import company.domain.mvvmstudyapp.data.preferences.PreferenceProvider
import company.domain.mvvmstudyapp.util.Coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val MINIMUM_INTERVAL = 9

class QuotesRepository(
    private val api: MyApi,
    private val database: AppDatabases,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)

        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> = withContext(Dispatchers.IO) {
        fetchQuotes()
        database.getQuoteDao().getQuotes()

    }

    private suspend fun fetchQuotes() {
        val lastSaveAt = prefs.getLastSavedAt()

        if (lastSaveAt == null || isFetchNeeded(LocalDateTime.parse(lastSaveAt))) {
            val response = apiRequest { api.getQuotes() }

            quotes.postValue(response.quotes)

        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean =
        ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSaveAt(LocalDateTime.now().toString())
            database.getQuoteDao().saveAllQuotes(quotes)

        }
    }
}