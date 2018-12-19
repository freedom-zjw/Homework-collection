#include<iostream>
#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<utility>
#include<vector> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 10010
#define fi first
#define se second
int n,cap,ans;
vector<pair<int,int> >g[N];
void dfs(int x,int dis,int fa){
	if (dis>ans)ans=dis;
	int len=g[x].size();
	rep(i,0,len-1){
		int y=g[x][i].fi;
		int d=g[x][i].se;
		if (y==fa)continue;
		dfs(y,dis+d,x); 
	}
}
int main(){
	int x,d,y;
	while (cin>>n>>cap){
		rep(i,1,n)g[i].clear();
		ans=0;
		rep(i,1,n-1){
			cin>>x>>y>>d;
			g[x].push_back(make_pair(y,d));
			g[y].push_back(make_pair(x,d));
		}
		dfs(cap,0,0);
		cout<<ans<<endl;
	}
	return 0;
}