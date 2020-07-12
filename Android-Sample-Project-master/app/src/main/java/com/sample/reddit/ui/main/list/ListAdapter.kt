package com.sample.reddit.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.sample.reddit.R
import com.sample.reddit.model.DataChildren
import com.sample.reddit.model.Topic
import kotlinx.android.synthetic.main.item_topic_recyclerview.view.*

class ListAdapter(
    val listener: TopicClickListener
) : RecyclerView.Adapter<ListAdapter.FactsViewHolder>() {

    private val topics = mutableListOf<DataChildren>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic_recyclerview, parent, false)
        return FactsViewHolder(view)
    }

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        topics[position].run {
            holder.setTopic(this.topic!!)
            holder.setShareButtonClickListener {
                listener.onTopicClick()
            }
        }
    }

    fun updateTopics(factsList: List<DataChildren>) {
        topics.clear()
        topics.addAll(factsList)
        notifyDataSetChanged()
    }

    inner class FactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setTopic(topic: Topic) {
            itemView.topicImageView.load(topic.thumbnail)
            itemView.titleTextView.text = topic.title
            itemView.commentsTextView.text = "${topic.comments.toString()} comments"
        }

        fun setShareButtonClickListener(clickListener: () -> Unit) {
            itemView.setOnClickListener {
                clickListener()
            }
        }
    }

    interface TopicClickListener {
        fun onTopicClick()
    }
}