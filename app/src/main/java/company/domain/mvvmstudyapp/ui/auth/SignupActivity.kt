package company.domain.mvvmstudyapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import company.domain.mvvmstudyapp.R
import company.domain.mvvmstudyapp.databinding.ActivitySignupBinding
import company.domain.mvvmstudyapp.ui.home.HomeActivity
import company.domain.mvvmstudyapp.util.*

import kotlinx.coroutines.launch

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<AuthViewModelFactory>()
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)

                }
            }
        })

        binding.buttonSignUp.setOnClickListener {
            userSignup()

        }

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }

    private fun userSignup() {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val passwordConfirm = binding.editTextPasswordConfirm.text.toString().trim()

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userSignup(name, email, password)

                if (authResponse.user != null)
                    viewModel.saveLoggedInUser(authResponse.user)
                else
                    binding.root.snackbar(authResponse.message!!)

            } catch (error: ApiException) {
                error.printStackTrace()

            } catch (error: NoInternetException) {
                error.printStackTrace()

            }
        }
    }
}
