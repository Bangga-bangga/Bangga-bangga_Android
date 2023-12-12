package com.example.bangga_bangga

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentYoungTab: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PreviewAdapter
    private var posts: MutableList<PreviewModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_young, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_young)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PreviewAdapter(posts)
        recyclerView.adapter = adapter

        fetchDataFromServer() // 서버에서 데이터를 가져옴

        return view
    }
    private fun fetchDataFromServer() {
        val previewPostsService = PreviewMzsPostsService.createMzPost(requireContext())
        val call = previewPostsService.getMzPosts(100, 0)
        call.enqueue(object : Callback<PreviewPostsResponse> {
            override fun onResponse(call: Call<PreviewPostsResponse>, response: Response<PreviewPostsResponse>) {
                if (response.isSuccessful) {
                    val postsResponse = response.body()
                    Log.d("게시판 미리보기", response.body().toString())
                    postsResponse?.posts?.let { posts ->
                        this@FragmentYoungTab.posts = posts.toMutableList()
                        adapter.setPosts(posts)
                        adapter.notifyDataSetChanged()
                    }
//                    recyclerView = view!!.findViewById(R.id.recyclerView_young) ?: return
//                    recyclerView.layoutManager = LinearLayoutManager(context)
//                    recyclerView.adapter = adapter
                } else {
                    Log.d("tag","서버 응답 없음")
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<PreviewPostsResponse>, t: Throwable) {
                // Handle failure
                Log.e("에러",t.message.toString())
            }
        })
    }
}