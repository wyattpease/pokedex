package com.example.shezh_project

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.shezh_project.databinding.MainActivityBinding
import com.example.shezh_project.view_models.UserSearchViewModel
import com.example.shezh_project.view_models.UserSearchViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shezh_project.adapters.RepoRecyclerAdapter


class MainActivity : AppCompatActivity() {

    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: UserSearchViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var mainrecycler: RecyclerView
    lateinit var usernameAlert: Toast
    lateinit var repoAlert: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.main_activity)
        mainrecycler = findViewById(R.id.recycler)
        getSupportActionBar()?.setIcon(R.drawable.layer_logo)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        val factory = UserSearchViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(UserSearchViewModel::class.java)
        binding.userSearchViewModel = viewModel
        binding.lifecycleOwner = this

        findViewById<Button>(R.id.get_repo_btn).setOnClickListener {
            getRepos()
        }

        usernameAlert = Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT)
        repoAlert = Toast.makeText(this,"User does not exist or has no repositories", Toast.LENGTH_SHORT)
        initialiseAdapter()
    }
    private fun getRepos(){
        val user = findViewById<EditText>(R.id.username)
        if (user.text.toString().isBlank()){
            usernameAlert.show()
        }
        else{
            usernameAlert.cancel()
            repoAlert.cancel()
            viewModel.getData()
            mainrecycler.adapter?.notifyDataSetChanged()
            viewModel.repoList.observe(this){
                if (viewModel.repoList.value?.isEmpty()==true) {repoAlert.show()}
            }
        }
    }
    private fun initialiseAdapter(){
        mainrecycler.layoutManager = viewManager
        observeData()
    }

    private fun observeData(){
        viewModel.repoList.observe(this) {
            Log.i("data", it.toString())
            mainrecycler.adapter = RepoRecyclerAdapter(viewModel, it, this)
        }
    }
}