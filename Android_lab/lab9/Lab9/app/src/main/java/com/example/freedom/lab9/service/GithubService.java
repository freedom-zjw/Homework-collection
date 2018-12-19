package com.example.freedom.lab9.service;

import com.example.freedom.lab9.model.Github;
import com.example.freedom.lab9.model.Repos;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by freedom on 2017/12/19.
 */

/*
所有的网络通信，其核心任务只有一个就是：Client端与Server端进行数据的交互和操作。
所以Retrofit就将底层的代码都封装起来只是暴露出了我们业务中的数据模型和操作方法。
这样理解之后，我们学习Retrofit就简单明了很多了。应该从以下这几点开始学习：

如何传入我们业务需求的URL以及变量参数
如何将我们业务中从服务器获取的Jason数据封装为与Retrofit向对应的类或者接口
如何使用Retrofit进行get,post,delete,put等操作。
还有就是如何在我们项目中配置Retrofit

 */

/*
Retrofit特性

1.将rest API封装为java接口，我们根据业务需求来进行接口的封装，实际开发可能会封装多个不同的java接口以满足业务需求。
(注意：这里会用到Retrofit的注解：比如get,post)
2.使用Retrofit提供的封装方法将我们的生成我们接口的实现类，通过注解Retrofit全部帮我们自动生成好了。
3.调用我们实现类对象的接口方法。

 */

/*
将Rest API转换为java接口
这里的Rest API是指服务器端的API，一般会暴露get，post方法
GitHubService其实是对Rest API的一个映射关系，
在实际开发中，我们可以定义：public interface ClientService，里面包含post ,get 方法。
 */
/*
 Observable 为 RxJava 中的类型。暂时可以理解为这个
接口会返回一个 Github 类型（即 Model 中定义好的类）
需要注意的是访问用户 repositories 返回的是一个 List
后面的getXXX方法表示需要get的url的动态部分
 */
public interface GithubService { //创建api 服务接口 创建接口类，使用注解声明网络请求的方法和相关参数
    //请求路径，请求路径中可以包含参数，并在参数中使用 @PATH 注解来动态改变路径，
    //比如路径 /api/v1/user/{id}，使用注解 @PATH("id") Long id 即可改变路径中 {id} 请求时的值。
    @GET("/users/{user}")
    Observable<Github> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Observable<List<Repos>> getUserRepos(@Path("user") String user);
}
