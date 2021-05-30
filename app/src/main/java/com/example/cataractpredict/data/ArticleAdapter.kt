package com.example.cataractpredict.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cataractpredict.databinding.ItemRowArticleBinding
import com.example.cataractpredict.detailarticle.DetailArticleActivity

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var listArticle = ArrayList<ArticleEntity>()

    fun setArticle(article: List<ArticleEntity>?){
        if(article == null) return
        this.listArticle.clear()
        this.listArticle.addAll(article)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.ArticleViewHolder {
        val itemsArticleBinding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(itemsArticleBinding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        val article = listArticle[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = listArticle.size

    class ArticleViewHolder(private val binding: ItemRowArticleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article: ArticleEntity){
            with(binding){
                tvItemHeadline.text = article.headline_detail_events
                tvItemDate.text = article.date_detail_event
                tvItemAuthor.text = article.author_detail_events
                tvItemCategory.text = article.name_events
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                    intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, article.id_event)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(article.photo_events)
                    .apply(RequestOptions().override(1080,340))
                    .into(imgEvent)
            }
        }

    }
}