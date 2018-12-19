#include<iostream>
#include<cstdio>
#include<algorithm>
#include<cstring>
using namespace std;
#define INF 0x3f3f3f3f 
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n; 
int f[1<<20][2];
int c[22][22],w[22][22],pre[22],bo[22],sta[22];
void dfs(int x){//预处理砍掉某条边后会消失哪条边 
    bo[x]=1;
    if (x>0)sta[x]|=(1<<(x-1)); 
    rep(i,1,n){
        if(!bo[i]&&w[x][i]!=0){
            dfs(i);
            sta[x]|=sta[i];
            pre[i]=x;
        }
    }
}
int dp(int s,int turn){
	if (!s)return 0;
	int k=(turn+1)/2;
	if (f[s][k]!=INF)return f[s][k];//搜过的状态不再搜 
	int maxx=-INF,minn=INF;
	rep(i,1,n){
		if (s&(1<<(i-1))){
			if (turn!=c[pre[i]][i])
				continue;
			int temp=dp(s&~sta[i],-turn);
			maxx=max(maxx,w[pre[i]][i]+temp);//A砍 
			minn=min(minn,w[pre[i]][i]+temp);//B砍 
		}
	}
	if(turn==1&&maxx==-INF)maxx=dp(s,-turn);//A没得砍 
	if(turn==-1&&minn==INF)minn=dp(s,-turn);//B没得砍 
	if (turn==1)f[s][k]=maxx;
	else f[s][k]=minn;
	return f[s][k];
} 
int main(){
	int x,y,color,weight;
	cin>>n;
	rep(i,1,n){
		cin>>x>>y>>color>>weight;
		c[x][y]=c[y][x]=color;
		w[x][y]=w[y][x]=weight*color;
	}
	pre[0]=-1;
	dfs(0);
	memset(f,INF,sizeof(f));
	cout<<dp((1<<n)-1,1)<<endl;
	return 0;
}
