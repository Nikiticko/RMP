package com.example.rmp_coursach

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmp_coursach.adapter.CatAdapter
import com.example.rmp_coursach.databinding.ActivityMvvmBinding
import com.example.rmp_coursach.viewmodel.CatViewModel

class MainActivityMVVM : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmBinding
    private lateinit var catAdapter: CatAdapter

    private val viewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catAdapter = CatAdapter(emptyList())

        binding.catRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivityMVVM)
            adapter = catAdapter
        }

        viewModel.cats.observe(this) { list ->
            catAdapter.updateData(list)
        }


        // 🔙 Кнопка возврата
        binding.backToMainButton.setOnClickListener {
            finish()
        }
    }
}
