package com.example.lumos.ui.collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lumos.R;

import java.io.File;

public class CollectionStarView extends RelativeLayout {
    private TextView tv_title,tv_des;
    private ProgressBar progress;
    private ImageView btn_icon;
    private String namespace = "http://schemas.android.com/apk/res/com.example.lumos";
    private String title,description;
    private String icon_path;

    public CollectionStarView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);

        View view = View.inflate(context, R.layout.component_collection_star,this);
        tv_title = (TextView) view.findViewById(R.id.cmnt_collection_star_name);
        tv_des = (TextView) view.findViewById(R.id.cmnt_collection_description);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        btn_icon = (ImageView) view.findViewById(R.id.cmnt_collection_star_icon);

        //title = attrs.getAttributeValue(namespace, "star_name");
        if (title != null) {
            tv_title.setText(title);
        }

        if (description != null) {
            tv_des.setText(description);
        }


        btn_icon = (ImageView)view.findViewById(R.id.cmnt_collection_star_icon);
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
        title = name;
        tv_title.setText(title);
    }
    public void setDescription(String des){
        description = des;
        tv_des.setText(description);
    }

    public void setMax(int Max){

        progress.setMax(Max);
    }

    public void setProgress(int pro){

        progress.setProgress(pro);
    }

    public void setDisappear(){
        Bitmap bm_home_star = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.bigg);
        setStar_icon(bm_home_star);
    }

    public void setDone(){
        Bitmap bm_home_star = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.medium);
        setStar_icon(bm_home_star);
    }

    public void setStar_icon(Bitmap bm){
        btn_icon.setImageBitmap(bm);
    }

}
