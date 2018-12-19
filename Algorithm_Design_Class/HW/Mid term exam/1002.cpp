
//×îÔ¶¾àÀë 
#include<iostream>
#include<algorithm>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n, x, y, ans, v[26], list[26], a[26][26], d[26];
int bfs(int s){
	int l=1,r=0;
	list[++r]=s;
	v[s]=1;
	memset(d,0,sizeof(d));
	while (l<=r){
		int x=list[l++];
		ans=max(ans,d[x]);
		rep(i,1,n)
			if (a[x][i]==1 && !v[i]){
				d[i]=d[x] + 1;
				v[i]=1;
				list[++r]=i;
			}
	}
	return list[r];
}
int main(){
	int T,x,y;
	cin>>T;
	rep(tt,1,T){
		cin>>n;
		memset(a,0,sizeof(a)); 
		rep(i,1,n-1){
			cin>>x>>y;
			a[x][y] = a[y][x] = 1; 
		} 
		ans=0;
		memset(v,0,sizeof(v));
		int x=bfs(1);
		memset(v,0,sizeof(v));
		int y=bfs(x);
		cout<<ans<<endl;
	}
	return 0; 
} 