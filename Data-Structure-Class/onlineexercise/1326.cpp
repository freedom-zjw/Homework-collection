#include<iostream>
#include<cstring>
#include<cstdlib>
#include<cstdio>
#include<string>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 2001
string s;
int a[N*2];
int Idx[N][2]; 
int n,ncnt,A,B,fa[N],dep[N];
void dfs(int now,int k){
	if (s[k]=='0'){
		a[k+1]=++ncnt;
		fa[ncnt]=now;
		dep[ncnt]=dep[now]+1;
		Idx[ncnt][0]=k+1;
		dfs(ncnt,k+1);
	}
	else if (s[k]=='1'){
		Idx[now][1]=k+1;
		a[k+1]=now;
		dfs(fa[now],k+1); 
	}
}
int main(){
	int T;
	cin>>T;
	rep(tt,1,T){
		cin>>n>>s;
		ncnt=0;
		dfs(0,0);
		cin>>A>>B;
		A=a[A];B=a[B];
		if (dep[A]<dep[B])swap(A,B);
		while (dep[A]>dep[B])A=fa[A];
		while (A!=B){
			A=fa[A];
			B=fa[B];
		}
		cout<<Idx[A][0]<<" "<<Idx[A][1]<<endl;
	}
	return 0;
}