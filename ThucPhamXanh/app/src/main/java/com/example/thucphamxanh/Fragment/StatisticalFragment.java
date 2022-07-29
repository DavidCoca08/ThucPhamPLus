package com.example.thucphamxanh.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.thucphamxanh.Adapter.AdapterBill;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.databinding.FragmentStatisticalBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatisticalFragment extends Fragment {
    private final String TAG = "StatisticalFragment";
    FragmentStatisticalBinding binding;
    TextInputLayout fromDate, toDate;
    Button btnSearch;
    TextView totalRevenue;
    final Calendar myCalendar= Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    NumberFormat numberFormat = new DecimalFormat("#,##0");
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AdapterBill adapterBill;
    private List<Bill> listBill = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticalBinding.inflate(inflater, container, false);
        getBill();
        initUi();
        getDate();

        return binding.getRoot();
    }

    public void initUi(){
        fromDate = binding.textStatisticalFragmentFromDate;
        toDate = binding.textStatisticalFragmentToDate;
        btnSearch = binding.btnStatisticalFragmentSearch;
        totalRevenue = binding.tvStatisticalFragmentTotalRevenue;
        recyclerView = binding.recyclerViewStatisticalFragmentItemBill;
        adapterBill = new AdapterBill(listBill, getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterBill = new AdapterBill(listBill,getContext());
        recyclerView.setAdapter(adapterBill);
    }

    public void getBill(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bill");
        SharedPreferences preferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBill.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Bill bill = snap.getValue(Bill.class);
                    if (user.equals("admin") && bill.getStatus().equals("Yes")){
                        listBill.add(bill);
                    }else if(user.equals(bill.getIdPartner()) && bill.getStatus().equals("Yes")){
                        listBill.add(bill);
                    }
                    int sum = 0;
                    for (int i = 0; i < listBill.size(); i++) {
                        sum += listBill.get(i).getTotal();
                    }
                    totalRevenue.setText(numberFormat.format(sum));
                }
                adapterBill.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDate(){
        DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                fromDate.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                toDate.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        };

        fromDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),startDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        toDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),endDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(view -> {
            SharedPreferences preferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
            String uId = preferences.getString("username", "");
            recyclerView.setVisibility(View.GONE);

            String startdate = fromDate.getEditText().getText().toString();
            String todate = toDate.getEditText().getText().toString();
            final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("Bill");
            Log.d(TAG, "getDate: ");
            rootReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "onDataChange: " + snapshot.getValue());
                    int total = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Bill bill = dataSnapshot.getValue(Bill.class);
                        if (!bill.getIdPartner().equals(uId))
                            continue;
                        Log.d(TAG, "onDataChange: " + bill);
                        SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date dayOut = spd.parse(bill.getDayOut());
                            Date startDate = spd.parse(startdate);
                            Date toDate = spd.parse(todate);
                            Log.d(TAG, "onDataChange: " + dayOut.toString());
                            Log.d(TAG, "onDataChange: " + startDate.toString());
                            Log.d(TAG, "onDataChange: " + toDate.toString());
                            Log.d(TAG, "onDataChange: " + bill.getDayOut());
                            if (dayOut.after(startDate) && dayOut.before(toDate)) {
                                //TODO hoa don hop le
                                Log.d(TAG, "onDataChange: ngay xuat hoa don trong ....");
                                total += bill.getTotal();
                            }
                            else {
                                Log.d(TAG, "onDataChange: ngay xuat hoa don kh  hop le");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    totalRevenue.setText(String.valueOf(total));
                    Log.d(TAG, "onDataChange: ");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "onCancelled: ", error.toException() );
                }
            });
        });
    }
}