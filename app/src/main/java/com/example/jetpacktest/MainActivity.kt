package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        sp = getPreferences(Context.MODE_PRIVATE)
        setContentView(binding.root)

        val countReserved = sp.getInt("count_reserved", 0)
        viewModel = ViewModelProvider(this, MainViewModelFactory(countReserved))[MainViewModel::class.java]
        viewModel.counter.observe(this) { count -> binding.infoText.text = count.toString() }
        binding.plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }
        binding.clearBtn.setOnClickListener {
            viewModel.clear()
        }
        lifecycle.addObserver(MyObserver(lifecycle))
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter.value ?: 0)
        }
    }

}