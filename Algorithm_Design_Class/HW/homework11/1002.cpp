#include<iostream>
#include<cstdio>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n;
int a[11],v[11];
void dfs(int x){
	if (x==n+1){
		rep(i,1,n-1)printf("%d ",a[i]);
		printf("%d\n",a[n]);
		return;
	}	
	rep(i,1,n)
		if (!v[i]){
			v[i]=1;
			a[x]=i;
			dfs(x+1);
			v[i]=0;
		}
}
int main(){
	scanf("%d",&n);
	dfs(1);
} 