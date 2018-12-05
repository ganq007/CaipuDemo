package com.example.geq.caipudemo.db;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.geq.caipudemo.view.CommentPageActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    //保存图片
    private static  void saveCroppedImage(Drawable bmp, ImageView ima) {
        ima.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return true;
            }
        });
    }



    //保存图片
    public static void  saveCroppedImage(final Context context, ImageView img , final Drawable drawable) {

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("保存图片");
                builder.setMessage("是否保存？");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BitmapDrawable bd = (BitmapDrawable) drawable;
                        Bitmap bmp= bd.getBitmap();

//                        File file = new File("/sdcard/myFolder");
//                        if (!file.exists())
//                            file.mkdir();
//
//                        file = new File("/sdcard/temp.jpg".trim());
//                        String fileName = file.getName();
//                        String mName = fileName.substring(0, fileName.lastIndexOf("."));
//                        String sName = fileName.substring(fileName.lastIndexOf("."));

                        // /sdcard/myFolder/temp_cropped.jpg
                        // String newFilePath = "/sdcard/myFolder" + "/" + mName + System.currentTimeMillis()+ sName;
                        File file = new File("/mnt/sdcard",System.currentTimeMillis()+ "CP.PNG");
                        try {
                            file.createNewFile();
                            FileOutputStream fos = new FileOutputStream(file);
                            bmp.compress(Bitmap.CompressFormat.PNG, 50, fos);
                            fos.flush();
                            fos.close();
                         //   发送sd卡挂上的广播 系统受到广播之后 会扫描sd卡 把新保存的文件路径添加到数据库中
                                        Intent intent = new Intent();
                                       intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                        intent.setData(Uri.fromFile(file));
                                      context.sendBroadcast(intent);

                           context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getPath())));
                            Log.e("---Environment", "Environment.getExternalStorageDirectory(): " +Environment.getExternalStorageDirectory().getPath());
                            Toast.makeText(context, "已保存至相册", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //无操作
                    }
                });
                builder.show();
                return true;
            }
        });

    }


}
