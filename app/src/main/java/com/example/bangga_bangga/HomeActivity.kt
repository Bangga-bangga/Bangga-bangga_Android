package com.example.bangga_bangga

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bangga_bangga.databinding.ActivityHomeBinding
//import java.util.logging.Handler
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout

interface OnBannerClickListener {
    fun onBannerClick(position: Int)
}

class HomeActivity : AppCompatActivity(), OnBannerClickListener {
    private var currentPosition = Int.MAX_VALUE / 2

    private var myHandler = MyHandler()
    private val intervalTime = 3000.toLong()  // 몇 초 간격으로 페이지를 넘길건지
    private lateinit var homeBinding: ActivityHomeBinding // 바인딩 객체 선언


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        val tabLayout = homeBinding.tabLayout
        // 유저 카테 고리 가져오기
        val prefCategory = getSharedPreferences("userCategory", Context.MODE_PRIVATE)
        val userType = prefCategory.getString("category", null)
        // 초기 선택된 탭
        var selectedTab = 0

        // 탭2,3번 이미지 변경하기
//        val tabSelectors = intArrayOf(R.drawable.selector1, R.drawable.selector2, R.drawable.selector3)
        val tabSelectors = intArrayOf(R.drawable.young_tab_unselected, R.drawable.old_tab_unselected, R.drawable.my_page_tab_unselected)

