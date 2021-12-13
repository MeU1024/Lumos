package com.example.lumos.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lumos.R;
import com.example.lumos.ui.info.UserBtnView;

import java.io.File;

public class TodayStarView extends RelativeLayout{
    private TextView tv_title;
    private ImageView btn_icon;
    private String namespace = "http://schemas.android.com/apk/res/com.example.lumos";
    private String title;
    private String icon_path;
    public TodayStarView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);

        View view = View.inflate(context, R.layout.component_today_star,this);
        tv_title = (TextView) view.findViewById(R.id.cmnt_star_name);
        //title = attrs.getAttributeValue(namespace, "star_name");
        if (title != null) {
            tv_title.setText(title);
        }

        btn_icon = (ImageView)view.findViewById(R.id.cmnt_star_icon);
        //icon_path = attrs.getAttributeValue(namespace,"star_icon");
        if(icon_path != null){
            String real_path = "@drawable/"+icon_path;
            Uri uri = Uri.fromFile(new File(real_path));
            System.out.println(uri);
            btn_icon.setImageURI(uri);
        }
    }
    public void setBtn_icon(Bitmap bm){
        btn_icon.setImageBitmap(bm);
    }
    public void setHabitName(String name){
        tv_title.setText(name);
    }
    public void setStarIcon(int max,int pro){

        Bitmap bm_home_star = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.stars);

        double percent = pro / max;
        if(percent < 0.3){
            bm_home_star = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.smalls);
        }else if (percent <0.7){
            bm_home_star = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.mediums);
        }else{
            bm_home_star = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.bigs);
        }
        setBtn_icon(bm_home_star);
    }



}
