package com.example.myapplication;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    Connection objConn;
    public DAO(){
        // hàm khởi tạo để mở kết nối
        DbSqlServer db = new DbSqlServer();
        objConn = db.openConnect(); // tạo mới DAO thì mở kết nối CSDL
    }
    public List<TbCategory> getAll(){
        List<TbCategory> listCat = new ArrayList<TbCategory>();

        try {
            if (this.objConn != null) {

                String sqlQuery = "SELECT * FROM test ";

                Statement statement = this.objConn.createStatement(); // khởi tạo cấu trúc truy vấn

                ResultSet resultSet = statement.executeQuery(sqlQuery); // thực thi câu lệnh truy vấn

                while (resultSet.next()) { // đọc dữ liệu gán vào đối tượng và đưa vào list

                    TbCategory objCat = new TbCategory();
                    objCat.setId(resultSet.getInt("id")); // truyền tên cột dữ liệu
                    objCat.setName(resultSet.getString("name")); // tên cột dữ liệu là name

                    listCat.add(objCat);
                }
            } // nếu kết nối khác null thì mới select và thêm dữ liệu vào, nếu không thì trả về ds rỗng



        } catch (Exception e) {
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
        }

        return  listCat;
    }
    public int add(){
        try {
            if (this.objConn != null) {

                String sqlQuery = "insert into test values (6,'hương') ";

                Statement statement = this.objConn.createStatement(); // khởi tạo cấu trúc truy vấn

//                ResultSet resultSet = statement.executeQuery(sqlQuery); // thực thi câu lệnh truy vấn
                if (statement.executeUpdate(sqlQuery) > 0) {
                    return 1;
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
            return -1;
        }

        return  -1;
    }
    public int edit(){
        try {
            if (this.objConn != null) {

                String sqlQuery = "Update test set name = 'huyền' where id = 1 ";

                Statement statement = this.objConn.createStatement(); // khởi tạo cấu trúc truy vấn

//                ResultSet resultSet = statement.executeQuery(sqlQuery); // thực thi câu lệnh truy vấn
                if (statement.executeUpdate(sqlQuery) > 0) {
                    return 1;
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
            return -1;
        }

        return  -1;
    }
    public int delete(){
        try {
            if (this.objConn != null) {

                String sqlQuery = "delete from test where ID = 1  ";

                Statement statement = this.objConn.createStatement(); // khởi tạo cấu trúc truy vấn

//                ResultSet resultSet = statement.executeQuery(sqlQuery); // thực thi câu lệnh truy vấn
                if (statement.executeUpdate(sqlQuery) > 0) {
                    return 1;
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzz", "getAll: Có lỗi truy vấn dữ liệu " );
            e.printStackTrace();
            return -1;
        }

        return  -1;
    }

}
