#include<iostream>
#include<cstdio>
#include<cstdlib>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n,m,now,maxm;
long long f[2][1<<11];
void dfs(int pos,int s,int j){
	if (pos>m){
		f[now][s]+=f[1-now][j];
		return;
	}
	if ((j>>(pos-1))&1)dfs(pos+1,s,j);//上面竖着铺了一个下来，这格不用管 
	else {
		if (pos<m&&!((j>>pos)&1))dfs(pos+2,s,j);//左边横着铺了一个过来，这格不用管 
		dfs(pos+1,s|(1<<pos-1),j);//这格自己打竖铺 
	}
}
int main(){ 
	while (scanf("%d%d",&n,&m),n>0){
		if ((n*m)%2){printf("0\n");continue;}
		memset(f[0],0,sizeof(f[0]));
		f[0][0]=1;now=0;
		maxm=(1<<m)-1;
		rep(i,1,n){
			now=1-now;memset(f[now],0,sizeof(f[now]));
			rep(j,0,maxm)
				if (f[1-now][j])dfs(1,0,j);
		}
		cout<<f[now][0]<<endl;
	}
	return 0;
}