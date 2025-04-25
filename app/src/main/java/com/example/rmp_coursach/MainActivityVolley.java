package com.example.rmp_coursach;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.rmp_coursach.data.CatDao;
import com.example.rmp_coursach.data.CatDatabase;
import com.example.rmp_coursach.model.Cat;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityVolley extends AppCompatActivity {

    private ImageView catImageView;
    private Button prevButton, nextButton;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private final List<String> imageUrls = new ArrayList<>();
    private int currentIndex = -1;

    private CatDao catDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        catImageView = findViewById(R.id.catImageView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        Button backButton = findViewById(R.id.back_to_main_button);

        backButton.setOnClickListener(v -> finish());

        requestQueue = Volley.newRequestQueue(this);

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        // DAO для записи в БД
        catDao = CatDatabase.getDatabase(this).catDao();

        nextButton.setOnClickListener(v -> loadNextImage());
        prevButton.setOnClickListener(v -> showPreviousImage());
    }

    private void loadNextImage() {
        String url = "https://api.thecatapi.com/v1/images/search";

        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONObject catObject = response.getJSONObject(0);
                        String imageUrl = catObject.getString("url");

                        imageUrls.add(imageUrl);
                        currentIndex++;

                        imageLoader.get(imageUrl, ImageLoader.getImageListener(
                                catImageView,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_foreground
                        ));

                        // Сохраняем URL в Room
                        Cat cat = new Cat(0, imageUrl); // передаём id=0, Room сгенерирует сам
                        new Thread(() -> {
                            try {
                                catDao.insert(cat);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );

        requestQueue.add(jsonRequest);
    }

    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            String imageUrl = imageUrls.get(currentIndex);

            imageLoader.get(imageUrl, ImageLoader.getImageListener(
                    catImageView,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_foreground
            ));
        }
    }
}
