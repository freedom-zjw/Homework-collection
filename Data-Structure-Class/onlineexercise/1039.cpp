#include<iostream>
#include<vector>
#include<cstdio>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	double x,y;
}a[13];
vector<int>g[13];
int color[13],n;
double dis(int x,int y){
	double xx=a[x].x-a[y].x;
	double yy=a[x].y-a[y].y;
	return xx*xx+yy*yy;
}
void dfs(int x,int fa,int col,int tot){
	int len=g[x].size();
	color[x]=col;
	rep(i,0,len-1){
		int y=g[x][i];
		if (y==fa)continue;
		if (color[x]==color[y]){
			color[x]=0;
			return;
		}
	}
	rep(i,0,len-1){
		int y=g[x][i];
		if (y==fa)continue;
		if (!color[y]){
			rep(j,1,tot)
				if (j!=col&&color[y]==0)
					dfs(y,x,j,tot);
		}
	}
		
}
int painted(int ans){
	memset(color,0,sizeof(color));
	rep(i,1,n)
		if (!color[i])
			dfs(i,0,1,ans);
	rep(i,1,n)
		if (!color[i])return 0;
	return 1;	
}
int main(){
	int T=0;
	while (cin>>n,n>0){
		rep(i,1,n){
			cin>>a[i].x>>a[i].y;
			g[i].clear();
			rep(j,1,i-1)
				if (dis(i,j)<=400){
					g[i].push_back(j);
					g[j].push_back(i);
				}
		}
		int ans;
		for (ans=1;;ans++)
			if (painted(ans))
				break;
		printf("The towers in case %d can be covered in %d frequencies.\n",++T,ans);
	}
	return 0;
} 