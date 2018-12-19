package com.example.freedom.lab9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freedom.lab9.Adapter.CardAdapter;
import com.example.freedom.lab9.Adapter.ViewHolder;
import com.example.freedom.lab9.factory.ServiceFactory;
import com.example.freedom.lab9.model.Github;
import com.example.freedom.lab9.service.GithubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText searchBar;
    private Button clearButton;
    private Button fetchButton;
    private RecyclerView recyclerVier;
    private ProgressBar waitPrograss;
    private List<Map<String, Object>> cardList = new ArrayList<>();//集合
    private CardAdapter cardAdapter;
    private GithubService githubservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = (EditText) findViewById(R.id.searchBar);
        clearButton = (Button)findViewById(R.id.clearButton);
        fetchButton = (Button)findViewById(R.id.fetchButton);
        recyclerVier = (RecyclerView) findViewById(R.id.recycler_view);
        waitPrograss = (ProgressBar) findViewById(R.id.mian_prograss);
        clearButton.setBackgroundColor(Color.parseColor("#218868"));
        fetchButton.setBackgroundColor(Color.parseColor("#218868"));


        //第一步先创建一个baseurl为"https://api.github.com"的对象
        //第二步使用retrofit初始化我们创建的接口GithubService，获取到GithubService实例之后就可以使用该实例发起请求
        Retrofit GithubRetrofit = ServiceFactory.createRetrofit("https://api.github.com");
        githubservice = GithubRetrofit.create(GithubService.class);


        recyclerVier.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(this, R.layout.main_item_layout, cardList) {
            //重写Adapter中的convert方法进行数据绑定，这里就是将获取到的某个用户的
            //login、id、blog数据分别绑定
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView login = holder.getView(R.id.mainitem_login);
                login.setText(s.get("login").toString());
                TextView id = holder.getView(R.id.mainitem_id);
                id.setText(s.get("id").toString());
                TextView blog = holder.getView(R.id.mainitem_blog);
                blog.setText(s.get("blog").toString());
            }
        };

        recyclerVier.setAdapter(cardAdapter); //使用setAdapter将适配器添加给RecyclerView
        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) { //短按跳转到user拥有的详细项目页面
                Intent intent = new Intent(MainActivity.this, ReposActivity.class);
                intent.putExtra("login", cardAdapter.getData(position, "login"));
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(int position) { //长按删除
                cardAdapter.removeData(position);
                Toast.makeText(MainActivity.this, "已删除改搜索记录", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override     //按clear按钮清空信息页
            public void onClick(View v) {
                cardAdapter.clearData();
            }
        });

        //点击fetch按钮，根据searchbar输入的内容搜索对应user的信息
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = searchBar.getText().toString();
                waitPrograss.setVisibility(View.VISIBLE); //将Prograss设为可见
                githubservice.getUser(User)  //用getUser method 访问对应url
                        .subscribeOn(Schedulers.newThread()) //新建线程进行网络访问
                        .observeOn(AndroidSchedulers.mainThread()) //在主线程处理请求结果
                        .subscribe(new Subscriber<Github>() {
                            @Override
                            public void onCompleted() {
                                //请求结束时调用的回调函数，这里是完成了请求就让等待的Prograss不可见
                                waitPrograss.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //有错位的时候抛出提示信息
                                Toast.makeText(MainActivity.this, e.getMessage()+"确认你搜索的用户存在", Toast.LENGTH_LONG).show();
                                waitPrograss.setVisibility(View.GONE);
                            }

                            @Override
                            public void onNext(Github github) {
                                //onNext 函数是收到一次数据时调用的函数,收到的是Github类型的函数
                                //处理操作是将新数据加入到adapter中
                                cardAdapter.addData(github);
                            }
                        });
            }
        });
    }//end onCreate
}
