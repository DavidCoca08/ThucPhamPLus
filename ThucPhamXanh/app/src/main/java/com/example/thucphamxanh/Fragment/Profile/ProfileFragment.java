package com.example.thucphamxanh.Fragment.Profile;

import static com.example.thucphamxanh.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.thucphamxanh.MainActivity;
import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = mStorage.getReference();
    private User user;

    private EditText etFullName, etEmail;
    private Button btnUpdateInfoUser;
    private ImageView ivAvatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: start");
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        user = profileViewModel.getUser().getValue();
        Log.d(TAG, "onCreate: " + user.toString());
        Log.d(TAG, "onCreate: end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUI();
        initListener();
        Log.d(TAG, "onCreateView: end");
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBitmapAvatarWithViewModel();
        setUserInfoToView();
        ivAvatar.setImageBitmap(user.getBitmapAvatar());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void initUI() {
        ivAvatar = binding.getRoot().findViewById(R.id.iv_profile_fragment_avatar);
        etFullName = binding.getRoot().findViewById(R.id.et_profile_fragment_full_name);
        etEmail = binding.getRoot().findViewById(R.id.et_profile_fragment_email);
        btnUpdateInfoUser = binding.getRoot().findViewById(R.id.btn_profile_fragment_update);
    }
    private void initListener() {
        ivAvatar.setOnClickListener(this::onClick);
        btnUpdateInfoUser.setOnClickListener(this::onClick);
    }
    public void setBitmapAvatarWithViewModel() {
        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Glide.with(getActivity()).load(user.getBitmapAvatar()).error(R.drawable.ic_avatar_default).into(ivAvatar);
                Log.d(TAG, "onChanged: ");
                Log.d(TAG, "onChanged: " + user.toString());
            }
        });
    }
    private void setUserInfoToView() {
        //solution 1 use FirebaseUser
        User user = new User();
        user = profileViewModel.getUser().getValue();
        Log.d(TAG, "setUserInfoToView: " + user);
        Log.d(TAG, "setUserInfoToView: setAvatar on setUserInfo method" );
        etFullName.setText(user.getName());
        etEmail.setText(user.getEmail());
/*        Glide.with(getActivity())
                .load(profileViewModel.getUser().getValue().getBitmapAvatar())
                .error(R.drawable.ic_avatar_default)
                .into(ivAvatar);*/
        Glide.with(getActivity())
                .load(user.getBitmapAvatar())
                .error(R.drawable.ic_avatar_default)
                .into(ivAvatar);
        Log.d(TAG, "setUserInfoToView: " + profileViewModel.getUser().getValue().getBitmapAvatar().toString());
        //solution 2: communicating with Activity


    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_profile_fragment_avatar:
                onClickRequestPermission();
                break;
            case R.id.btn_profile_fragment_update:
                updateUserInfo();
                break;
        }
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
    private void updateUserInfo() {
        User user = new User();
        Bitmap bitmap = ((BitmapDrawable) ivAvatar.getDrawable()).getBitmap();

        user.setBitmapAvatar(bitmap);
        user.setName(etFullName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        //up ảnh lên storage, lấy Uri storage set vào uriAvatar của user, change user trong ViewModel
        // Get the data from an ImageView as bytes
        StorageReference imgRef = mStorageReference.child("images");
        StorageReference spaceRef = mStorageReference.child("images/" + user.getEmail() + "_avatar.jpg");
        String strUri = spaceRef.getName();
        ivAvatar.setDrawingCacheEnabled(true);
        ivAvatar.buildDrawingCache();
        Bitmap bitmap2 = ((BitmapDrawable) ivAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = spaceRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "onFailure: ");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.d(TAG, "onSuccess: ");
                //user.setUriAvatar(spaceRef.getDownloadUrl().getResult());
                FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(user.getName())
                        .setPhotoUri(Uri.parse(spaceRef.getPath()))
                        .build();

                userAuth.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                    ((MainActivity)requireActivity()).showUserInformation();
                                }
                            }
                        });

                //sửa thông tin trong nav bar
            }
        });

        //use ViewModel communicating with MainAcitivy
        //profileViewModel.setUser(user);

    }




}