package com.example.githubuser

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter()
    }

  private val viewModel by viewModels<MainViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        viewModel.resultSuccess.observe(this){
            adapter.setData(it)
        }

        viewModel.resultError.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.resultLoading.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.getUser()

    }
}