package com.example.android.addfragmentdynamicexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView mTextViewArticle;
    private TextView mTextViewTitle;

    private Button mPrevButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewTitle = findViewById(R.id.title);
        mTextViewArticle = findViewById(R.id.article);
        mTextViewArticle.setText(R.string.article2);
        mTextViewTitle.setText(R.string.title2);

        mPrevButton = findViewById(R.id.nav_button);
        mPrevButton.setText(R.string.prev);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });

    }
}