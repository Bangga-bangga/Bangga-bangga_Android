package com.example.bangga_bangga

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.api.PreviewAdultPostApi
import com.example.bangga_bangga.model.PreviewModel
import com.example.bangga_bangga.model.PreviewPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOldTab : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PreviewAdapter
    private var posts: MutableList<PreviewModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_old, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_old)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PreviewAdapter(posts)
        recyclerView.adapter = adapter
        fetchDataFromServer() // 서버에서 데이터를 가져옴
        return view
    }
    private fun fetchDataFromServer() {
//        val previewPostsService = PreviewAdultsPostsService.createAdultPost(requireContext())
        val previewPostsApi = PreviewAdultPostApi.createAdultPost(requireContext())
        val call = previewPostsApi.getAdultPosts(100, 0)
        call.enqueue(object : Callback<PreviewPostResponse> {
            override fun onResponse(call: Call<PreviewPostResponse>, response: Response<PreviewPostResponse>) {
                if (response.isSuccessful) {
                    val postsResponse = response.body()
                    Log.d("게시판 미리보기", response.body().toString())

                    postsResponse?.posts?.let { posts ->
                        this@FragmentOldTab.posts = posts.toMutableList()
                        adapter.setPosts(posts)
                        adapter.notifyDataSetChanged()

                    }

                } else {
                    Log.d("에러 코드", response.code().toString())
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<PreviewPostResponse>, t: Throwable) {
                // Handle failure
                Log.e("요청 실패 에러 메시지",t.message.toString())
            }
        })
    }

}