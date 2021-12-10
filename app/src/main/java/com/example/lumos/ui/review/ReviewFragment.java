package com.example.lumos.ui.review;

import android.content.Context;
import android.os.Bundle;
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

import com.example.lumos.databinding.FragmentReviewBinding;

import com.example.lumos.R;

public class ReviewFragment extends Fragment {

    private ReviewViewModel reviewViewModel;
    private FragmentReviewBinding binding;
    private Context mContext;

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

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //显示用户选择的日期
                Toast.makeText(mContext, year + "年" + month + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
                String str_date = "You have lighted up " + "?%" + " stars on " + year + "." + (month+1) + "." + dayOfMonth + "!";
                tv_date.setText(str_date);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}