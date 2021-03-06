package com.example.auto_install;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ContentsCopyActivity extends AppCompatActivity {

    /** 로그를 위한 변수들 **/
    private static final String TAG = "TEST"; // TAG for Logcat
    private TextView logView; // 로그를 보여주기 위한 텍스트뷰
    private String log; // 로그를 찍을 내용


    private Button button; // 컨텐츠 복사 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_copy);

        button = findViewById(R.id.button);
        logView = findViewById(R.id.logView);
        logView.setMovementMethod(new ScrollingMovementMethod());

        button.setOnClickListener(onButtonClickListener);
    }

    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            log =""; // 로그 초기화


            /**
             * USB 경로 확인 : USB마다 경로명이 다르므로 일반화
             **/
            /* USB 경로 확인 후 파일목록 출력 */
            log += "[USB 경로확인]";
            String usbDir = "/storage/64CA-79C3"; // 근화학교 USB 경로 --> 나중에 경로 일반화해야 함!
            getChildFileList(usbDir);


            /* USB경로/contents에 파일이 있는지 확인 */
            File usbContents = new File(usbDir, "/contents");
            if(usbContents.exists()) {
                getChildFileList(usbContents.getPath()); // 파일목록 출력
            } else {
                // contents 파일이 없다고 경고
                Log.e(TAG, usbContents.getPath() + " 파일이 존재하지 않습니다.");
                log += usbContents.getPath() + " 파일이 존재하지 않습니다." + "\n";
            }


            /**
             * 기기에 Android/data/kr.or.keris.dt2018/files/DTBook/DTBook/DtextBook 경로가 존재하는지 확인
             * (디지털 교과서 앱이 설치되어 있는지 확인)
             **/
            File DtextBook = new File(Environment.getExternalStorageDirectory() + "/Android/data/kr.or.keris.dt2018/files/DTBook/DTBook/DtextBook");
            if(DtextBook.exists()) {
                getChildFileList(DtextBook.getPath());
            } else {
                // DtextBook 폴더가 없다고 경고 (디지털 교과서 앱이 설치되지 않음)
                Log.e(TAG, DtextBook.getPath() + " 파일이 존재하지 않습니다.");
                log += DtextBook.getPath() + " 파일이 존재하지 않습니다." + "\n";
            }


            /**
             * 컨텐츠 파일 복사
             **/
            log += "[컨텐츠 파일 복사]\n";


            /** text 파일 복사 test **/
            /*
            String copyfile = "copythis.txt"; // 복사할 파일명
            String fromDir = Environment.getExternalStorageDirectory() + "/Download" + File.separator; // 복사 전 폴더
            String toDir = Environment.getExternalStorageDirectory() + "/Download/copyhere" + File.separator; // 복사 후 폴더
             */

            /** zip 파일 복사 test **/
            /*
            String copyfile = "ziptest.zip"; // 복사할 파일명
            String fromDir = Environment.getExternalStorageDirectory() + "/Download" + File.separator; // 복사 전 폴더
            String toDir = Environment.getExternalStorageDirectory() + "/Download/copyhere" + File.separator; // 복사 후 폴더
            */

            /** USB에서 DTextBook으로 zip 파일 복사 test **/
            String copyfile = "ziptest.zip"; // 복사할 파일명
            String fromDir = usbContents + File.separator; // 복사 전 폴더
            String toDir = DtextBook + File.separator; // 복사 후 폴더

            String from = fromDir + copyfile;
            String to = toDir + copyfile;

            File fCopyfile = new File(from);
            File fToDir = new File(toDir);




            if (!fCopyfile.exists()) {
                Log.e(TAG, fCopyfile + " 복사할 파일이 없어요!");
                log += fCopyfile + " 복사할 파일이 없어요!\n";
            } else if (!fToDir.exists()) {
                Log.e(TAG, fToDir + " 복사할 폴더가 없어요!!!");
                log += fToDir + " 복사할 폴더가 없어요!!!\n";
            } else {
                getChildFileList(fromDir);
                getChildFileList(toDir);

                try {
                    copyFile(from, to);
                    Log.e(TAG, "Copy Success!");
                    log += "Copy Success!\n";
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Copy Failed...");
                    log += "Copy Failed...\n";
                }

                getChildFileList(fromDir);
                getChildFileList(toDir);

            }


            /**
             * zip 파일 압축 해제
             **/
            String zipFile = "ziptest"; // zip 파일명 (확장자 제외)
            String unzipLocation = DtextBook.getPath() + File.separator;  // unzip 할 위치

            Decompress d = new Decompress(zipFile, unzipLocation);
            //d.unzip();




            logView.setText(log);

        }
    };



    /**
     * 지정한 경로의 파일목록 출력
     * @param parentUri : 파일목록을 출력할 상위폴더의 경로 (String)
     **/
    private void getChildFileList(String parentUri) {
        log += "\n";

        File dir = new File(parentUri);
        File[] files = dir.listFiles(); // parent 폴더 안에 있는 파일들

        Log.e(TAG, "폴더 경로 >> " + dir.getPath());
        log += "폴더 경로 >>  " + dir.getPath() + "\n";

        if(files == null) {
            Log.e(TAG, "null");
            log += "null\n";
        } else {
            Log.e(TAG, "파일개수: " + files.length); // 파일 개수
            log += "파일개수: " + files.length + "\n";

            for(File file : files) {
                Log.e(TAG, file + ""); // 파일명
                log += file + "\n";
            }
        }

        log += "\n";
    }


    /**
     * 파일 복사
     * @param from : 복사하기 전 파일 경로 (파일명 포함)
     * @param to : 복사한 후 파일 경로 (파일명 포함)
     * @throws Exception
     **/
    private static void copyFile(String from, String to) throws Exception{
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;

        try{
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);
            in = fis.getChannel();
            out = fos.getChannel();

            in.transferTo(0, in.size(), out);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(out != null)
                out.close();
            if(in != null)
                in.close();
            if(fis != null)
                fis.close();
            if(fos != null)
                fos.close();
        }
    }


}
