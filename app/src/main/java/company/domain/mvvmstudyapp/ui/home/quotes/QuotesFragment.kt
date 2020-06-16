package company.domain.mvvmstudyapp.ui.home.quotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

import company.domain.mvvmstudyapp.R
import company.domain.mvvmstudyapp.data.databases.entity.Quote
import company.domain.mvvmstudyapp.util.Coroutines
import company.domain.mvvmstudyapp.util.hide
import company.domain.mvvmstudyapp.util.show

import kotlinx.android.synthetic.main.quotes_fragment.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<QuotesViewModelFactory>()
    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.quotes_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[QuotesViewModel::class.java]
        bindUI()
        /*Coroutines.main {
            val quotes = viewModel.quotes.await()

            quotes.observe(this, Observer {
                context?.toast(it.size.toString())

            })
        }*/
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()

        //  change to this -> viewModel.quotes.await().observe(this, Observer { ... })
        viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            initRecycleView(it.toQuoteItem())

        })
    }

    private fun List<Quote>.toQuoteItem(): List<QuoteItem> = this.map { QuoteItem(it) }

    private fun initRecycleView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)

        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter

        }
    }
}
