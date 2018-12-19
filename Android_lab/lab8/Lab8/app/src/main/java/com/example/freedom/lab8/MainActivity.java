package com.example.freedom.lab8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final myDB db = new myDB(this);
    List<String> name = new LinkedList<>();
    List<String> birth = new LinkedList<>();
    List<String> gift = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView list = (ListView) findViewById(R.id.list);
        Cursor cursor = db.getAll();
        if (cursor != null) { //查询数据并通过SimpleCursorAdapter绑定到ListView上
            ListAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.item,
                    cursor,
                    new String[]{"name", "birth", "gift"},
                    new int[]{R.id.name, R.id.birthday, R.id.gift},
                    0
            );
            list.setAdapter(adapter);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                name.add(cursor.getString(1));
                birth.add(cursor.getString(2));
                gift.add(cursor.getString(3));
                cursor.moveToNext();
            }
        }


        final Button add_item = (Button) findViewById(R.id.add);
        add_item.setOnClickListener(new View.OnClickListener() {//点击增加条目按钮则切换activity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Additem.class);
                startActivity(intent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击条目显示详细信息
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //通过LayoutInflater 加载其他xml布局
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View myview = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(myview);
                builder.setTitle("ヽ(ﾟ￣∀￣)ﾉ～★恭喜发财★～べ");

                final TextView diaName = (TextView) myview.findViewById(R.id.D_name);
                final EditText diaBirth = (EditText) myview.findViewById(R.id.D_birth);
                final EditText diaGift = (EditText) myview.findViewById(R.id.D_gift);
                final TextView diaPhone = (TextView) myview.findViewById(R.id.phone);
                //设置好显示的信息
                diaName.setText(name.get(i));
                diaBirth.setText(birth.get(i));
                diaGift.setText(gift.get(i));
                //通过getPhoneNumber方法获得通讯录中的电话号码
                String phone = getPhoneNumber(name.get(i));
                if (phone.equals("")) diaPhone.setText("电话：无");
                else diaPhone.setText("电话：" + phone);

                builder.setNegativeButton("放弃修改",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setPositiveButton("保存修改",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.update(diaName.getText().toString(), diaBirth.getText().toString(), diaGift.getText().toString());
                                onResume();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog2.setTitle("是否删除？").setMessage("").setNegativeButton("否",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete(name.get(position));
                                onResume();
                            }
                        }).create();
                dialog2.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ListView list = (ListView) findViewById(R.id.list);
        name.clear();
        birth.clear();
        gift.clear();
        Cursor cursor = db.getAll();
        if (cursor != null) {
            ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, cursor,
                    new String[]{"name", "birth", "gift"},
                    new int[]{R.id.name, R.id.birthday, R.id.gift}, 0);
            list.setAdapter(adapter);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                name.add(cursor.getString(1));
                birth.add(cursor.getString(2));
                gift.add(cursor.getString(3));
                cursor.moveToNext();
            }
        }
    }

    public String getPhoneNumber(String Name) {
        String number = new String();
        //读取联系人列表
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //找到联系人中名字与生日信息中名字一样的联系人
            if (name.equals(Name)) {
                //判断该联系人信息中是否有号码
                int isHas = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (isHas > 0) { //有号码则取出该号码
                    Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (c.moveToNext()) {
                        number += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + " ";
                    }
                    c.close();
                }
            }
        }
        return number;
    }
}
