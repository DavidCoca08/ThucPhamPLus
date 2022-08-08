package com.example.thucphamxanh.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.thucphamxanh.Activity.SignInActivity;
import com.example.thucphamxanh.Fragment.Profile.ProfileFragment;
import com.example.thucphamxanh.Fragment.Profile.ProfileViewModel;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentPersonalBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalFragment extends Fragment {
    private static final String TAG = "PersonalFragment";
    FragmentPersonalBinding binding;
    Button btn_logout_personal, btn_changepassword_personal;
    TextView tvNumberPhoneUser, tvEdit;
    ImageView imgUser;
    List<User> listUser = new ArrayList<>();
    User user;
    Partner partner;
    ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        btn_logout_personal = binding.btnPersonalFragmentLogoutPersonal;
        btn_changepassword_personal = binding.btnPersonalFragmentChangePasswordPersonal;
        tvNumberPhoneUser = binding.tvPersonalFragmentNumberPhoneUser;
//        tvNumberPhoneUser.setText(sharedPreferences.getString("username",""));
        tvEdit = binding.tvPersonalFragmentEditUser;
        imgUser = binding.imgPersonalFragmentImgUser;
        imgUser.setImageResource(R.drawable.ic_avatar_default);

        Log.d(TAG, "onCreateView: ");
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        btn_logout_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                logout();
                getActivity().finishAffinity();
            }

            private void logout() {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("My_User",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged", false);
                editor.commit();
            }
        });

        btn_changepassword_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new ChangePasswordFragment()).addToBackStack(null).commit();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new ProfileFragment()).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = profileViewModel.getUser().getValue();
        Partner partner = profileViewModel.getPartner().getValue();
        if (user != null) {
            profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    Log.d("TAG", "onChanged: ");
                    tvNumberPhoneUser.setText(user.getName());
                    Glide.with(requireActivity()).load(user.getStrUriAvatar()).error(R.drawable.ic_avatar_default).into(imgUser);
                }
            });
        } else if (partner != null) {
            profileViewModel.getPartner().observe(getViewLifecycleOwner(), new Observer<Partner>() {
                @Override
                public void onChanged(Partner partner) {
                    Log.d("TAG", "onChanged: ");
                    try {
                        byte[] decodeString = Base64.decode(partner.getImgPartner(), Base64.DEFAULT);
                        tvNumberPhoneUser.setText(partner.getUserPartner() + "\n" + partner.getNamePartner());
                        Glide.with(requireActivity())
                                .load(decodeString)
                                .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                                .error(R.drawable.ic_avatar_default)
                                .into(imgUser);
                    } catch (Exception e) {
                        Log.e(TAG, "onChanged: ", e);
                    }

                }
            });
        }
        Log.d(TAG, "onViewCreated: " + user);
        profileViewModel.getUser().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "onChanged: ");
            }
        });
    }

    public void getUser(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        Log.d("username", String.valueOf(id));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    User user1 = snap.getValue(User.class);
                    if (id.equals(user1.getPhoneNumber())){
                        listUser.add(user1);
                    }
                }
                Log.d("size", String.valueOf(listUser.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}