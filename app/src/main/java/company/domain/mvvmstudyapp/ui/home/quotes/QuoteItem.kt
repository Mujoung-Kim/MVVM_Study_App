package company.domain.mvvmstudyapp.ui.home.quotes

import com.xwray.groupie.databinding.BindableItem

import company.domain.mvvmstudyapp.R
import company.domain.mvvmstudyapp.data.databases.entity.Quote
import company.domain.mvvmstudyapp.databinding.ItemQuoteBinding

class QuoteItem(private val quote: Quote) : BindableItem<ItemQuoteBinding>() {
    override fun getLayout(): Int = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)

    }
}