package com.example.shezh_project.view_models

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shezh_project.models.SearchResult
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class UserSearchViewModel : ViewModel(), Observable {
    @Bindable
    val username = MutableLiveData<String>()
    var repoList = MutableLiveData<ArrayList<SearchResult>>()

    private val client = OkHttpClient()

    fun getData(){
        repoList.value?.clear()
        var newList = arrayListOf<SearchResult>()
        val request = Request.Builder()
            .url("https://api.github.com/users/${username.value}/repos")
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val json = JSONArray(response.body()?.string())

                    for (i in 0 until json.length()){
                        val sr = SearchResult(
                            json.getJSONObject(i)["name"].toString(),
                            json.getJSONObject(i)["description"].toString()
                        )
                        newList.add(sr)
                    }

                    repoList.postValue(newList)
                }
            }
        })
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}
