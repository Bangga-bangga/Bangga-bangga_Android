package com.example.bangga_bangga

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel
import com.example.bangga_bangga.model.UserInfoModel
class MyPostHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nicknameTextView: TextView = itemView.findViewById(R.id.myNicknameText)
    private val emailTextView: TextView = itemView.findViewById(R.id.myEmailText)
    private val ageTextView: TextView = itemView.findViewById(R.id.myAgeText)


    fun bind(user: UserInfoModel){
        nicknameTextView.text = user.nickname
        emailTextView.text = user.email
        ageTextView.text = user.age.toString()
    }
}