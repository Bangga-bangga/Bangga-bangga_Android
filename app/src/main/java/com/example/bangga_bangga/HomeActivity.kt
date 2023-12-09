package com.example.bangga_bangga


import android.os.Bundle

import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bangga_bangga.databinding.ActivityHomeBinding
//import java.util.logging.Handler
import android.os.Handler

class HomeActivity : AppCompatActivity() {
    private var currentPosition = Int.MAX_VALUE / 2

    private var myHandler = MyHandler()
    private val intervalTime = 3500.toLong()  // 몇 초 간격으로 페이지를 넘길건지
    private lateinit var homeBinding: ActivityHomeBinding // 바인딩 객체 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.viewPagerAdvice.adapter = BannerAdapter(getBannerList())
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
    }
    private fun autoScrollStart(intervalTime: Long){
        myHandler.removeMessages(0)
        myHandler.sendEmptyMessageDelayed(0, intervalTime)
    }
    private fun autoScrollStop(){
        myHandler.removeMessages(0) // 핸들러를 중지시킴
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
    private fun incrementCurrentPosition() {
        currentPosition++
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
            arrayOf("행동의 가치는 그 행동을 끝까지 이루는 데 있다.", "#d5e4b5"),
            arrayOf("바람에 흔들리는 꽃의 향기가 더 멀리 간다.", "#fff7d8"),
            arrayOf("게으르지 않음은 영원한 삶의 집이요,\n게으름은 죽음의 집이다.", "#d5e4b5"),
            arrayOf("천천히 가도 괜찮아, 삶은 속도가 아니라 방향이니까.", "#fff7d8"),
            arrayOf("실패는 잊어라.\n하지만 그것이 준 교훈은 절대 잊으면 안된다.", "#d5e4b5"),
            arrayOf("잘났든 못났든 이 세상 가장 아름다운 것은\n당신이 살아낸 그 시간들, 결국은 당신의 삶", "#fff7d8"),
            arrayOf("인생에 뜻을 세우는데 적당한 때는 없다.", "#d5e4b5"),
            arrayOf("길을 잃는다는 것은 곧, 길을 알게 되는 것이다.", "#fff7d8"),
            arrayOf("꿈을 꾸기에 인생은 빛난다.", "#d5e4b5"),
            arrayOf("진정한 기쁨은 어두컴컴한 밤을 지나 아침에 온다.", "#fff7d8")
        )
    }

}