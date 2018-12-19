package com.example.freedom.lab7;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by freedom on 2017/12/9.
 */
public class FileEdit  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);

        final EditText filename = (EditText) findViewById(R.id.filename);
        final EditText content = (EditText) findViewById(R.id.content);
        Button save = (Button) findViewById(R.id.save);
        Button load = (Button) findViewById(R.id.load);
        Button clear = (Button) findViewById(R.id.clear);
        Button delete = (Button) findViewById(R.id.delete);
        TextView Title = (TextView) findViewById(R.id.textView);

        Title.setText("File Editor");

        load.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FileInputStream fileInputStream;
                try {
                    fileInputStream = openFileInput(filename.getText().toString());
                    byte[] initial = new byte[fileInputStream.available()];
                    fileInputStream.read(initial);
                    String str = new String(initial, "UTF-8");
                    content.setText(str);
                    fileInputStream.close();
                    Toast.makeText(FileEdit.this, "Load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to read file.");
                    Toast.makeText(FileEdit.this, "Fail to load file", Toast.LENGTH_SHORT).show();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FileOutputStream outputStream;
                try {
                    //打开文件
                    outputStream = openFileOutput(filename.getText().toString(), Context.MODE_PRIVATE);
                    outputStream.write(content.getText().toString().getBytes("UTF-8"));//写入文件
                    outputStream.close();
                    Toast.makeText(FileEdit.this, "Save successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                content.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (FileEdit.this.getApplicationContext().deleteFile(filename.getText().toString())) {
                    Toast.makeText(FileEdit.this, "Delete Successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(FileEdit.this, "Fail to Delete File", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}