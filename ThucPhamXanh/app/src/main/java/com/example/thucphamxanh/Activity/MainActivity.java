package com.example.thucphamxanh.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.thucphamxanh.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.thucphamxanh.Fragment.Profile.ProfileViewModel;
import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    public static final String TAG = "MainActivity";
    public static final int MY_REQUEST_CODE = 10;


    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvUserEmail;

    private FirebaseUser userAuth;
    private User user = new User();
    private Bitmap chooseAvatarBitmap;
    private ProfileViewModel profileViewModel;



    private final Observer<User> userObserver = new Observer<User>() {
        @Override
        public void onChanged(User user1) {
            Log.d(TAG, "onChanged: change user information");
            user.setBitmapAvatar(user1.getBitmapAvatar());
            user.setName(user1.getName());
            user.setEmail(user1.getEmail());
            changeUserInfo();
        }

        private void changeUserInfo() {
            //Glide.with(MainActivity.this).load(userAuth.getPhotoUrl()).error(R.drawable.ic_avatar_default).into(ivAvatar);
            if (user.getName() != null)
                if (!user.getName().isEmpty())
                    Log.d(TAG, "changeUserInfo: visibility");
                    tvUserName.setVisibility(View.VISIBLE);
                     tvUserName.setText(user.getName());
        }
    };
    //TODO: thử chuyển method sang ProfileFragment
    private final ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent intent = result.getData();
                                if (intent != null && intent.getData() != null) {
                                    Uri uriImage = intent.getData();
                                    Bitmap selectedImageBitmap = null;
                                    try {
                                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
                                        Log.d(TAG, "onActivityResult: " + selectedImageBitmap.toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d(TAG, "onActivityResult: ");
                                    User userTemp = new User();
                                    userTemp.setBitmapAvatar(selectedImageBitmap);
                                    profileViewModel.setUser(userTemp);
                                    chooseAvatarBitmap = selectedImageBitmap;
                                }

                            }
                        }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
//        DrawerLayout drawer = binding.drawerLayout;
//        NavigationView navigationView = binding.navView;
        //khởi tạo các view
        initUI();
        //get thông tin userAuth từ db và gán vào user để giao tiếp giữa các fragment
        initViewModel();
        showUserInformation();
        //set thông tin user vào viewmodel để giao tiếp
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_my_profile, R.id.nav_change_password, R.id.nav_sign_out)
                .setOpenableLayout(mDrawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, navController);

        //implement logout navigation
        mNavigationView.getMenu().findItem(R.id.nav_sign_out).setOnMenuItemClickListener(this::onMenuItemClick);

    }

    private void initViewModel() {
        this.profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        //profileViewModel.setUser(user);
        //Log.d(TAG, "initViewModel: " + user.toString());
        //this.profileViewModel.getUser().observe(this, userObserver);

    }

    private void initUI() {
        mDrawerLayout = binding.drawerLayout;
        mNavigationView = binding.navView;
        ivAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.iv_MainActivity_avatar);
        tvUserName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_MainActivity_username);
        tvUserEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_MainActivity_userEmail);
    }
    public void showUserInformation() {
        if (userAuth == null) {
            Log.d(TAG, "showUserInformation: userAuth null");
            return;
        }
        String name = userAuth.getDisplayName();
        String email = userAuth.getEmail();
        Uri photoUrl = userAuth.getPhotoUrl();

        Log.d(TAG, "showUserInformation: info: " + name + " - " + email + " - " + photoUrl);
        if (name == null) {
            tvUserName.setVisibility(View.GONE);
        } else {
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText(name);
        }
        tvUserEmail.setText(email);
        //syncView();
        this.user.setName(name);
        this.user.setEmail(email);
        this.user.setBitmapAvatar(((BitmapDrawable)ivAvatar.getDrawable()).getBitmap());
        Log.d(TAG, "showUserInformation: " + user.toString());
        profileViewModel.setUser(this.user);
        Log.d(TAG, "showUserInformation: end method");
        /*this.user.setName(name);
        this.user.setEmail(email);
        try {
            Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(ivAvatar);
        } catch (Exception e) {
            Log.e(TAG, "showUserInformation: ", e);
        }


        ivAvatar.buildDrawingCache();
        this.user.setBitmapAvatar(ivAvatar.getDrawingCache());
        //this.user.setUriAvatar(photoUrl.toString());
        Log.d(TAG, "showUserInformation: " + user.toString());
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //logout application
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.nav_sign_out) {
            logout();
            return true;
        }
        return false;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Please enable read external permission !");
                Toast.makeText(this, "Please enable read external permission !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
        Log.d(TAG, "openGallery: openGallery method");

    }
    public void syncView() {
        StorageReference storageReference = FirebaseStorage.getInstance().
                getReference().child((userAuth.getPhotoUrl().toString()).substring(1));
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MainActivity.this).load(uri).error(R.drawable.ic_avatar_default).into(ivAvatar);
            }
        });
        Log.d(TAG, "syncView: ");
    }
}