package com.sample.reddit.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.sample.reddit.R
import com.sample.reddit.model.DataChildren
import com.sample.reddit.model.Topic
import kotlinx.android.synthetic.main.item_topic_recyclerview.view.*

class ListAdapter(
    private val listener: TopicClickListener
) : RecyclerView.Adapter<ListAdapter.TopicsViewHolder>() {

    private val topics = mutableListOf<DataChildren>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topic_recyclerview, parent, false)
        return TopicsViewHolder(view)
    }

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        topics[position].run {
            holder.setTopic(this.topic!!)
            holder.setTopiButtonClickListener {
                listener.onTopicClick(this.topic)
            }
        }
    }

    fun setTopics(topicsList: List<DataChildren>) {
        topics.clear()
        topics.addAll(topicsList)
        notifyDataSetChanged()
    }

    fun updateTopics(topicsList: List<DataChildren>) {
        topics.addAll(topicsList)
        notifyDataSetChanged()
    }

    inner class TopicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setTopic(topic: Topic) {
            if (topic.thumbnail == EMPTY_THUMBNAIL) {
                itemView.topicImageView.isVisible = false
            } else {
                itemView.topicImageView.isVisible = true
                itemView.topicImageView.load(topic.thumbnail)
            }
            itemView.titleTextView.text = topic.title
            // TODO: Use resources.string
            itemView.commentsTextView.text = "${topic.comments} comments"
        }

        fun setTopiButtonClickListener(clickListener: () -> Unit) {
            itemView.setOnClickListener {
                clickListener()
            }
        }
    }

    interface TopicClickListener {
        fun onTopicClick(topic: Topic)
    }

    companion object {
        private const val EMPTY_THUMBNAIL = "self"
    }
}