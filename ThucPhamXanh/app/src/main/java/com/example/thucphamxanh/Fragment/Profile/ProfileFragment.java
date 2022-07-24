package com.example.thucphamxanh.Fragment.Profile;

import static com.example.thucphamxanh.Activity.MainActivity.MY_REQUEST_CODE;

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
import com.bumptech.glide.signature.ObjectKey;
import com.example.thucphamxanh.Activity.MainActivity;
import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = mStorage.getReference();
    private User user;

    private EditText etFullName, etEmail;
    private TextInputLayout mLayoutName, mLayoutEmail, mLayoutAddress, mLayoutPhoneNumber;
    private Button btnUpdateInfoUser;
    private ImageView ivAvatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: start");
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        user = profileViewModel.getUser().getValue();
        Log.d(TAG, "onCreate: end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initUI();
//        setUserInfoToView();
        Log.d(TAG, "onCreateView: end");
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUserInformation();
        initListener();
//        ivAvatar.setImageBitmap(user.getBitmapAvatar());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void initUI() {
        ivAvatar = binding.getRoot().findViewById(R.id.iv_profile_fragment_avatar);
//        etFullName = binding.getRoot().findViewById(R.id.et_profile_fragment_full_name);
//        etEmail = binding.getRoot().findViewById(R.id.et_profile_fragment_email);
        btnUpdateInfoUser = binding.getRoot().findViewById(R.id.btn_profile_fragment_update);
        mLayoutEmail = binding.getRoot().findViewById(R.id.text_input_layout_profile_fragment_email);
        mLayoutName = binding.getRoot().findViewById(R.id.text_input_layout_profile_fragment_full_name);
        mLayoutAddress = binding.getRoot().findViewById(R.id.text_input_layout_profile_fragment_address);
        mLayoutPhoneNumber = binding.getRoot().findViewById(R.id.text_input_layout_profile_fragment_phone_number);
    }
    private void initListener() {
        ivAvatar.setOnClickListener(this::onClick);
        btnUpdateInfoUser.setOnClickListener(this::onClick);
    }
    public void showUserInformation() {
        Log.d(TAG, "setBitmapAvatarWithViewModel: start");
        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mLayoutName.getEditText().setText(user.getName());
                mLayoutEmail.getEditText().setText(user.getEmail());
                mLayoutAddress.getEditText().setText(user.getAddress());
                mLayoutPhoneNumber.getEditText().setText(user.getPhoneNumber());
                if (user.getBitmapAvatar() != null) {
                    Glide.with(requireActivity())
                            .load(user.getBitmapAvatar())
                            .error(R.drawable.ic_avatar_default)
                            .into(ivAvatar);
                    user.setBitmapAvatar(null);
                    Log.d(TAG, "onChanged() returned: " + "ảnh được chọn từ thư mục ảnh");
                } else {
                    Glide.with(requireActivity())
                            .load(user.getStrUriAvatar())
                            .error(R.drawable.ic_avatar_default)
                            .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                            .into(ivAvatar);
                    Log.d(TAG, "onChanged() returned: " + "ảnh được lấy từ storage về");
                }
//                Glide.with(getActivity()).load(user.getBitmapAvatar()).error(R.drawable.ic_avatar_default).into(ivAvatar);
                Log.d(TAG, "onChanged: ");
                Log.d(TAG, "onChanged: " + user.toString());
            }
        });
        Log.d(TAG, "setBitmapAvatarWithViewModel: end");
    }
    private void setUserInfoToView() {
        //solution 1 use FirebaseUser
        User user = profileViewModel.getUser().getValue();
        Log.d(TAG, "setUserInfoToView: setAvatar on setUserInfo method" );
        Log.d(TAG, "setUserInfoToView: " + user.toString());
//        etFullName.setText(user.getName());
//        etEmail.setText(user.getEmail());
        mLayoutName.getEditText().setText(user.getName());
        mLayoutEmail.getEditText().setText(user.getEmail());
        mLayoutAddress.getEditText().setText(user.getAddress());
        mLayoutPhoneNumber.getEditText().setText(user.getPhoneNumber());
        Glide.with(requireActivity())
                .load(user.getStrUriAvatar())
                .error(R.drawable.ic_avatar_default)
                .into(ivAvatar);
        /*try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference pathRef = storage
                    .getReference()
                    .child((user.getUriAvatar().getPath()).substring(1));
            pathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(TAG, "onSuccess: ");
                    Glide.with(requireActivity())
                            .load(user.getStrUriAvatar())
                            .error(R.drawable.ic_avatar_default)
                            .into(ivAvatar);
                    Log.d(TAG, "loadUserInfo: " + user.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: ", e);
                    //TODO thông báo cho người dùng các kiểu
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "setUserInfoToView: ", e);
        } finally {
            Glide.with(requireActivity())
                    .load(user.getStrUriAvatar())
                    .error(R.drawable.ic_avatar_default)
                    .into(ivAvatar);
        }*/
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_profile_fragment_avatar:
                Log.d(TAG, "onClick: click imageview");
                onClickRequestPermission();
                break;
            case R.id.btn_profile_fragment_update:
                Log.d(TAG, "onClick: click btn update");
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
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, MY_REQUEST_CODE);
        }
    }
    private void updateUserInfo() {
        Log.d(TAG, "updateUserInfo: start");

        user.setName(mLayoutName.getEditText().getText().toString());
        user.setAddress(mLayoutAddress.getEditText().getText().toString());
        user.setPhoneNumber(mLayoutPhoneNumber.getEditText().getText().toString());
        Bitmap bitmap = ((BitmapDrawable) ivAvatar.getDrawable()).getBitmap();

        //up ảnh lên storage, lấy Uri storage set vào uriAvatar của user, change user trong ViewModel
        // Get the data from an ImageView as bytes
        StorageReference spaceRef = mStorageReference.child("images/" + user.getId() + "_avatar.jpg");
        ivAvatar.setDrawingCacheEnabled(true);
        ivAvatar.buildDrawingCache();
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
                                    Log.d(TAG, "onComplete: update profile successful");
                                    //update thông tin user treng RealTime database
                                    updateUserOnDatabase();
                                    //show lại thông tin user trong nav bar
//                                    ((MainActivity)requireActivity()).showUserInformation();
                                    //dòng này không nhớ để làm gì
                                    //có thể setUser cho mainActivity lắng nghe
                                    //và tương tác với các Fragment khác

                                    profileViewModel.setUser(user);
                                    return;
                                }
                                Log.d(TAG, "onComplete: update profile failure");
                            }

                            private void updateUserOnDatabase() {
                                //TODO tách DAO riêng dễ quản lý
                                DatabaseReference mDatabase;
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                String key = mDatabase.child("users").push().getKey();
                                Map<String, Object> userValue = user.toMap();
                                Map<String, Object> userUpdateValue = new HashMap<>();
                                userUpdateValue.put("/users/" + user.getId(), userValue);
                                mDatabase.updateChildren(userUpdateValue).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ((MainActivity)requireActivity()).showUserInformation();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: update profile failure");
                            }
                        });

                //sửa thông tin trong nav bar
            }
        });
        Log.d(TAG, "updateUserInfo: end");
        //use ViewModel communicating with MainAcitivy

    }




}