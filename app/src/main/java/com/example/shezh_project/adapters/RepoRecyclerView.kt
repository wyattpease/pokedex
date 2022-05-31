package com.example.shezh_project.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.shezh_project.R
import com.example.shezh_project.models.SearchResult
import com.example.shezh_project.view_models.UserSearchViewModel
import kotlinx.android.synthetic.main.repo_view.view.*

class RepoRecyclerAdapter(val viewModel: UserSearchViewModel, private val arrayList: ArrayList<SearchResult>, private val context: Context): RecyclerView.Adapter<RepoRecyclerAdapter.RepoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RepoRecyclerAdapter.RepoViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.repo_view,parent,false)
        return RepoViewHolder(root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RepoRecyclerAdapter.RepoViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class RepoViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(repo: SearchResult){
            binding.repo_name.text = repo.name
        }

    }

}