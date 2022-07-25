package com.example.thucphamxanh.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.thucphamxanh.databinding.FragmentStatisticalBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatisticalFragment extends Fragment {
    FragmentStatisticalBinding binding;
    TextInputLayout fromDate, toDate;
    Button btnSearch;
    TextView totalRevenue;
    final Calendar myCalendar= Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticalBinding.inflate(inflater, container, false);
        initUi();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
            }
        };

        fromDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                fromDate.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        });

        toDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                toDate.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        });

        return binding.getRoot();
    }
    public void initUi(){
        fromDate = binding.textStatisticalFragmentFromDate;
        toDate = binding.textStatisticalFragmentToDate;
        btnSearch = binding.btnStatisticalFragmentSearch;
        totalRevenue = binding.tvStatisticalFragmentTotalRevenue;
    }
}