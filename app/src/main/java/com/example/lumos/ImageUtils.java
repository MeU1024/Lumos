package com.example.lumos;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUtils {
    //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
    public static void layoutView(View v,int width,int height) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    public static String viewSaveToImage(View view,String child) {
        Log.e("ssh","a");
        /**
         * View组件显示的内容可以通过cache机制保存为bitmap
         * 我们要获取它的cache先要通过setDrawingCacheEnable方法把cache开启，
         * 然后再调用getDrawingCache方法就可 以获得view的cache图片了
         * 。buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，
         * 若果 cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         * 若果要更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         */
        //    view.setDrawingCacheEnabled(true);
        //    view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //设置绘制缓存背景颜色
        //    view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

//    aaa.setImageBitmap(cachebmp);//直接展示转化的bitmap

        //保存在本地 产品还没决定要不要保存在本地
        FileOutputStream fos;
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                Log.e("ssh",sdRoot.toString());
                File file = new File(sdRoot, child+".png");
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");
            //压缩图片 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        view.destroyDrawingCache();
        return sharePic(cachebmp,child);
    }

    private static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        /** 如果不设置canvas画布为白色，则生成透明 */
//    c.drawColor(Color.WHITE);

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    //保存在本地并一键分享
    public static String sharePic(Bitmap cachebmp,String child) {
        final File qrImage = new File(Environment.getExternalStorageDirectory(), child+".jpg");
        if(qrImage.exists())
        {
            qrImage.delete();
        }
        try {
            qrImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(qrImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(cachebmp == null)
        {
            return "";
        }
        cachebmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//    Toast.makeText(this, "保存成功 " + qrImage.getPath().toString(), Toast.LENGTH_SHORT).show();
        return qrImage.getPath();
    }

    public static Bitmap myShot(Activity activity) {
// 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
// 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
// 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
// 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
// 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights-statusBarHeights);
// 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }



    public static void saveToSD(Bitmap bmp, String dirName, String fileName) throws IOException {

// 判断sd卡是否存在

        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {

            File dir = new File(dirName);

// 判断文件夹是否存在，不存在则创建

            if(!dir.exists()){

                dir.mkdir();

            }

            File file = new File(dirName + fileName);

// 判断文件是否存在，不存在则创建

            if (!file.exists()) {

                file.createNewFile();

            }

            FileOutputStream fos = null;

            try {

                fos = new FileOutputStream(file);

                if (fos != null) {

// 第一参数是图片格式，第二个是图片质量，第三个是输出流

                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);

// 用完关闭

                    fos.flush();

                    fos.close();

                }

            } catch (FileNotFoundException e) {

// TODO Auto-generated catch block

                e.printStackTrace();

            } catch (IOException e) {

// TODO Auto-generated catch block

                e.printStackTrace();

            }

        }

    }

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;
        }
        return true;
    }



}
