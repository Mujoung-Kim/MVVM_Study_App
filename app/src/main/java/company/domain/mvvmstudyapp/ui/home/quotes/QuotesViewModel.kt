package company.domain.mvvmstudyapp.ui.home.quotes

import androidx.lifecycle.ViewModel

import company.domain.mvvmstudyapp.data.repositories.QuotesRepository
import company.domain.mvvmstudyapp.util.lazyDeferred

class QuotesViewModel(repository: QuotesRepository) : ViewModel() {
    val quotes by lazyDeferred {
        repository.getQuotes()

    }
}
