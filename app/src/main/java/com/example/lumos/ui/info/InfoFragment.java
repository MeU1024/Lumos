package com.example.lumos.ui.info;

import androidx.lifecycle.ViewModelProvider;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        //是否显示登录按钮
        if (false) {    // 对管理员显示审核评论入口
            View loginView = root.findViewById(R.id.user_cardview3);
            loginView.setVisibility(View.VISIBLE);

            View exitView = root.findViewById(R.id.user_cardview4);
            exitView.setVisibility(View.INVISIBLE);

        }
        else{
            View loginView = root.findViewById(R.id.user_cardview3);
            loginView.setVisibility(View.INVISIBLE);

            View exitView = root.findViewById(R.id.user_cardview4);
            exitView.setVisibility(View.VISIBLE);

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
        return root;
    }


}