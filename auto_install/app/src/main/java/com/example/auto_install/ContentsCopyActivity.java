package com.example.auto_install;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class ContentsCopyActivity extends AppCompatActivity {

    /** 로그를 위한 변수들 **/
    private static final String TAG = "TEST"; // TAG for Logcat
    private TextView logView; // 로그를 보여주기 위한 텍스트뷰
    private String log = ""; // 로그를 찍을 내용


    private Button button; // 컨텐츠 복사 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_copy);

        button = findViewById(R.id.button);
        logView = findViewById(R.id.logView);

        button.setOnClickListener(onButtonClickListener);
    }

    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            log ="";

            /**
             * USB에 컨텐츠 파일이 있는지 확인
             **/

            // USB경로 확인하고 파일 목록 console에 출력
            File usbDir = new File ("/storage/64CA-79C3"); // 근화학교 USB 경로 --> 나중에 경로 일반화해야 함!
            String[] usbDirFiles = usbDir.list(); // USB 안에 있는 파일들

            Log.e(TAG, "USB 경로: " + usbDir.getPath()); // USB 경로
            log += "USB 경로: " + usbDir.getPath() + "\n";

            if(usbDirFiles == null) {
                Log.e(TAG, "null");
                log += "null";
            } else {
                Log.e(TAG, "파일개수: " + usbDirFiles.length); // 파일 개수
                log += "파일개수: " + usbDirFiles.length + "\n";

                for(String filename : usbDirFiles) {
                    Log.e(TAG, filename); // 파일명
                    log += filename + "\n";
                }

            }



            logView.setText(log);

        }
    };


}
