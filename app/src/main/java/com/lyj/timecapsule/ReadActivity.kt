package com.lyj.timecapsule

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class ReadActivity : AppCompatActivity() {

    private lateinit var topCapsule: View
    private lateinit var bottomCapsule: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        topCapsule = findViewById(R.id.top)
        bottomCapsule = findViewById(R.id.bottom)
        val countdownText = findViewById<TextView>(R.id.textView2)
        val content = findViewById<TextView>(R.id.textView3)
        val targetDate = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
            .parse("2024.11.28 15:53")

        content.alpha = 0f
        targetDate?.let {
            val currentTime = System.currentTimeMillis()
            val targetTime = it.time
            val timeDifference = targetTime - currentTime

            if (timeDifference > 0) {
                // 타이머 시작
                startCountdown(it, countdownText, content)
            } else {
                // 이미 시간이 지난 경우
                countdownText.text = "타임캡슐 오픈!"
                startAnimation(content) // 애니메이션 바로 실행
            }
        }


    }

    fun startAnimation(content: TextView) {
        val topAnimator = ObjectAnimator.ofFloat(topCapsule, "translationY", -220f)
        val bottomAnimator = ObjectAnimator.ofFloat(bottomCapsule, "translationY", 250f)

        topAnimator.duration = 1000 // 1초
        bottomAnimator.duration = 1000 // 1초

        topAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                // 캡슐에 fade out 효과 적용 후 내용 표시
                fadeOutView(topCapsule, 0.5f)
                fadeOutView(bottomCapsule, 0.5f)
                fadeInView(content)
            }
        })

        topAnimator.start()
        bottomAnimator.start()
    }

    fun fadeOutView(view: View, finalAlpha: Float) {
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, finalAlpha)

        fadeOut.duration = 1000
        fadeOut.start()
    }

    fun fadeInView(view: View) {
        val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f) // Alpha를 0에서 1로 변경
        fadeIn.duration = 1000 // 1초 동안 애니메이션
        fadeIn.start()
    }

    fun startCountdown(targetDate: Date, countdownText:TextView, content: TextView){
        val currentTime = System.currentTimeMillis()
        val targetTime = targetDate.time
        val timeDifference = targetTime - currentTime

        if (timeDifference > 0) {
            object : CountDownTimer(timeDifference, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                    val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                    val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                    // 남은 시간 업데이트
                    countdownText.text = "D-$days ${hours}h ${minutes}m ${seconds}s"
                }

                override fun onFinish() {
                    countdownText.text = "타임캡슐 오픈!"
                    fadeInView(content)

                }
            }.start()
        }


    }





}