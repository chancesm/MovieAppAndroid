package com.example.chanc.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class movieActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent intent = getIntent();
        String Title = intent.getStringExtra(MainActivity.TITLE);
        String Plot = intent.getStringExtra(MainActivity.PLOT);
        String Rating = intent.getStringExtra(MainActivity.RATING);
        String Runtime = intent.getStringExtra(MainActivity.RUNTIME);
        String Path = intent.getStringExtra(MainActivity.PATH);
        Glide.with(this).load(Path).into((ImageView) findViewById(R.id.imageView));
        TextView title = findViewById(R.id.textView);
        TextView rating = findViewById(R.id.textView2);
        TextView plot = findViewById(R.id.textView3);
        TextView runtime = findViewById(R.id.textView4);
        plot.setText(Plot);
        rating.setText(Rating);
        title.setText(Title);
        runtime.setText(Runtime);
    }
}
