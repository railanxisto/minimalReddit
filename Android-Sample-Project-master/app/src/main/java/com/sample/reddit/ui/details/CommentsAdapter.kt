package com.sample.reddit.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.reddit.R
import com.sample.reddit.model.Comment
import com.sample.reddit.model.DataChildrenComments
import kotlinx.android.synthetic.main.item_comment_recyclerview.view.*

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private val comments = mutableListOf<DataChildrenComments>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_recyclerview, parent, false)
        return CommentsViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        comments[position].run {
            holder.setComment(this.comment)
        }
    }

    fun setComments(commentsList: List<DataChildrenComments>) {
        comments.clear()
        comments.addAll(commentsList)
        notifyDataSetChanged()
    }

    inner class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setComment(comment: Comment?) {
            // TODO: Use resources.string
            itemView.authorTextView.text = "Replied by ${comment?.author}"
            itemView.scoreTextView.text = comment?.score.toString()
            itemView.bodyTextView.text = comment?.body
        }
    }
}