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
import android.widget.Toast;

import com.example.thucphamxanh.Adapter.StatisticalAdapter;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.databinding.FragmentStatisticalBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StatisticalFragment extends Fragment {
    private final String TAG = "StatisticalFragment";
    private FragmentStatisticalBinding binding;
    private TextInputLayout getDateSearch;
    private Button btnSearch;
    private TextView totalRevenue, tvHide;
    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    NumberFormat numberFormat = new DecimalFormat("#,##0");
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StatisticalAdapter adapterStatistical;
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

    public void initUi() {
        getDateSearch = binding.textStatisticalFragmentGetDateSearch;
        btnSearch = binding.btnStatisticalFragmentSearch;
        totalRevenue = binding.tvStatisticalFragmentTotalRevenue;
        recyclerView = binding.recyclerViewStatisticalFragmentItemBill;
        adapterStatistical = new StatisticalAdapter(listBill, getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterStatistical);
        tvHide = binding.tvStatisticalFragmentHide;
    }

    public void getBill() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bill");
        SharedPreferences preferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        String user = preferences.getString("username", "");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBill.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Bill bill = snap.getValue(Bill.class);
                    if (user.equals("admin") && bill.getStatus().equals("Yes")) {
                        listBill.add(bill);
                    } else if (user.equals(bill.getIdPartner()) && bill.getStatus().equals("Yes")) {
                        listBill.add(bill);
                    }
                    int sum = 0;
                    for (int i = 0; i < listBill.size(); i++) {
                        sum += listBill.get(i).getTotal();
                    }
                    totalRevenue.setText(numberFormat.format(sum));
                }
                adapterStatistical.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                getDateSearch.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        };

        getDateSearch.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), onDateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(view -> {
            SharedPreferences preferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
            String user = preferences.getString("username", "");
            recyclerView.setVisibility(View.GONE);
            tvHide.setVisibility(View.GONE);
            String getdatesearch = getDateSearch.getEditText().getText().toString();
            if (getdatesearch.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            } else {
                final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("Bill");
                rootReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange: " + snapshot.getValue());
                        int total = 0;
                        List<Bill> list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Bill bill = dataSnapshot.getValue(Bill.class);
                            if (user.equals("admin") && bill.getStatus().equals("Yes")) {
                                if (getdatesearch.equals(bill.getDayOut())) {
                                    total += bill.getTotal();
                                    list.add(bill);
                                    adapterStatistical = new StatisticalAdapter(list, getContext());
                                    recyclerView.setAdapter(adapterStatistical);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    tvHide.setVisibility(View.VISIBLE);
                                }
                            } else if (user.equals(bill.getIdPartner()) && bill.getStatus().equals("Yes")) {
                                if (getdatesearch.equals(bill.getDayOut())) {
                                    total += bill.getTotal();
                                    list.add(bill);
                                    adapterStatistical = new StatisticalAdapter(list, getContext());
                                    recyclerView.setAdapter(adapterStatistical);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    tvHide.setVisibility(View.VISIBLE);
                                }
                            }
//                            if (user.equals("admin") && bill.getStatus().equals("Yes")) {
//                                try {
//                                    Date dayOut = spd.parse(bill.getDayOut());
//                                    Date startDate = sdf.parse(startdate);
//                                    Date toDate = sdf.parse(todate);
//                                    if (dayOut.after(startDate) && dayOut.before(toDate)) {
//                                        //TODO hoa don hop le
//                                        Log.d(TAG, "onDataChange: ngay xuat hoa don trong ....");
//                                        total += bill.getTotal();
//                                    } else {
//                                        Toast.makeText(getContext(), "Không tìm thấy doanh thu ngày vừa chọn", Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            } else if (user.equals(bill.getIdPartner()) && bill.getStatus().equals("Yes")) {
//                                try {
//                                    Date dayOut = spd.parse(bill.getDayOut());
//                                    Date startDate = spd.parse(startdate);
//                                    Date toDate = spd.parse(todate);
//                                    if (dayOut.after(startDate) && dayOut.before(toDate)) {
//                                        //TODO hoa don hop le
//                                        Log.d(TAG, "onDataChange: ngay xuat hoa don trong ....");
//                                        total += bill.getTotal();
//                                    } else {
//                                        Toast.makeText(getContext(), "Không tìm thấy doanh thu ngày vừa chọn", Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                        }
                        totalRevenue.setText(numberFormat.format(total));
                        adapterStatistical.notifyDataSetChanged();
                        Log.d(TAG, "onDataChange: ");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                    }
                });
            }
        });
    }
}