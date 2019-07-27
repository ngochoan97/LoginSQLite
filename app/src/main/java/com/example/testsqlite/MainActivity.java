package com.example.testsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etName, etPass;
    Button btnLogin;
    SQLHelperNew sqlHelperNew;
    ArrayList<String> getUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       etName = findViewById(R.id.etName);
       etPass=findViewById(R.id.etPass);
       btnLogin=findViewById(R.id.btnLogin);
       sqlHelperNew = new SQLHelperNew(getBaseContext());
       sqlHelperNew.insertValues();


       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (checkUser(etName.getText().toString())){
                    if (checkRegex(etPass.getText().toString())) {
                        if (checkPass(etName.getText().toString(), etPass.getText().toString())) {
                            Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Mật khẩu nhập vào không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Mật Khẩu Phải gồm số , chữ biết hoa, viết thường và kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                    }
               }
               else {
                   Toast.makeText(MainActivity.this, "Tài Khoản chưa tồn tại", Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
    public boolean checkUser(String name) {
        getUser=sqlHelperNew.getAllUser();
        if (getUser.contains(name)) {

            return true;
        }
        return false;
    }

    public  boolean checkPass(String name,String pass){
        if(getUser.contains(name)&& pass.equals(sqlHelperNew.getPassByUser(name))){
            return true;
        }
        return false;
    }
    public boolean checkRegex(String pass){
        String regex="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#$^+=!*()@%&]).{6,20}$";
        if (pass.matches(regex)) {
            return true;
        }else {return false;}
    }
}
