#include<iostream>
#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<algorithm>
#include<cmath>
#include<ctime>
#include<string>
#include<set>
#include<queue>
#include<map>
#include<vector>
#include<utility>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);++i)
#define dto(i,u,v) for (int i=(u);i<=(v);++i)
#define w1 100
#define w2 2
#define w3 1
#ifdef   GLOBALS  
#define  EXTERN 
#else  
#define  EXTERN  extern  
#endif  
struct vessel{
	int a,t,b;
	/*
	定义表示船的结构体
	a: arrive time
	t: service time
	b: it need to occupiy b berths
	*/
};
struct sol{
	int position_to_start_docking[30];//
	int time_to_start_service[30];
	int total_waiting_time;
	int last_departure_time;
	int fx;
	/*定义表示停船方案的结构体
	以下涉及到的船的顺序是按读数据时的顺序 
	
	position_to_start_docking[i] 第i艘船从哪个berths开始停
    time_to_start_service[i]     第i艘船从哪个时间开始停 
    total_waiting_time           总等待时间 
    ast_departure_time           最后离开的船的离开时间 
    fx                           该方案的评价值，见题目 
	*/ 
};
struct permutation{
	int array[30];
	int sta[13][31];
	sol ans;
	/*
	array: 排列，指定停靠顺序，array[i]表示第i个停的船是读数据时的第array[i]艘船 
	sta: 港口状态，第一维是位置，第二维是时间，值为0表示能停，为1表示不能停 
	ans：存储该排列的贪心得出的结果 
	*/ 
};
EXTERN int T,n,L;
EXTERN vessel boat[30];
EXTERN sol ANS;
EXTERN permutation p[7];                  
EXTERN char datanum[50];
EXTERN bool OpenDataFile();             //打开数据读取文件和结果写入文件
EXTERN void Print_Ans();                //输出答案 
EXTERN void Evaluate(permutation &n1); //计算fx,最迟离开时间，总等待时间 
EXTERN void update(sol &n1, sol n2);   //更新最优解，存在n1中 


////////////////////贪心部分
EXTERN bool check(permutation &n1, int stb, int edb, int stt, int edt);//检查能否停 
EXTERN void find_location(permutation &n1, int idx);//给第排列中的第idx位指示的船找位置停
EXTERN void Greedy(permutation &n1); //对permutaion序列指定的停靠顺序用贪心寻找停靠位置
							 //结果在存在n1中 
//////////////////////////// 

///////////////////////////爆搜部分
EXTERN int v[30];
EXTERN void dfs(int x);//求全排列 
EXTERN void violence_search();// 爆搜方法入口函数 
////////////////////////////////// 

////////////////////////模拟退火部分
EXTERN double random();
EXTERN void Simulate_Anneal();
/////////////////////////////////// 

