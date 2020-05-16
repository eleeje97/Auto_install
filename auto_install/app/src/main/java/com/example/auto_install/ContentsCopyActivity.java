package com.example.auto_install;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ContentsCopyActivity extends AppCompatActivity {

    Button button; // 컨텐츠 복사 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_copy);

        button = findViewById(R.id.button);
    }
}
