package com.example.auto_install;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Decompress {
    private String _zipFile;  // zip 파일명 (확장자 제외)
    private String _location; // unzip 할 위치

    public Decompress(String zipFile, String location) {
        _zipFile = location + zipFile + ".zip";
        _location = location + zipFile + File.separator;

        File f = new File(_location);
        _dirChecker(zipFile);
        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }

    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;

            while ((ze = zin.getNextEntry()) != null) {
                Log.e("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    BufferedInputStream in = new BufferedInputStream(zin);
                    BufferedOutputStream out = new BufferedOutputStream(fout);
                    byte b[] = new byte[1024];
                    int n;
                    while ((n = in.read(b,0,1024)) >= 0) {
                        out.write(b,0, n);
                    }
                    zin.closeEntry();
                    fout.close();
                }
            }

            zin.close();
            Log.e("Decompress", "Unzip Success!");
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
        }
    }


    //변수 location에 저장된 directory의 폴더를 만듭니다.

    /**
     * unzip 한 파일이 directory라면, _location 안에 dir 생성
     * @param dir : unzip하여 나온 directory 이름
     */
    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }
}
