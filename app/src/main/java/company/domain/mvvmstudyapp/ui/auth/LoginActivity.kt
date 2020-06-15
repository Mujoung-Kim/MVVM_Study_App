package company.domain.mvvmstudyapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import company.domain.mvvmstudyapp.R
import company.domain.mvvmstudyapp.data.databases.entity.User
import company.domain.mvvmstudyapp.databinding.ActivityLoginBinding
import company.domain.mvvmstudyapp.ui.home.HomeActivity
import company.domain.mvvmstudyapp.util.hide
import company.domain.mvvmstudyapp.util.show
import company.domain.mvvmstudyapp.util.snackbar

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    //  change to this -> private val factory: AuthViewModelFactory by instance()
    private val factory by instance<AuthViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
//        val api = MyApi(networkConnectionInterceptor)
//        val databases = AppDatabases(this)
//        val repository = UserRepository(api, databases)
//        val factory = AuthViewModelFactory(repository)

        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
//        val viewModel: AuthViewModel by viewModels()
        val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.authListener = this
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)

                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()

    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
//        root_layout.snackbar("${user.name} is Logged In")
        /*loginResponse.observe(this, Observer {
            progress_bar.hide()
            toast(it)

        })*/
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)

    }
}
