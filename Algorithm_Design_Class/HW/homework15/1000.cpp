#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 105
int n,m;
int a[N][N],match[N],v[N],ans;
int getmatch(int x){
	rep(i,1,m){
		if (!v[i] && a[x][i]){
			v[i]=1;
			if (match[i]==0 || getmatch(match[i])){
				match[i] = x;
				return 1;
			}
		}
	}
	return 0;
}
int main(){
	ios::sync_with_stdio(false);
	while (cin>>n>>m){
		ans=0;
		rep(i,1,n)
			rep(j,1,m)
				cin>>a[i][j];
		memset(match,0,sizeof(match));
		rep(i,1,n){
			memset(v,0,sizeof(v));
			if (getmatch(i))ans++;
		}
		cout<<ans<<endl;
	}
	return 0;
}