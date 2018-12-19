#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int a[110][110],v[110];
int n,m;
void dfs(int x){
	v[x]=1;
	rep(i,0,n-1)
		if (a[x][i] && !v[i])dfs(i);
}
int main(){
	while (cin>>n>>m){
		if (!n)break;
		memset(a,0,sizeof(a));
		memset(v,0,sizeof(v));
		rep(i,1,m){
			int x,y;
			cin>>x>>y;
			a[x][y]=a[y][x]=1;
		}
		int cnt=0;
		rep(i,0,n-1)
			if (!v[i]){
				dfs(i);
				cnt++;
			}
		cout<<cnt-1<<endl;
	}
	return 0;
}