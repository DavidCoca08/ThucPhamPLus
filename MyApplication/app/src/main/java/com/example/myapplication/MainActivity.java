package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbSqlServer dbSqlServer = new DbSqlServer();
        Connection conn = dbSqlServer.openConnect();
        DAO catDao = new DAO();
        List<TbCategory> listCat = catDao.getAll(); // lấy danh sách cho vào biến
        if (catDao.add()>0){
            Log.d("zzzzzzzzzz", "add thành công");
        }
        if (catDao.edit()>0){
            Log.d("zzzzzzzzzz", "sửa thành công");
        }
        if (catDao.delete()>0){
            Log.d("zzzzzzzzzz", "xóa thành công");
        }

        // duyệt mảng in ra danh sách
        for(int i = 0; i<listCat.size(); i++){
            TbCategory objCat = listCat.get(i);

            Log.d("zzzzz", "onCreate: phần tử thứ " + i + ":  id = " + objCat.getId() + ", name = " + objCat.getName());

        }

    }

}