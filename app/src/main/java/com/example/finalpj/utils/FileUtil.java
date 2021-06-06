package com.example.finalpj.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil
{
    /**
     * 将图片转换成Base64编码的字符串
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_WRAP);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        //LogUtil.e("Push:base64",result.length()+"      "+ result);
        return result;
    }

    //将Bitmap类型的图片转化成file类型，便于上传到服务器
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        String path = Environment.getExternalStorageDirectory() + "/颂温暖";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + "/"+fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 75, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    public static String compressImagePathToImagePath(String imagePath){
        long time = System.currentTimeMillis();
        File temp =  null;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        File file = new File(imagePath);
        //LogUtil.e("FileUtil","before: "+file.length()/1024 +"KB");
        try{
            temp = saveFile(bitmap,time+".jpeg");
            /*LogUtil.e("FileUtil","abosolutePath: " + temp.getAbsolutePath());
            LogUtil.e("FileUtil","canonicalPath: " + temp.getCanonicalPath());
            LogUtil.e("FileUtil","Path: " + temp.getPath());
            LogUtil.e("FileUtil","parentPath: " + temp.getParent());
            LogUtil.e("FileUtil","Name: " + temp.getName());*/
        }catch (IOException e){
            e.printStackTrace();
            //LogUtil.e("FileUtil","文件创建失败");
        }
        //LogUtil.e("FileUtil","after: "+ temp.length()/1024 +"KB");
        return temp.getPath();
    }
}
