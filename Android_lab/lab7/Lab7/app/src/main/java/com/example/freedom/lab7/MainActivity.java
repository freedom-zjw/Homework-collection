package com.example.freedom.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText text1 = (EditText) findViewById(R.id.text1);
        final EditText text2 = (EditText) findViewById(R.id.text2);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        final SharedPreferences sp = getSharedPreferences("MyText",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        boolean ischecked = sp.getBoolean("checked",false);


        button2.setOnClickListener(new View.OnClickListener(){//清空按钮
            @Override
            public void onClick(View v) {
                text1.setText("");
                text2.setText("");
            }
        });

        if(!ischecked) {
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                 public void onClick(View v) {
                    if (TextUtils.isEmpty(text1.getText().toString()) ||TextUtils.isEmpty(text2.getText().toString()) ) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (!text1.getText().toString().equals(text2.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Password Missmatch", Toast.LENGTH_SHORT).show();
                    } else if (text1.getText().toString().equals(text2.getText().toString())){
                        editor.putString("password", text1.getText().toString());
                        editor.putBoolean("checked", true);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this,FileEdit.class);
                        startActivity(intent);

                    }
                }
            });
        }
        if(ischecked){
            text1.setVisibility(View.INVISIBLE);
            text2.setHint("Password");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(text2.getText().toString() .equals(sp.getString("password",text1.getText().toString()))){
                        Intent intent = new Intent(MainActivity.this,FileEdit.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Password Missmatch",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
