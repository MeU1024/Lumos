package com.example.lumos.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.lumos.MainActivity;
import com.example.lumos.R;
import com.example.lumos.databinding.FragmentHomeBinding;
import com.example.lumos.ui.collection.CollectionStarView;
import com.example.lumos.ui.home.HomeViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    //    private HomeViewModel homeViewModel;
//    private FragmentHomeBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
    private HomeViewModel homeViewModel;
    private List<Habit> TodayHabitList;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        this.mContext = getActivity();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            getTodayHabit();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.layout_home);
        //把数据显示至屏幕
        for (Habit p : TodayHabitList) {

            TodayStarView tsv = new TodayStarView(mContext,null);
            tsv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tsv.setHabitName(p.getName());
            tsv.setStarIcon(p.getMax(),p.getProgress());
            tsv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, tsv.getName(),Toast.LENGTH_SHORT).show();

                    //更改星星的状态
                    String loginUserName = null;
                    loginUserName = getUserName();
                    DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(mContext, loginUserName+".db", null, 1);
                    final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();

                    db.execSQL("UPDATE habit SET lday = ? WHERE name = ? ",
                            new String[]{getDateToday(),tsv.getName()});
                    db.close();
                    //tsv.setStarIcon(1,1);

                }
            });


            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tsv.getLayoutParams();
            lp.setMargins((int) (Math.random()*550), 100, 0, 0);
            tsv.setLayoutParams(lp);
            ll.addView(tsv);


        }


    }

    private void getTodayHabit() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TodayHabitList = new ArrayList<Habit>();

        String loginUserName = null;
        loginUserName = getUserName();
        DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(getActivity(), loginUserName+".db", null, 1);
        final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
        //

        //更新任务状态
        db.execSQL("UPDATE habit SET state = 2 WHERE lday != ? or ? AND state != 1",
                new String[]{getYesterday(),getDateToday()});

        //显示今日打卡
        String sql_select_today_habit = "select * from habit where  state = 2 ";
        Cursor cursor_select_today_habit = db.rawQuery(sql_select_today_habit,null);
        //Cursor cursor_select_today_habit = db.query("habit", null, "state=0", null, null, null, null);
        while(cursor_select_today_habit.moveToNext()){

            String name = cursor_select_today_habit.getString(cursor_select_today_habit.getColumnIndex("name"));
            String des = cursor_select_today_habit.getString(cursor_select_today_habit.getColumnIndex("des"));
            Integer days = cursor_select_today_habit.getInt(cursor_select_today_habit.getColumnIndex("days"));
            Integer state = cursor_select_today_habit.getInt(cursor_select_today_habit.getColumnIndex("state"));
            String str_cday = cursor_select_today_habit.getString(cursor_select_today_habit.getColumnIndex("cday"));
            String str_lday = cursor_select_today_habit.getString(cursor_select_today_habit.getColumnIndex("lday"));
            String str_sday = cursor_select_today_habit.getString(cursor_select_today_habit.getColumnIndex("sday"));

            Date cday = simpleDateFormat.parse(str_cday);
            Date lday = simpleDateFormat.parse(str_lday);
            Date sday = simpleDateFormat.parse(str_sday);

            Habit p = new Habit(name,days,des,cday,lday,sday,state);
            TodayHabitList.add(p);
        }

        cursor_select_today_habit.close();
        db.close();
    }

    private String getUserName(){
        String loginUserName = null;
        SharedPreferences sp= getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        loginUserName = sp.getString("loginUserName" , null);
        return loginUserName;

    }

    private String getDateToday(){

        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String DateToday = year+"-"+month+"-"+day;
        return DateToday;
    }

    private String getYesterday(){

        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String DateToday = year+"-"+month+"-"+ (day-1);
        return DateToday;
    }
}