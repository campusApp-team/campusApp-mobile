package com.example.campusapp.ui.main.forum

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.campusapp.R
import com.example.campusapp.backend.DataRef.MESSAGES_TEXT
import com.example.campusapp.backend.DataRef.MESSAGES_TIMESTAMP
import com.example.campusapp.backend.DataRef.storage
import com.example.campusapp.tool.GlideApp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.chat_tv_item.view.*
import kotlinx.android.synthetic.main.forum_detail_header.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val HEADER_VIEW = 0
const val MESSAGE_VIEW = 1

class MessageAdapter(
    private var mDoc:List<DocumentSnapshot>,
    private var fDoc:List<DocumentSnapshot>,
    private val reference:String,
    private var titlePath:String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mId: String = "null"
        val mText: TextView = mView.chat_tv
        val mDate: TextView = mView.date_tv_chat
    }

    class HeaderViewHolder(val mView: View) : RecyclerView.ViewHolder(mView){
        val mForumTitle: TextView = mView.forum_title
        val mForumId: TextView = mView.forum_id_tv
        val mImageView: AppCompatImageView = mView.forum_header_image
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.v(this.javaClass.simpleName,"onBind $position")
        if (holder is HeaderViewHolder) {
            holder.mView.tag = "header"
        } else if (holder is MyViewHolder) {
            val item = mDoc[position-1]
            holder.mId = item.id
            holder.mText.text = item.getString(MESSAGES_TEXT)

            var displayTime = ""
            val time = item.getTimestamp(MESSAGES_TIMESTAMP)

            if(time != null){
                val cal = Calendar.getInstance()
                cal.timeInMillis = Timestamp.now().seconds*1000
                val thisYear = cal.get(Calendar.YEAR)
                cal.timeInMillis = time.seconds*1000

                displayTime = when {
                    cal.get(Calendar.YEAR) != thisYear ->
                        "${cal.get(Calendar.DAY_OF_MONTH)} ${cal.get(Calendar.MONTH)+1} ${cal.get(Calendar.YEAR)}"
                    Timestamp.now().seconds - time.seconds < 24*60*60 -> { // today's post
                        val date = Date((time.seconds%(24*60*60))*1000)
                        SimpleDateFormat("HH:mm").format(date)
                    }else -> {// within current year
                        val date = Date(time.seconds*1000)
                        SimpleDateFormat("dd MMM").format(date)
                    }
                }
            }

            holder.mDate.text = displayTime
            with(holder.mView) {
                tag = item.id
            }
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== HEADER_VIEW){
            val headerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.forum_detail_header, parent, false)

            val i = (Math.random() * 8).roundToInt()
            val imageRef = storage.getReference(when(i){
                3    -> "forum/pic3.png"
                else -> "forum/pic$i.jpg"
            })

            val circularProgressDrawable = CircularProgressDrawable(parent.context)
            circularProgressDrawable.strokeWidth= 5f
            circularProgressDrawable.centerRadius = 23f
            circularProgressDrawable.start()

//            val url = "https://avatars2.githubusercontent.com/u/25717858"
            GlideApp.with(parent.context)
                .load(imageRef)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.placeholder)
                .into(HeaderViewHolder(headerView).mImageView)

            HeaderViewHolder(headerView).mForumTitle.text = titlePath
            HeaderViewHolder(headerView).mForumId.text = reference
            HeaderViewHolder(headerView)
        }else{
            val mView = LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_tv_item, parent, false)
            MyViewHolder(mView)
        }
    }

    fun updateMessagesData(newMessages: List<DocumentSnapshot>){
        mDoc = newMessages
//        notifyDataSetChanged()
    }

    fun updateSubForumsData(newSubForums: List<DocumentSnapshot>){
        fDoc = newSubForums
        if (fDoc.isNotEmpty()){
            Log.v(this.javaClass.simpleName, " mashal  ${fDoc[0].get("title")}")
        }
//        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER_VIEW
            else -> MESSAGE_VIEW
        }
    }

    override fun getItemCount() = mDoc.size+1

}