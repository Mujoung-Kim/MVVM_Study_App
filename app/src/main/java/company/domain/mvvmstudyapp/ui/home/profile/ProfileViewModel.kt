package company.domain.mvvmstudyapp.ui.home.profile

import androidx.lifecycle.ViewModel
import company.domain.mvvmstudyapp.data.repositories.UserRepository

class ProfileViewModel(repository: UserRepository) : ViewModel() {

    val user = repository.getUser()

}
