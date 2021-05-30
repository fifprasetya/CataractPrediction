package com.example.cataractpredict.detailarticle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cataractpredict.R
import com.example.cataractpredict.data.ArticleEntity
import com.example.cataractpredict.databinding.ActivityDetailArticleBinding
import com.example.cataractpredict.utils.EventDummy

class DetailArticleActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ARTICLE = "extra_article"
    }

    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if(extras != null){
            val articleId = extras.getString(EXTRA_ARTICLE)
            if(articleId != null){
                for(article in EventDummy.generateDummyEvent()){
                    if(article.id_event == articleId){
                        populateArticle(article)
                    }
                }
            }
        }
    }

    private fun populateArticle(articleEntity: ArticleEntity){
        binding.textHeadline.text = articleEntity.headline_detail_events
        binding.textAuthor.text = articleEntity.author_detail_events
        binding.textDate.text = articleEntity.date_detail_event
        binding.textDescription.text = articleEntity.content_detail_event

        Glide.with(this)
            .load(articleEntity.photo_events)
            .apply(RequestOptions().override(360,240))
            .into(binding.imgPoster)
    }
}