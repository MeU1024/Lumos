package com.example.lumos.ui.info;

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

public class UserBtnView extends RelativeLayout {
    private TextView tv_title;
    private ImageView btn_icon;
    private String namespace = "http://schemas.android.com/apk/res/com.example.lumos";
    private String title;
    private String icon_path;
    public UserBtnView(Context context, AttributeSet attrs){
        super(context,attrs);

        View view = View.inflate(context, R.layout.component_user_titles,this);
        tv_title = (TextView) view.findViewById(R.id.user_btn_title);
        title = attrs.getAttributeValue(namespace, "user_titles_components_title");
        if (title != null) {
            tv_title.setText(title);
        }

        btn_icon = (ImageView)view.findViewById(R.id.user_btn_icon);
        icon_path = attrs.getAttributeValue(namespace,"icon_path");
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
