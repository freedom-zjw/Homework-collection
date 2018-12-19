#include<iostream>
#include<cstdio>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
const int dx[]={-1,-1,-1,0,0,1,1,1};
const int dy[]={-1,0,1,-1,1,-1,0,1};
int ans,n,m,sta;
int play(int w,int now,int ty){
	int cnt=0,xx=w/m,yy=w%m;
	rep(i,0,7){
		int x=xx+dx[i],y=yy+dy[i];
		if (x<0)x=n-1;
		else if (x==n)x=0;
		if (y<0)y=m-1;
		else if (y==m)y=0;
		if ( ( 1<<(x*m+y) ) & now )cnt++;
	}
	if (!ty)return !(cnt==2||cnt==3);
	else return cnt==3;
}
int check(int now){
	int next=now; 
	rep(i,0,n*m-1){
		if ( (1<<i) & now ){
			if (play(i,now,0))next^=(1<<i); //有0，1，4，5，6，7，8个邻居则死亡 
			else next=next;//否则继续生存 
		}
		else if (play(i,now,1))next^=(1<<i);//新生命诞生 
	}
	return next==sta;
}
int main(){
	int k,x,y,cases=0;
	while(scanf("%d%d",&n,&m)!=EOF){
		if (!n && !m)break;
		sta=ans=0;
		scanf("%d",&k);
		rep(tt,1,k){
			scanf("%d%d",&x,&y);
			sta |= ( 1<<(x*m+y) );
		}
		int tot_sta = ( 1<<(n*m) ) -1;
		rep(i,0,tot_sta)
			if (check(i))ans++;
		if (!ans)printf("Case %d: Garden of Eden.\n",++cases);
		else printf("Case %d: %d possible ancestors.\n",++cases,ans);
	} 
	return 0;
} 