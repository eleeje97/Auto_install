package com.example.auto_install;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button contents_btn;
    private TextView text;
    private String log = ""; // 로그를 찍을 내용
    private String apk_file_name =  "";
    /**
     * 송현주 USB 경로 : /storage/5029-C52A
     * */

    // usb > apkfiles 디렉토리의 경로 지정
    public String usb_path = "/storage/5029-C52A";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        contents_btn = findViewById(R.id.contents_btn);


        contents_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ContentsCopyActivity.class);

                // usb의 경로 저장 및 usb에 담긴 리스트를 저장할 파일 생성
                File usb = new File(usb_path);
                File[] usbFiles = usb.listFiles();

                if(usbFiles == null){
                    text.setText("NULL!!!!");
                }

                else {
                    // usbFiles 안에 리스트를 출력
                    // apks라는 이름을 가진 폴더를 찾아 저장
                    for (File curFile : usbFiles) {

                        if (curFile.getName().equals("apks")) {
                            apk_file_name += curFile.getName();
                        } else {
                            System.out.println("Not Found 'apks'");
                        }

                    }

                    // apks 파일 안에 apk 파일 path 저장
                    File apk_dir = new File(usb_path + apk_file_name);
                    //File[] apkFiles = apk_dir.listFiles();

                    //for(File apkFile : apkFiles){
                    //    log += apkFile;
                    //}

                    text.setText(">> " + apk_file_name
                            + "\n apk_dir path :  >>" + apk_dir.getPath()+"\n");
                }
                startActivity(intent);



            }



        });







        // auto_install 앱이 설치 되었는지를 확인
        // packageManager를 이용하면 확인 가능



        // auto_install 앱이 설치 되었다면 apkFiles를 순차적으로 설치 작업 진행
        /** 해당 작업을 진행할 때 사용자가 해야할 일
         *  환경설정(일반) > 보안 > 출처를 알 수 없는 앱 > 설치할 때마다 묻기 또는 허용으로 체크
         *  이유) 보안상의 이유로 apk 파일로 설치하는 것이 허용되어 있지 않음으로 사용자가 직접 체크 해주어야 함 **/

        // 3가지 apk 파일들의 설치 여부 확인 후, 콘텐츠 다운로드 버튼 활성화
    }
}

