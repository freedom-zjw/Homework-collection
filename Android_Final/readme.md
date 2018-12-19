* 课程表、今日课程、课程列表的点击事件

  Lesson_infomation那个我看了，里面没看到jy有写处理cid，所以我统一留了个接口，在Main_Tabhost里面，把cid传给你

  ```java
  public void Go_To_Lesson_Info(int cid){
  	Toast.makeText(this,""+cid,Toast.LENGTH_SHORT).show();
  }
  ```

* RecyclerView的添加传入参数是现在是你写的类

  ```java
  public void Add_Today_Class(Course course) //添加今日课程
  public void Add_Class_list(Course course) //添加课程列表
  public void Add_Chat_List(Comment comment) //添加闲聊
  ```

  另外新写了几个函数

  ```java
  public void Update_Class_Table() //根据ClassTable这个ArrayList更新课程表View
  public void Filling_ListClassToday() //根据课程表所有课程填充今日课程
  public void Update_ViewTodayClass() //根据ClassToday更新今日课程View
  public void Update_ViewClassList() //根据AllClass更新课程列表View
  ```

  我用函数`Filling_ListClassTable()` 往ClassTable和AllClass这两个ArrayList里面写了点数据用来测试，你后面把这个函数删掉就ok

* 所有课程里面的长按选课/退课时间

  我在`Long_Click_Class_List()` 里面写的长按事件，留了cid给你

  ```
  int cid=Integer.parseInt(class_list.get(position).get("cid"));//这门课程的cid
  ```

  然后判断这门课有没有选的话，我是在从AllClass里拿数据填RecyclerView的时候把有没有选课这个信息一起扔进了RecyclerList，每次选课退课都有对应更改这个信息，我个人是觉得这样实现不是很好，你应该可以用cid查询这门课有没有选

  然后选课退课之后的处理，我做的是：

  1. 选课后（退课后）Map对应的位置设1（0），这个Map就是之前跟你说的判断这个时间段是否已经选课的Map
  2. 对应位置的已选标记（就那个小对勾）显示/消失
  3. 选课/退课后更新课程表View和今日课程View

  理论上来说只要你把数据库里的数据更新一下，然后更新一下ClassTable、TodayClass这些ArrayList这些就好了

  这方面有问题你可以找我后面再改

  示例代码

  ```java
  //选课成功
  ClassList.put(key,1);//map对应key置1
  Change_Mark(position,"visible");//该位置对勾显示
  /*
  这里应该是你去数据库更新数据，然后更新那几个ArrayList
  我下面的更新函数都是根据新的ArrayList来更新View
  */
  Update_Class_Table();//更新课程表View
  Update_ViewTodayClass();//更新今日课程View
  class_list.get(position).put("isChoose",1+"");//RecyclerList中这个课程的是否已选信息设为已选
  ```

* 聊天界面和课程列表的上拉下拉

  ```java
  public void Class_List_Drop_Down() //课程列表下拉刷新
  public void Class_List_Drop_Up() //课程列表上拉加载
  public void Chat_List_Drop_Down() //聊天界面下拉刷新
  public void Chat_List_Drop_Up() //聊天界面的上拉加载
  ```

* 聊天界面举报超过20

  一样写在了`Click_Chat_item()` 里，注释里写了点其他东西

* 发表新吐槽的界面

  在OnClick的点击提交按钮里，就是`v.getId()==R.id.submit` 给Comment类填了数据，然后你在这里把填完的传上去就可以了