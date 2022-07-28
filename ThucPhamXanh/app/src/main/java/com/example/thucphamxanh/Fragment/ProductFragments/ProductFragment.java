package com.example.thucphamxanh.Fragment.ProductFragments;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thucphamxanh.Adapter.ProductAdapter;
import com.example.thucphamxanh.Adapter.ProductAdapter_tabLayout;
import com.example.thucphamxanh.Model.Partner;
import com.example.thucphamxanh.Model.Product;
import com.example.thucphamxanh.R;
import com.example.thucphamxanh.databinding.FragmentProductBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;
    private TabLayout tabLayoutProduct;
    private ViewPager2 viewPagerProduct;
    private ProductAdapter_tabLayout adapter_tabLayout;
    private FloatingActionButton fab_addProduct;
    private List<Product> listProduct = new ArrayList<>();
    private TextInputLayout til_nameProduct,til_priceProduct,til_amountProduct;
    private ImageView img_Product,img_addImageCamera,img_addImageDevice;
    private String nameProduct,imgProduct,userPartner;
    private int codeCategory,priceProduct,amountProduct;
    private Button btn_addVegetable,btn_cancleVegetable;
    private Spinner sp_nameCategory;
    private String[] arr = {"Rau củ","Hoa quả","Thịt"};
    private ArrayAdapter<String> adapterSpiner;
    private ProductAdapter adapter;
    private static final int REQUEST_ID_IMAGE_CAPTURE =10;
    private static final int PICK_IMAGE =100;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        tabLayout();
        initUI();
        fab_addProduct.setOnClickListener(view1 -> {
            dialogProduct();
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void tabLayout(){
        tabLayoutProduct = binding.tabProductFragment;
        viewPagerProduct = binding.viewPagerProductFragment;
        adapter_tabLayout = new ProductAdapter_tabLayout(this);
        viewPagerProduct.setAdapter(adapter_tabLayout);
        new TabLayoutMediator(tabLayoutProduct, viewPagerProduct, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: tab.setText("Rau củ");
                        break;
                    case 1: tab.setText("Hoa quả");
                        break;
                    case 2:tab.setText("Thịt");
                        break;
                    case 3:tab.setText("Đồ ăn ");
                        break;
                }
            }
        }).attach();
    }
    public void initUI(){
        getAllProducts();
        Log.d("EEeeeeee", String.valueOf(listProduct.size()));
        fab_addProduct = binding.fabAddProductFragment;
        adapter = new ProductAdapter(listProduct,getContext());

    }

    private void dialogProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm sản phẩm");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initUiDialog(view);
        img_addImageCamera.setOnClickListener(view1 -> {
            requestPermissionCamera();
        });
        img_addImageDevice.setOnClickListener(view1 -> {
            requestPermissionDevice();
        });
        btn_addVegetable.setOnClickListener(view1 -> {
            getTexts();
            validate();
        });
        btn_cancleVegetable.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }
    public void initUiDialog(View view){
        img_Product = view.findViewById(R.id.imgProduct_dialog);
        img_addImageCamera = view.findViewById(R.id.img_addImageCamera_dialog);
        img_addImageDevice = view.findViewById(R.id.img_addImageDevice_dialog);
        til_nameProduct =  view.findViewById(R.id.til_NameProduct_dialog);
        til_priceProduct =  view.findViewById(R.id.til_PriceProduct_dialog);
        til_amountProduct =  view.findViewById(R.id.til_AmountProduct_dialog);
        btn_addVegetable =  view.findViewById(R.id.btn_addVegetable_dialog);
        btn_cancleVegetable =  view.findViewById(R.id.btn_cancleVegetable_dialog);
        sp_nameCategory = view.findViewById(R.id.sp_nameCategory);
        adapterSpiner = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,arr);
        sp_nameCategory.setAdapter(adapterSpiner);
    }

    public void getTexts(){
        nameProduct = til_nameProduct.getEditText().getText().toString();
        Bitmap bitmap = ((BitmapDrawable)img_Product.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imgByte = outputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imgProduct = Base64.getEncoder().encodeToString(imgByte);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("My_User", Context.MODE_PRIVATE);
        userPartner = sharedPreferences.getString("username","");

                String category = sp_nameCategory.getSelectedItem().toString();
                if (category.equals("Rau củ")){
                    codeCategory = 1;
                }else if (category.equals("Hoa quả")){
                    codeCategory = 2;
                }else  if (category.equals("Thịt")){
                    codeCategory = 3;
                }

        priceProduct = Integer.parseInt(til_priceProduct.getEditText().getText().toString());
        amountProduct = Integer.parseInt(til_amountProduct.getEditText().getText().toString());

    }
    public void setDataProduct(){
        Product product = new Product();
        product.setUserPartner(userPartner);
        product.setCodeCategory(codeCategory);
        product.setNameProduct(nameProduct);
        product.setPriceProduct(priceProduct);
        product.setAmountProduct(amountProduct);
        product.setImgProduct(imgProduct);
        addProduct(product);

    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                this.img_Product.setImageBitmap(bp);

                Uri imageUri = data.getData();
                img_Product.setImageURI(imageUri);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "Bạn chưa thêm ảnh", Toast.LENGTH_LONG).show();
            } else if (data!=null){
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_LONG).show();

            }
        }
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK ) {
                Uri imageUri = data.getData();
                this.img_Product.setImageURI(imageUri);
            }
        }

    }

    public  void getAllProducts(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProduct.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    listProduct.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addProduct(Product product){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        if (listProduct.size()==0){
            product.setCodeProduct(1);
            product.setCodeCategory(product.getCodeCategory());
            product.setUserPartner(product.getUserPartner());
            product.setImgProduct(product.getImgProduct());
            product.setNameProduct(product.getNameProduct());
            product.setPriceProduct(product.getPriceProduct());
            product.setAmountProduct(product.getAmountProduct());
            reference.child("1").setValue(product);

        }else {
            int i = listProduct.size()-1;
            int id = listProduct.get(i).getCodeProduct()+1;
            product.setCodeProduct(id);
            product.setCodeCategory(product.getCodeCategory());
            product.setUserPartner(product.getUserPartner());
            product.setImgProduct(product.getImgProduct());
            product.setNameProduct(product.getNameProduct());
            product.setPriceProduct(product.getPriceProduct());
            product.setAmountProduct(product.getAmountProduct());
            reference.child(""+id).setValue(product);
        }
    }
    public void updateProduct(Product product){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        reference.child(""+product.getCodeProduct()).updateChildren(product.toMap());
    }
    public void deleteProduct(Product product){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Product");
        reference.child(""+product.getCodeProduct()).removeValue();
    }
    public boolean isEmptys(String str,TextInputLayout til){
        if (str.isEmpty()){
            til.setError("Không được để trống");
            return false;
        }else til.setError("");
        return true;
    }
    public void validate(){
        if (isEmptys(nameProduct,til_nameProduct) && isEmptys(String.valueOf(priceProduct),til_priceProduct)
                && isEmptys(String.valueOf(amountProduct),til_amountProduct) && !imgProduct.isEmpty()){
            setDataProduct();
            removeAll();
        }
    }
    public void removeAll(){
        til_amountProduct.getEditText().setText("");
        til_nameProduct.getEditText().setText("");
        til_priceProduct.getEditText().setText("");
        img_Product.setImageResource(R.drawable.ic_menu_camera1);
    }
    public void requestPermissionCamera(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                captureImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                requestPermissionCamera();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Nếu bạn không cấp quyền,bạn sẽ không thể tải ảnh lên\n\nVui lòng vào [Cài đặt] > [Quyền] và cấp quyền để sử dụng")
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }
    public void requestPermissionDevice(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                openGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                requestPermissionDevice();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Nếu bạn không cấp quyền,bạn sẽ không thể tải ảnh lên\n\nVui lòng vào [Cài đặt] > [Quyền] và cấp quyền để sử dụng" )
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

}