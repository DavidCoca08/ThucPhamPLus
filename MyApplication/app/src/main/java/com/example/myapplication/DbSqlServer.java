package com.example.myapplication;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class  DbSqlServer {

    Connection connection=null;
    final String TAG = "zzzzzz";

    public Connection openConnect(){
        String ip = "192.168.1.101",
                port = "1433",
                user = "sa",
                pass = "123",
                db = "demo";
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db +";user=" + user +";password=" + pass +";";
            connection = DriverManager.getConnection(connectUrl);
            Log.d(TAG, "openConnect: OK");


        } catch (Exception e) {
            Log.e(TAG, "getCollection: Loi ket noi CSDL" );
            e.printStackTrace();
        }
        return connection;
    }


}
