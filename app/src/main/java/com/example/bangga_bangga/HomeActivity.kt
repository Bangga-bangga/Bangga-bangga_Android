package com.example.bangga_bangga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bangga_bangga.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
//    private var numBanner = 10
    private var currentPosition = Int.MAX_VALUE / 2
//    private var myHandler = MyHandler()
//    private val intervalTime = 1500.toLong()  // 몇 초 간격으로 페이지를 넘길건지
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.viewPagerAdvice.adapter = BannerAdapter(getBannerList())
        homeBinding.viewPagerAdvice.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        homeBinding.viewPagerAdvice.setCurrentItem(currentPosition, false)


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