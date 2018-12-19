#include<iostream>
#include<cstring>
#include<cstdio>
#include<cmath>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define INF 100000000.0
struct node{
	double x,y;
}a[110];
int n;
double dp[110][110];
bool cmp(const node &n1,const node &n2){
	return n1.x<n2.x;
}
double dist(node &n1,node &n2){
	double x=n1.x-n2.x;
	double y=n1.y-n2.y;
	return sqrt(x*x+y*y);
}
int main(){
	while(cin>>n){
		rep(i,1,n)
			cin>>a[i].x>>a[i].y;
		sort(a+1,a+1+n,cmp);
		rep(i,0,n)
			rep(j,0,n)
				if (!i || !j)dp[i][j]=0;
				else dp[i][j]=INF;
		dp[1][1]=0;//起点到自己是0
		rep(i,2,n)dp[1][i]=dp[i][1]=dp[i-1][1]+dist(a[i],a[i-1]);
		//上面这步是设置边界，即一个人走完全程 
		rep(i,1,n)//枚举第一个人走的点
			rep(j,1,i){//枚举第二个人走的点
				if(i>j+1)dp[i][j]=dp[j][i]=dp[i-1][j]+dist(a[i],a[i-1]);
				else if (i==j+1){
					rep(k,1,j-1)//枚举第一个人是从k点走到i点的 
						dp[i][j]=dp[j][i]=min(dp[i][j],dp[k][j]+dist(a[i],a[k]));
				} 
				else{//i==j
					rep(k,1,j-1)
						dp[i][j]=dp[j][i]=min(dp[i][j],
						dp[i-1][k]+dist(a[i],a[i-1])+dist(a[k],a[j]));	
				}
			} 
		printf("%.2lf\n",dp[n][n]);	
	} 
	return 0;
}