        if(userType =="adult"){
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, FragmentOldTab()).commit();

        } else if(userType == "mz"){
            selectedTab = 1
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, FragmentYoungTab()).commit();
        }

        /** 툴바 생성 코드**/
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""



        // 게시글 미리보기 탭
        for(i in tabSelectors.indices) {
            val tab = tabLayout.newTab()
            when {
                userType == "adult" && i == 0 -> tabLayout.addTab(tab, true)
                userType == "mz" && i == 1 -> tabLayout.addTab(tab, true)
                else -> tabLayout.addTab(tab, false)
            }
            tab.setCustomView(R.layout.tab_layout)

            val tabView = tab.customView
            val tabImageView = tabView?.findViewById<ImageView>(R.id.tabImage)
            when (i) {
                selectedTab -> {
                    when (i) {
                        0 -> tabImageView?.setImageResource(R.drawable.young_tab_selected)
                        1 -> tabImageView?.setImageResource(R.drawable.old_tab_selected)
                        2 -> tabImageView?.setImageResource(R.drawable.my_page_tab_selected)
                    }
                }
                else -> {
                    when (i) {
                        0 -> tabImageView?.setImageResource(R.drawable.young_tab_unselected)
                        1 -> tabImageView?.setImageResource(R.drawable.old_tab_unselected)
                        2 -> tabImageView?.setImageResource(R.drawable.my_page_tab_unselected)
                    }
                }
            }

            tabLayout.getTabAt(i)?.let { tab ->
                tab.view?.setOnClickListener{
                    val prevSelectedTab = selectedTab
                    selectedTab = i
                    val transaction = supportFragmentManager.beginTransaction()
                    when(i){
                        0 -> {
                            transaction.replace(R.id.frameLayout, FragmentOldTab())
                            updateTabImages(prevSelectedTab)
                            tabImageView?.setImageResource(R.drawable.young_tab_selected)
                        }
                        1 -> {
                            transaction.replace(R.id.frameLayout, FragmentYoungTab())
                            updateTabImages(prevSelectedTab)
                            tabImageView?.setImageResource(R.drawable.old_tab_selected)
                        }
                        2 -> {
                            transaction.replace(R.id.frameLayout, FragmentMyPageTab())
                            updateTabImages(prevSelectedTab)
                            tabImageView?.setImageResource(R.drawable.my_page_tab_selected)
                        }
                    }
                    transaction.commit()
                }
            }
        }

        // 배너
        val bannerAdapter = BannerAdapter(getBannerList(), this)
        bannerAdapter.setOnBannerClickListener(this)
        homeBinding.viewPagerAdvice.adapter = bannerAdapter

        homeBinding.viewPagerAdvice.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        homeBinding.viewPagerAdvice.setCurrentItem(currentPosition, false)

        homeBinding.viewPagerAdvice.apply{
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }

        // 로고 클릭 이벤트
        homeBinding.imageView.setOnClickListener {
            // 페이지를 새로고침하는 코드
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish() // 현재 액티비티를 종료하여 새로운 액티비티를 열 때 새로고침 효과
        }

        // 사용자 유형에 따라 게시글 작성 버튼 보이기 유무 변경
        val writePostBtn = homeBinding.writePostBtn

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 선택된 탭의 위치를 가져옴
                val selectedTabPosition = tab?.position ?: 0
                if(selectedTabPosition == 0 && userType == "adult"){
                    writePostBtn.visibility = View.VISIBLE
                } else if(selectedTabPosition == 1 && userType == "adult"){
                    writePostBtn.visibility = View.GONE
                } else if(selectedTabPosition == 0 && userType == "mz"){
                    writePostBtn.visibility = View.GONE
                } else if(selectedTabPosition == 1 && userType == "mz"){
                    writePostBtn.visibility = View.VISIBLE
                } else{
                    writePostBtn.visibility = View.GONE
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택 해제되었을 때 필요한 동작을 수행할 수 있음
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택되었을 때 필요한 동작을 수행할 수 있음
            }
        })
        // 글 작성 버튼 클릭 이벤트
        writePostBtn.setOnClickListener{
            val intent = Intent(this, NewPostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateTabImages(prevSelectedTab: Int) {
        val tabLayout = homeBinding.tabLayout
        val prevTabView = tabLayout.getTabAt(prevSelectedTab)?.customView
        val prevTabImageView = prevTabView?.findViewById<ImageView>(R.id.tabImage)
        when (prevSelectedTab) {
            0 -> prevTabImageView?.setImageResource(R.drawable.young_tab_unselected)
            1 -> prevTabImageView?.setImageResource(R.drawable.old_tab_unselected)
            2 -> prevTabImageView?.setImageResource(R.drawable.my_page_tab_unselected)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //toolbar의 back키 눌렀을 때 동작
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun autoScrollStart(intervalTime: Long){
        myHandler.removeMessages(0)
        myHandler.sendEmptyMessageDelayed(0, intervalTime)
    }
    private fun autoScrollStop(){
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }
    override fun onBannerClick(position: Int){
        val intent = if (position % 2 == 0){
            Intent(this, ICanActivity::class.java) // 할 수 있다 페이지로 변경
        } else {
            Intent(this, TrashKeywordActivity::class.java) // 감정 쓰레기통 페이지로 변경
        }
        startActivity(intent)
    }
    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what == 0) {
                homeBinding.viewPagerAdvice.setCurrentItem(++currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime) // 스크롤을 계속 이어서 한다.
            }
        }
    }

    // 다른 페이지 갔다가 돌아오면 다시 스크롤 시작
    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }
    // 다른 페이지로 떠나있는 동안 스크롤이 동작할 필요는 없음. 정지
    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }
    private fun getBannerList(): Array<Array<String>>{
        return arrayOf(
            arrayOf("행동의 가치는 그 행동을 끝까지 이루는 데 있다.", "#fff2ef"),
            arrayOf("바람에 흔들리는 꽃의 향기가 더 멀리 간다.", "#fff7d8"),
            arrayOf("게으르지 않음은 영원한 삶의 집이요,\n게으름은 죽음의 집이다.", "#fff2ef"),
            arrayOf("천천히 가도 괜찮아, 삶은 속도가 아니라 방향이니까.", "#fff7d8"),
            arrayOf("실패는 잊어라.\n하지만 그것이 준 교훈은 절대 잊으면 안된다.", "#fff2ef"),
            arrayOf("잘났든 못났든 이 세상 가장 아름다운 것은\n당신이 살아낸 그 시간들, 결국은 당신의 삶", "#fff7d8"),
            arrayOf("인생에 뜻을 세우는데 적당한 때는 없다.", "#fff2ef"),
            arrayOf("길을 잃는다는 것은 곧, 길을 알게 되는 것이다.", "#fff7d8"),
            arrayOf("꿈을 꾸기에 인생은 빛난다.", "#fff2ef"),
            arrayOf("진정한 기쁨은 어두컴컴한 밤을 지나 아침에 온다.", "#fff7d8")
        )
    }



}