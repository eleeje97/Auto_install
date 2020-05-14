package com.example.auto_install;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // usb > apkfiles 디렉토리의 경로 지정
    public String dir_path = Environment.getExternalStorageDirectory().getAbsolutePath()
                             + "디렉토리 이름 지정`";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 지정된 경로의 파일 생성
        //File dir_file = new File(dir_path);


        // 생성된 dir_file의 하위 파일들(확장자: .apk) 경로를 apkFiles에 저장

        //File[] apkFiles = dir_file.listFiles();

        /** 정보를 잘 가져 왔는지 확인용
         *  테스트 해보고 아래 for문은 지울 것 **/

        /*
        for(File curFile: apkFiles){
            System.out.println(curFile.getPath());
        }
        */

        // auto_install 앱이 설치 되었는지를 확인
        // packageManager를 이용하면 확인 가능



        // auto_install 앱이 설치 되었다면 apkFiles를 순차적으로 설치 작업 진행
        /** 해당 작업을 진행할 때 사용자가 해야할 일
         *  환경설정(일반) > 보안 > 출처를 알 수 없는 앱 > 설치할 때마다 묻기 또는 허용으로 체크
         *  이유) 보안상의 이유로 apk 파일로 설치하는 것이 허용되어 있지 않음으로 사용자가 직접 체크 해주어야 함 **/

        // 3가지 apk 파일들의 설치 여부 확인 후, 콘텐츠 다운로드 버튼 활성화
    }
}

