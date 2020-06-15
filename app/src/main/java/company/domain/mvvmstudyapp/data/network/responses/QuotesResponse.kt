package company.domain.mvvmstudyapp.data.network.responses

import company.domain.mvvmstudyapp.data.databases.entity.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>

)