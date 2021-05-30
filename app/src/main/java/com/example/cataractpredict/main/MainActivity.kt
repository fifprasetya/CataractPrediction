package com.example.cataractpredict.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cataractpredict.R
import com.example.cataractpredict.data.ArticleAdapter
import com.example.cataractpredict.databinding.ActivityMainBinding
import com.example.cataractpredict.prediction.PredictionActivity
import com.example.cataractpredict.utils.EventDummy

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val article = EventDummy.generateDummyEvent()
        val articleAdapter = ArticleAdapter()
        articleAdapter.notifyDataSetChanged()
        articleAdapter.setArticle(article)

        with(binding.rvArticle){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articleAdapter
        }

        binding.btnPredict.setOnClickListener {
            val intent = Intent(this, PredictionActivity::class.java)
            startActivity(intent)
        }


    }
}