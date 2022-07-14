package com.example.groceryshopping.ui.Profile;

import static com.example.groceryshopping.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.groceryshopping.MainActivity;
import com.example.groceryshopping.R;
import com.example.groceryshopping.databinding.FragmentProfileBinding;
import com.example.groceryshopping.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private EditText etFullName, etEmail;
    private Button btnUpdateInfoUser;
    private ImageView ivAvatar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUI();
        initListener();
        return root;
    }

    private void initListener() {
        ivAvatar.setOnClickListener(this::onClick);
        btnUpdateInfoUser.setOnClickListener(this::onClick);
    }

    private void setUserInfo() {
        //solution 1 use FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        Log.d(TAG, "setUserInfo: setAvatar on setUserInfo method" );
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatar_default).into(ivAvatar);
        etFullName.setText(user.getDisplayName());
        etEmail.setText(user.getEmail());
        //solution 2: communicating with Activity


    }

    private void initUI() {
        ivAvatar = binding.getRoot().findViewById(R.id.iv_profile_fragment_avatar);
        etFullName = binding.getRoot().findViewById(R.id.et_profile_fragment_full_name);
        etEmail = binding.getRoot().findViewById(R.id.et_profile_fragment_email);
        btnUpdateInfoUser = binding.getRoot().findViewById(R.id.btn_profile_fragment_update);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        setBitmapAvatar();
        //setUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_profile_fragment_avatar:
                onClickRequestPermission();
                break;
            case R.id.btn_profile_fragment_update:
                updateProfileUser();
                break;
        }
    }

    private void updateProfileUser() {
        User user = new User();
        Bitmap bitmap = ((BitmapDrawable) ivAvatar.getDrawable()).getBitmap();
        user.setBitmapAvatar(bitmap);
        user.setName(etEmail.getText().toString());
        user.setEmail(etEmail.getText().toString());
        profileViewModel.setUser(user);
    }

    private void onClickRequestPermission() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null) {
            Log.d(TAG, "onClickRequestPermission: android 6.0");
            return;
        }
        //check version < android 6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mainActivity.openGallery();
            Log.d(TAG, "onClickRequestPermission: android > 6.0");
            return;
        }
        //check user permission when version >= android 6.0
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
         == PackageManager.PERMISSION_GRANTED) {
            mainActivity.openGallery();
        } else {
            String[] permission = {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            getActivity().requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    public void setBitmapAvatar() {
        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                etEmail.setText(user.getEmail());
                etFullName.setText(user.getName());
                Glide.with(getActivity()).load(user.getBitmapAvatar()).error(R.drawable.ic_avatar_default).into(ivAvatar);
            }
        });
    }
}