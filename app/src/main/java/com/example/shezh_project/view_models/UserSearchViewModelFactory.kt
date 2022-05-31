package com.example.shezh_project.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class UserSearchViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserSearchViewModel::class.java)){
            return UserSearchViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }
}