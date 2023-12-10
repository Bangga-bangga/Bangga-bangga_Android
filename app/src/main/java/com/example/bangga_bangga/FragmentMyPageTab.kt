package com.example.bangga_bangga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class FragmentMyPageTab : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PreviewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        // 예시 데이터 생성
        val posts = mutableListOf(
            PreviewModel("제목1", "첫 번째 글", "닉네임1", 0,0),
            PreviewModel("제목2", "두 번째 글", "닉네임2",0,0),
            PreviewModel("제목3", "세 번째 글", "닉네임3",0,0),
            PreviewModel("제목3", "세 번째 글", "닉네임3",0,0),
            PreviewModel("제목3", "세 번째 글", "닉네임3",0,0),
            PreviewModel("제목3", "세 번째 글", "닉네임3",0,0)
        )

//        recyclerView = view.findViewById(R.id.recyclerView_old)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        adapter = PreviewAdapter(posts)
//        recyclerView.adapter = adapter

//        fetchDataFromServer() // 서버에서 데이터를 가져옴
        return view
    }
    private fun fetchDataFromServer() {
        // 여기서 서버에서 데이터를 가져오는 코드를 구현하고,
        // 가져온 데이터를 Post 객체 리스트로 만듭니다.

        // 예시 데이터 생성
        val posts = listOf(
            PreviewModel("제목1", "첫 번째 글", "닉네임1", 0,0),
            PreviewModel("제목2", "두 번째 글", "닉네임2",0,0),
            PreviewModel("제목3", "세 번째 글", "닉네임3",0,0)
        )

        adapter.setPosts(posts)
    }
}