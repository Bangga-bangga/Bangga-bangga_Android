package com.example.bangga_bangga

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.MyPostModel
import com.example.bangga_bangga.model.PreviewModel
import com.example.bangga_bangga.model.UserInfoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMyPageTab : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyPostAdapter
    private var posts: MutableList<PreviewModel> = mutableListOf()
    private var user = UserInfoModel(
        email = "",
        category = "",
        nickname = "",
        age = 0,
        posts = mutableListOf()
    )

    private lateinit var myNicknameTextView: TextView
    private lateinit var myEmailTextView: TextView
    private lateinit var myAgeTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_mypage, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_myPage)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MyPostAdapter(posts)
        recyclerView.adapter = adapter

        // TextView들을 초기화
        myNicknameTextView = view.findViewById(R.id.myNicknameText)
        myEmailTextView = view.findViewById(R.id.myEmailText)
        myAgeTextView = view.findViewById(R.id.myAgeText)

        fetchDataFromServer() // 서버에서 데이터를 가져옴
        return view
    }
    private fun fetchDataFromServer() {
        val userInfoService = UserInfoService.createUserInfo(requireContext())
        val call = userInfoService.getUserInfo(100,0)
        call.enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                if (response.isSuccessful) {
                    val userInfoResponse = response.body()
                    Log.d("응답", userInfoResponse.toString())
                    userInfoResponse?.let{ res ->
                        myNicknameTextView.text = res.nickname
                        myEmailTextView.text = res.email
                        myAgeTextView.text = res.age.toString()
                    }

                    userInfoResponse?.myPost?.let { myPosts ->
                        this@FragmentMyPageTab.posts = myPosts.posts.toMutableList()
                        adapter.setUserInfo(posts)
                        adapter.notifyDataSetChanged()
                    }

                } else {
                    Log.d("에러 코드", response.code().toString())
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                // Handle failure
                Log.e("요청 실패 에러 메시지",t.message.toString())
            }
        })

        adapter.setUserInfo(posts)
    }
}