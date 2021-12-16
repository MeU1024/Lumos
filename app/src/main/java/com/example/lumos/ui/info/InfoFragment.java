package com.example.lumos.ui.info;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.content.Context;
import com.example.lumos.R;
import com.example.lumos.ui.home.HomeViewModel;

public class InfoFragment extends Fragment {

//    private InfoViewModel mViewModel;
//
//    public static InfoFragment newInstance() {
//        return new InfoFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_info, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(InfoViewModel.class);
//        // TODO: Use the ViewModel
//    }

    private InfoViewModel infoViewModel;
    private TextView user_nickname_tv;
    private String Username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        Boolean LoginStatus = false;
        SharedPreferences sp=getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        LoginStatus = sp.getBoolean("isLogin" , false);

        user_nickname_tv = root.findViewById(R.id.user_nickname);

        if (LoginStatus) {
            //已登录
            View loginView = root.findViewById(R.id.user_cardview3);
            loginView.setVisibility(View.INVISIBLE);

            View exitView = root.findViewById(R.id.user_cardview4);
            exitView.setVisibility(View.VISIBLE);
            user_nickname_tv.setText(getUserName());

        }
        else{
            //未登录
            View loginView = root.findViewById(R.id.user_cardview3);
            loginView.setVisibility(View.VISIBLE);

            View exitView = root.findViewById(R.id.user_cardview4);
            exitView.setVisibility(View.INVISIBLE);
        }

        //final TextView textView = root.findViewById(R.id.text_info);
        //下面代码中observe()方法里的this，可能在IDE里是有红色波浪线的，但是却是可以编译的。
        //也可以直接把this改成getViewLifecycleOwner() 就不会报错了
//        infoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        setIcons(root);

        return root;
    }

    private void setIcons(View root){
        Bitmap bm_info_universe = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.universe);
        UserBtnView info_universe = (UserBtnView)root.findViewById(R.id.info_universe);
        info_universe.setBtn_icon(bm_info_universe);

        Bitmap bm_info_help = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.help);
        UserBtnView info_help = (UserBtnView)root.findViewById(R.id.info_help);
        info_help.setBtn_icon(bm_info_help);

        Bitmap bm_info_share = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.share);
        UserBtnView info_share = (UserBtnView)root.findViewById(R.id.info_share);
        info_share.setBtn_icon(bm_info_share);

        Bitmap bm_info_about = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.about);
        UserBtnView info_about = (UserBtnView)root.findViewById(R.id.info_about);
        info_about.setBtn_icon(bm_info_about);

        Bitmap bm_info_login = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.login);
        UserBtnView info_login = (UserBtnView)root.findViewById(R.id.info_login);
        info_login.setBtn_icon(bm_info_login);

        Bitmap bm_info_register = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.register);
        UserBtnView info_register = (UserBtnView)root.findViewById(R.id.info_register);
        info_register.setBtn_icon(bm_info_register);

        Bitmap bm_info_exit = (Bitmap) BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.exit);
        UserBtnView info_exit = (UserBtnView)root.findViewById(R.id.info_exit);
        info_exit.setBtn_icon(bm_info_exit);



    }

    private String getUserName(){
        String loginUserName = null;
        SharedPreferences sp= getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        loginUserName = sp.getString("loginUserName" , null);
        return loginUserName;

    }



}