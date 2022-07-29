package com.example.thucphamxanh.Activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.thucphamxanh.Fragment.Profile.ProfileViewModel;
import com.example.thucphamxanh.Model.Bill;
import com.example.thucphamxanh.Model.Cart;
import com.example.thucphamxanh.Model.User;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    public static final String TAG = "MainActivity";
    public static final int MY_REQUEST_CODE = 10;

    //Object config layout
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private AppBarConfiguration mAppBarConfiguration;

    //binding của main
    private ActivityMainBinding binding;

    //các view show thông tin user trên nav header
    private ImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvUserEmail;

    //các reference để gọi API tới firebase
    private DatabaseReference mDatabase;
    private FirebaseUser userAuth;
    //object chứa thông tin user sau khi đăng nhập
    //dùng để truyền vào các fragment.....
    private User user;

    //không dùng nữa
    private Bitmap chooseAvatarBitmap;


    //ViewModel để giao tiếp dữ liệu với ProfileFragment
    private ProfileViewModel profileViewModel;
    private List<Bill> listBill = new ArrayList<>();
//    private int cartQuantity = 0;


    public void loadUserInfoById(String phoneNumber){
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.child("User").child(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            MainActivity.this.user = snapshot.getValue(User.class);
                            Log.d(TAG, "onDataChange: " + user);
                            profileViewModel.setUser(user);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                    }
                });
    }
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
                                    user.setBitmapAvatar(selectedImageBitmap);
                                    profileViewModel.setUser(user);
                                    //thay đổi viewmodel
                                    chooseAvatarBitmap = selectedImageBitmap;
                                }
                            }
                        }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //khởi tạo user được xác thực bằng FirebaseAuthentication
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        //khởi tạo các tham chiếu API ???
        //initReferent();
        //khởi tạo các view
        initUI();
        checkUser();
        SharedPreferences preferences1 = getSharedPreferences("Number",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences1.edit();
        String number = "0";
        editor.putString("number",""+number);
        editor.apply();
        getBill();
        //get thông tin userAuth từ db và gán vào user để giao tiếp giữa các fragment
        initViewModel();
        setUserViewModelObserver();
        initUI();
        checkUser();
        /*
         * khởi tạo observer cho user khi thay đổi thông tin user bên ProfileFragment
         * */
        //setUserViewModelObserver();
        //show thông tin user lên nav header khi đăng nhập
        showUserInformation();
        //set thông tin user vào viewmodel để giao tiếp
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_Product,
                R.id.nav_Bill,
                R.id.nav_Partner,
                R.id.nav_my_profile,
                R.id.nav_change_password,
                R.id.nav_sign_out,
                R.id.nav_Food)
                .setOpenableLayout(mDrawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, navController);

        //set event khi click vào logout navigation
        mNavigationView.getMenu().findItem(R.id.nav_sign_out).setOnMenuItemClickListener(this::onMenuItemClick);

    }
    public void checkUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("My_User",MODE_PRIVATE);
        String userPhoneNumber = sharedPreferences.getString("username","");

        if (userPhoneNumber.equals("admin")){
            mNavigationView.getMenu().findItem(R.id.nav_Food).setVisible(false);
        }else if (userPhoneNumber.length()==10){
            mNavigationView.getMenu().findItem(R.id.nav_Product).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.nav_Partner).setVisible(false);
        }else {
//            binding.appBarMain.toolbar.setVisibility(View.GONE);
            binding.navView.setVisibility(View.GONE);
        }
    }
    @Deprecated
    private void initReferent() {
        user = new User();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Deprecated
    private void setUserViewModelObserver() {
        final Observer<User> userObserver = new Observer<User>() {
            @Override
            public void onChanged(User user1) {
                Log.d(TAG, "onChanged: change user information");
                tvUserEmail.setText(user1.getPhoneNumber());
                tvUserName.setText(user1.getName());
                Glide.with(MainActivity.this)
                        .load(user1.getStrUriAvatar())
                        .error(R.drawable.ic_avatar_default)
                        .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                        .into(ivAvatar);
                Log.d(TAG, "onChanged: " + user1.toString());
            }

        };
        profileViewModel.getUser().observe(this, userObserver);
    }

    private void initViewModel() {
        this.profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

    }

    private void initUI() {
        mDrawerLayout = binding.drawerLayout;
        mNavigationView = binding.navView;
        ivAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.iv_MainActivity_avatar);
        tvUserName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_MainActivity_username);
        tvUserEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_MainActivity_userEmail);
    }
    public void showUserInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences("My_User",MODE_PRIVATE);
        String userPhoneNumber = sharedPreferences.getString("username","");
        loadUserInfoById(userPhoneNumber);

        //loadUserInfo();
        /*tvUserEmail.setText(user.getEmail());
        tvUserName.setText(user.getName());
        tvUserName.setVisibility(View.VISIBLE);*/
        /*Glide.with(MainActivity.this)
                .load(user.getStrUriAvatar())
                .placeholder(R.drawable.ic_avatar_default)
                .error(R.drawable.ic_avatar_default)
                .into(ivAvatar);
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference pathRef = storage
                    .getReference()
                    .child((user.getUriAvatar().getPath()).substring(1));
            pathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    user.setStrUriAvatar(uri.toString());
                    Log.d(TAG, "onSuccess: ");
                    Log.d(TAG, "loadUserInfo: " + user.toString());
                    Glide.with(MainActivity.this)
                            .load(user.getStrUriAvatar())
                            .error(R.drawable.ic_avatar_default)
                            .into(ivAvatar);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: ", e);

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "showUserInformation: ",e );
        }
*/
    }
    @Deprecated
    private void loadUserInfo() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("users")
                .child(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: task " + String.valueOf(task.getResult()));
                            DataSnapshot dataSnapshot = task.getResult();
                            user = dataSnapshot.getValue(User.class);
                            tvUserEmail.setText(user.getEmail());
                            tvUserName.setText(user.getName());
                            tvUserName.setVisibility(View.VISIBLE);
                            Glide.with(MainActivity.this)
                                    .load(user.getStrUriAvatar())
                                    .error(R.drawable.ic_avatar_default)
                                    .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                                    .into(ivAvatar);
                            profileViewModel.setUser(user);
                            Log.i(TAG, "onComplete: info user load from storage: " + user);
                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                }
        });
        /*user.setUriAvatar(firebaseUser.getPhotoUrl());
        user.setName(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());
        user.setId(firebaseUser.getUid());*/
        Log.d(TAG, "loadUserInfo: " + user.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_activity, menu);
        // Hùng làm phần này
        final MenuItem menuItem = menu.findItem(R.id.btn_Actionbar_cart);
        View actionView = menuItem.getActionView();

        TextView cartBadgeTextView = actionView.findViewById(R.id.tv_CartActionItem_cart_badge);
        cartBadgeTextView.setVisibility(View.GONE);
        SharedPreferences preferences = getSharedPreferences("My_User",MODE_PRIVATE);
        String user = preferences.getString("username","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Cart");
        List<Cart> list1 = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    if (cart.getUserClient().equals(user)) {
                        list1.add(cart);

                    }
                }
                cartBadgeTextView.setText(String.valueOf(list1.size()));
                cartBadgeTextView.setVisibility(list1.size() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
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
    //mở thư mục ảnh để chọn và set lên imageview trong ProfileFragment
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
        Log.d(TAG, "openGallery: openGallery method");
    }
    public void getBill(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Bill");
        SharedPreferences preferences = getSharedPreferences("My_User", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBill.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Bill bill = snap.getValue(Bill.class);
                    if (user.equals(bill.getIdPartner()) && bill.getStatus().equals("No")) {
                        listBill.add(bill);
                    }
                }
                SharedPreferences preferences1 = getSharedPreferences("Number",MODE_PRIVATE);

                int number = Integer.parseInt(preferences1.getString("number",""));
                if (listBill.size()>number){
                    notification();
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("number", String.valueOf(listBill.size()));
                    editor.apply();
                }else {
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("number", String.valueOf(listBill.size()));
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void notification(){
        String CHANNEL_ID="1234";

        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getPackageName() + "/" + R.raw.sound);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //For API 26+ you need to put some additional code like below:
        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "Thông báo", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.GRAY);
            mChannel.enableLights(true);
            mChannel.setDescription("Chuông thông báo");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(soundUri, audioAttributes);
            mChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel( mChannel );
            }
        }

        //General code:
        NotificationCompat.Builder status = new NotificationCompat.Builder(this,CHANNEL_ID);
        status.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //.setOnlyAlertOnce(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Bạn có đơn hàng mới")
                .setDefaults(Notification.DEFAULT_LIGHTS )
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +getPackageName()+"/"+R.raw.sound))
                .build();


        mNotificationManager.notify((int)System.currentTimeMillis(), status.build());

    }



}