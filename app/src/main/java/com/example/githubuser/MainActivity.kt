package com.example.githubuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.remote.ApiClient
import com.example.githubuser.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {

                flow {
                    val response = ApiClient
                        .githubService
                        .getUserGithub()

                    emit(response)
                }.onStart {//dijalankan saat mulai
                    binding.progressBar.isVisible = true
                }.onCompletion {// dijalankan saat selesai
                    binding.progressBar.isVisible = false
                }.catch {// dijalankan saat error
                    Toast.makeText(this@MainActivity, it.message.toString(), Toast.LENGTH_SHORT)
                }.collect {
                    //method untuk mendapatkan response
                    adapter.setData(it)
                }
            }
        }
    }
}