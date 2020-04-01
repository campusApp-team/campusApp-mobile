package com.example.campusapp.ui.main.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.campusapp.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.chat_tv_item.view.*
import kotlinx.android.synthetic.main.forum_detail.view.*

class MessageAdapter(private var mDoc:List<DocumentSnapshot>) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {
    val TAG = "MessageAdapter"

    class MyViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId: String = "null"
        val mText: TextView = mView.chat_tv
    }

    fun updateData(newData: List<DocumentSnapshot>){
        mDoc =  newData
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_tv_item, parent, false)
        return MyViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mDoc[position%mDoc.size]
        holder.mId = item.id
        holder.mText.text = item.getString("text")
        with(holder.mView) {
            tag = item.id
        }
    }
    // TODO15 remove duplication
    override fun getItemCount() = mDoc.size*20

}