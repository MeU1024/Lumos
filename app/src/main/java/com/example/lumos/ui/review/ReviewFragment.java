package com.example.lumos.ui.review;

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
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lumos.DBOpenHelper;
import com.example.lumos.databinding.FragmentReviewBinding;

import com.example.lumos.R;

import java.util.Calendar;

public class ReviewFragment extends Fragment {

    private ReviewViewModel reviewViewModel;
    private FragmentReviewBinding binding;
    private Context mContext;
    String str_date = null;
    Integer allhabit_someday,done_someday;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reviewViewModel =
                new ViewModelProvider(this).get(ReviewViewModel.class);

        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.mContext = getActivity();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        CalendarView calendarView = getActivity().findViewById(R.id.calendarView);
        TextView tv_date = getActivity().findViewById(R.id.text_review_date);

        //Toast.makeText(mContext, getDateToday(),Toast.LENGTH_SHORT).show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //显示用户选择的日期

                //打开数据库，获取当前选择的日期
                String loginUserName = null;
                loginUserName = getUserName();
                DBOpenHelper dbsqLiteOpenHelper = new DBOpenHelper(getActivity(), loginUserName+".db", null, 1);
                final SQLiteDatabase db = dbsqLiteOpenHelper.getWritableDatabase();
                String choose_String = year +"-"+ (month+1) +"-" +dayOfMonth;
                String dateToday = getDateToday();
                //Toast.makeText(mContext, choose_String,Toast.LENGTH_SHORT).show();

                //如果选的是今天，
                if(choose_String.equals(getDateToday())){
                    String sql_allhabit = "select count(*) from habit where state = 0 or lday = ?";
                    Cursor cursor_allhabit = db.rawQuery(sql_allhabit, new String[]{dateToday.toString()});
                    cursor_allhabit.moveToFirst();
                    long allhabit_count = cursor_allhabit.getLong(0);
                    cursor_allhabit.close();

                    String sql_done = "select count(*) from habit where lday = ? ";
                    Cursor cursor_done = db.rawQuery(sql_done, new String[]{dateToday.toString()});
                    cursor_done.moveToFirst();
                    long done_count = cursor_done.getLong(0);
                    cursor_done.close();

                    Cursor cursor_existed =  db.rawQuery("SELECT * FROM record WHERE today = ?",
                            new String[]{dateToday.toString()});
                    //存在数据才返回true
                    if(cursor_existed.moveToFirst())
                    {
                        cursor_existed.close();
                        db.execSQL("UPDATE record SET allhabit = ?,done = ? WHERE today = ?",
                                new String[]{Long.toString(allhabit_count),Long.toString(done_count),dateToday.toString()});


                    }else {
                        cursor_existed.close();
                        ContentValues values = new ContentValues();
                        values.put("today", dateToday);
                        values.put("allhabit", allhabit_count);
                        values.put("done", done_count);
                        long msg = db.insert("record", "try", values);
                        Log.d("Debug", String.valueOf(msg));
                    }

                    long progressper = 100 * done_count/allhabit_count;
                    if(allhabit_count == 0){
                        str_date = "No star needed to light up~";
                    }else{
                        str_date = "You have lighted up " + progressper + "%" + " stars on \n\n " + year + "." + (month+1) + "." + dayOfMonth + "!";
                    }
                    tv_date.setText(str_date);

                }else{
                    //不是今天
                    Cursor cursor_someday =  db.rawQuery("SELECT * FROM record WHERE today = ?",
                            new String[]{choose_String.toString()});

                    if(cursor_someday.moveToFirst())
                    {
                        allhabit_someday = cursor_someday.getInt(cursor_someday.getColumnIndex("allhabit"));
                        done_someday = cursor_someday.getInt(cursor_someday.getColumnIndex("done"));
                        cursor_someday.close();

                        if(allhabit_someday == 0){
                            str_date = "No star needed to light up~";
                        }else{

                            str_date = "You have lighted up " + 100*done_someday/allhabit_someday + "%" + " stars on \n\n" + year + "." + (month+1) + "." + dayOfMonth + "!";
                        }

                        tv_date.setText(str_date);
                    }
                    else{
                        tv_date.setText("No Record For This Day!");
                    }

                }

                db.close();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

}