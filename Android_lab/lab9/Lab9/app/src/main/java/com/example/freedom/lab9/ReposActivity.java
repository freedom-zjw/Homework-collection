package com.example.freedom.lab9;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freedom.lab9.Adapter.CardAdapter;
import com.example.freedom.lab9.Adapter.ViewHolder;
import com.example.freedom.lab9.factory.ServiceFactory;
import com.example.freedom.lab9.model.Repos;
import com.example.freedom.lab9.service.GithubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by freedom on 2017/12/19.
 */

public class ReposActivity extends Activity {
    String login = "";
    private GithubService githubService;
    ProgressBar reposPrograss;
    RecyclerView recyclerView;
    List<Map<String, Object>> reposList = new ArrayList<>();
    CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        recyclerView = findViewById(R.id.reposList);
        reposPrograss = findViewById(R.id.reposProgress);

        //第一步先创建一个baseurl为"https://api.github.com/"的对象
        //第二步使用retrofit初始化我们创建的接口GithubService，获取到GithubService实例之后就可以使用该实例发起请求
        Retrofit GithubRetrofit = ServiceFactory.createRetrofit("https://api.github.com");
        githubService = GithubRetrofit.create(GithubService.class);

        //获取要访问repository的user名
        Bundle extras = getIntent().getExtras();
        login = extras.getString("login");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(this, R.layout.repos_item_layout, reposList) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                //重写Adapter中的convert方法进行数据绑定，这里就是将获取到的某个用户的
                //name、id、blog数据分别绑定
                TextView name = holder.getView(R.id.repositem_name);
                name.setText(s.get("name").toString());
                TextView language = holder.getView(R.id.repositem_lang);
                language.setText(s.get("language").toString());
                TextView description = holder.getView(R.id.repositem_desc);
                description.setText(s.get("description").toString());
            }
        };

        recyclerView.setAdapter(cardAdapter);
        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) { //点击跳转到项目具体网页
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(cardAdapter.getData(position, "html_url"));
                intent.setData(content_url);
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(int position) {
                return false;
            }
        });

        //调用retrofit创建好的访问实例去获取用户的repository信息
        githubService.getUserRepos(login)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repos>>() {
                    @Override
                    public void onCompleted() {
                        reposPrograss.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReposActivity.this, e.getMessage()+"确认你搜索的用户存在", Toast.LENGTH_LONG).show();
                        reposPrograss.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<Repos> repos) {
                        //将数据添加进adapter以在recyclerview中显示
                        for(int i = 0; i < repos.size(); i++) {
                            cardAdapter.addData(repos.get(i));
                        }
                    }
                });
    }//end onCreate
}
