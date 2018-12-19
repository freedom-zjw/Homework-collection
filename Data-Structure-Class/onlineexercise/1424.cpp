#include<iostream>
#include<cstdio>
#include<vector>
#include<queue>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 10010
int n,m,ans,flag;
vector<int>g[N];
int deg[N],v[N],reward[N];
void work(int n){
	int cnt=0;
	queue<int> Q;
	rep(i,1,n)
		if (!deg[i]){
			Q.push(i);
			reward[i]=100;
		}
	while (!Q.empty()){
		int x=Q.front();
		Q.pop();
		cnt++;
		ans+=reward[x];
		int len=g[x].size();
		rep(i,0,len-1){
			int y=g[x][i];
			if (--deg[y]==0)Q.push(y);
			reward[y]=reward[y]>(reward[x]+1)?reward[y]:(reward[x]+1);
		} 
	}
	if (cnt==n)flag=1;
	else flag=0;
}
int main(){
	int x,y;
	scanf("%d%d",&n,&m);
	flag=1;
	rep(i,1,m){
		scanf("%d%d",&x,&y);
		g[y].push_back(x);
		deg[x]++;
	}
	work(n);
	if (!flag)printf("Poor Xed\n");
	else printf("%d\n",ans);
	return 0;
} 