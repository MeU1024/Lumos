package com.example.lumos.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lumos.R;

import java.io.File;

public class TodayStarView extends RelativeLayout{
    private TextView tv_title;
    private ImageView btn_icon;
    private String namespace = "http://schemas.android.com/apk/res/com.example.lumos";
    private String title;
    private String icon_path;
    public TodayStarView(Context context, AttributeSet attrs){
        super(context,attrs);

        View view = View.inflate(context, R.layout.component_today_star,this);
        tv_title = (TextView) view.findViewById(R.id.cmnt_star_name);
        title = attrs.getAttributeValue(namespace, "star_name");
        if (title != null) {
            tv_title.setText(title);
        }

        btn_icon = (ImageView)view.findViewById(R.id.cmnt_star_icon);
        icon_path = attrs.getAttributeValue(namespace,"star_icon");
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
}
