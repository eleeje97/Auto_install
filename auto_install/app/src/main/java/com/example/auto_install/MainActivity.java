package com.example.auto_install;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView apk1,apk2,apk3; // 3가지 apk 파일의 설치 여부를 출력
    private Button contents_btn; // 콘텐츠 다운로드 버튼
    private boolean all_apks_install = false; // 3개의 apk 파일이 설치 되었는지 여부

    private String apk_file_name =  "";

    private boolean check = false; // 해당 앱이 설치되어 있는지 확인
    /**
     * 송현주 USB 경로 : /storage/5029-C52A
     * */

    // usb > apkfiles 디렉토리의 경로 지정
    public String usb_path = "/storage/5029-C52A";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // apk 파일 접근해 설치를 할 수 있도록
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // usb 정보가져오기
       // UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);


        // xml 파일 연결
        apk1 = findViewById(R.id.apkinfo1);
        apk2 = findViewById(R.id.apkinfo2);
        apk3 = findViewById(R.id.apkinfo3);



        contents_btn = findViewById(R.id.contents_btn);
        contents_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) { // 텍스트 다운로드 버튼을 클릭했다면


                Intent intent = new Intent(getApplicationContext(),ContentsCopyActivity.class);

                // usb의 경로 저장 및 usb에 담긴 리스트를 저장할 파일 생성
                File usb = new File(usb_path);
                File[] usbFiles = usb.listFiles();

                // usb 파일 없다면 null 있다면 하위 폴더 정보 저장
                if(usbFiles == null){
                    Toast.makeText(getApplication(), "null", Toast.LENGTH_SHORT).show();
                }

                else {
                    // usbFiles 안에 리스트를 출력
                    // apks라는 이름을 가진 폴더를 찾아 저장

                    // usb 하위 폴더 중 apk 파일이 담겨 있는 폴더를 찾음
                    for (File curFile : usbFiles) {

                        // 현재 폴더의 이름이 apks 라면
                        if (curFile.getName().equals("apks")) {
                            apk_file_name = curFile.getName();
                        }
                        // 일치하는 폴더가 없다면
                        else {
                            System.out.println("Not Found 'apks'");
                        }
                    }

                    // 위 과정을 통해 일치하는 폴더를 찾았다면 apks 파일 안에 각각의 apk파일을 가져오기 위해 다시 파일 생성
                    // apk_file_name이 apks임, usb안에 apks 파일을 apk_dir로 표현한 것임
                    File apk_dir = new File(usb_path + "/" +apk_file_name);

                    // apks 파일 안에 apk 파일 path 저장
                    File[] apkFiles = apk_dir.listFiles();

                    /** 기기에 설치된 앱의 packageInfo 확인하기 **/
                    // 나중에 example 회사명이나 다른 걸로 바꾸기
                    String auto_install_pk = "com.example.auto_install";
                    if(isChecked(auto_install_pk) == true){

                        // akps 파일에 3개의 apk 파일을 설치
                        for(File apkFile : apkFiles){
                            apkInstall(apkFile);
                        }


                        //  3개의 apk 파일이 설치되어 패키지가 존재하는지 확인

                    }
                    else{
                        Toast.makeText(getApplication(), auto_install_pk + "앱이 설치되어 있지 않습니다."
                                , Toast.LENGTH_SHORT).show();
                    }






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


    // apkFile 자동 설치를 위한 메소드
    public void apkInstall(File apkFile){

        // 설치할 apk 파일의 위치를 저장
        Uri apkUri = Uri.fromFile(apkFile);

        try{
            Intent apk_intent = new Intent(Intent.ACTION_VIEW);
            apk_intent.setDataAndType(apkUri, "application/vnd.android.package-archive");


            startActivity(apk_intent);
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    // 앱의 설치여부 확인을 위한 메소드
    public boolean isChecked(String packagename){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packagename);

        // 해당 패키지 존재 여부에 따라 동작하도록
        // 해당 앱이 설치되어 있지 않다면
        if(launchIntent == null){
            return false;
        }

        else { // 앱이 설치되어 있다면 Toast 메세지 출력
            Toast.makeText(getApplication(), packagename + "앱이 설치되어 있습니다."
                    , Toast.LENGTH_SHORT).show();

            return true;
        }
    }
}

