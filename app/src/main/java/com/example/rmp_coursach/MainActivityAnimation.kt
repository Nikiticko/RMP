package com.example.rmp_coursach

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivityAnimation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val image = findViewById<ImageView>(R.id.animatedImage)
        val lottie = findViewById<LottieAnimationView>(R.id.lottieView)
        val startButton = findViewById<Button>(R.id.startButton)
        val backButton = findViewById<Button>(R.id.back_to_main_button)

        // Кнопка "Назад"
        backButton.setOnClickListener {
            finish()
        }

        // Кнопка "Запустить анимацию"
        startButton.setOnClickListener {
            // Загружаем кота с TheCatAPI
            val request = JsonArrayRequest(
                Request.Method.GET,
                "https://api.thecatapi.com/v1/images/search",
                null,
                { response ->
                    try {
                        val url = response.getJSONObject(0).getString("url")
                        Picasso.get().load(url).into(image)

                        // Анимация
                        image.visibility = ImageView.VISIBLE
                        val combo = AnimationUtils.loadAnimation(this, R.anim.combo)
                        image.startAnimation(combo)

                        // Lottie
                        lottie.playAnimation()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "Ошибка при разборе данных", Toast.LENGTH_SHORT).show()
                    }
                },
                {
                    it.printStackTrace()
                    Toast.makeText(this, "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            )

            Volley.newRequestQueue(this).add(request)
        }
    }
}
