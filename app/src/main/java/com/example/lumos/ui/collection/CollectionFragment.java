package com.example.lumos.ui.collection;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lumos.DBOpenHelper;
import com.example.lumos.Habit;
import com.example.lumos.R;
import com.example.lumos.databinding.FragmentCollectionBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionFragment extends Fragment {

    private CollectionViewModel collectionViewModel;
    private FragmentCollectionBinding binding;
    private List<Habit> HabitList;
    private Context mContext;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        collectionViewModel =
                new ViewModelProvider(this).get(CollectionViewModel.class);

        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.mContext = getActivity();

//        final TextView textView = binding.textCollection;
//        collectionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            getHabitList();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.layout_collection);
        //把数据显示至屏幕
        for (Habit p : HabitList) {
            //1.集合中每有一条元素，就new一个textView
            TextView tv = new TextView(mContext);
//            //2.把人物的信息设置为文本框的内容
//            tv.setText(p.getName());
//            tv.setTextSize(18);
//            //3.把textView设置为线性布局的子节点
//            ll.addView(tv);


            CollectionStarView csv = new CollectionStarView(mContext,null);
            csv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            csv.setHabitName(p.getName());
            csv.setDescription(p.getDes());
            csv.setMax(p.getMax());
            csv.setProgress(p.getProgress());
            if(p.getState() == 1){
                csv.setDone();
            }
            else if(p.getState()==2){
                csv.setDisappear();
            }


            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) csv.getLayoutParams();
            lp.setMargins(0, 80, 0, 0);
            csv.setLayoutParams(lp);
            ll.addView(csv);


        }


    }

    public void getHabitList() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HabitList = new ArrayList<Habit>();

        String loginUserName = null;
        loginUserName = getUserName();
        DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(getActivity(), loginUserName+".db", null, 1);
        final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

        String sql_select_habit = "select * from habit ORDER BY state desc";
        Cursor cursor_select_habit = db.rawQuery(sql_select_habit, null);
        //cursor_select_habit.moveToFirst();
        while(cursor_select_habit.moveToNext()){

            String name = cursor_select_habit.getString(cursor_select_habit.getColumnIndex("name"));
            String des = cursor_select_habit.getString(cursor_select_habit.getColumnIndex("des"));
            Integer days = cursor_select_habit.getInt(cursor_select_habit.getColumnIndex("days"));
            Integer state = cursor_select_habit.getInt(cursor_select_habit.getColumnIndex("state"));
            String str_cday = cursor_select_habit.getString(cursor_select_habit.getColumnIndex("cday"));
            String str_lday = cursor_select_habit.getString(cursor_select_habit.getColumnIndex("lday"));
            String str_sday = cursor_select_habit.getString(cursor_select_habit.getColumnIndex("sday"));

            Date cday = simpleDateFormat.parse(str_cday);
            Date lday = simpleDateFormat.parse(str_lday);
            Date sday = simpleDateFormat.parse(str_sday);

            Habit p = new Habit(name,days,des,cday,lday,sday,state);
            HabitList.add(p);
        }

        cursor_select_habit.close();
        db.close();
    }

    private String getUserName(){
        String loginUserName = null;
        SharedPreferences sp= getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        loginUserName = sp.getString("loginUserName" , null);
        return loginUserName;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}