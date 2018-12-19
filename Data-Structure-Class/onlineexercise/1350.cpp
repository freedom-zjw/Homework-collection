#include<iostream>
#include<cstdio>
#include<cstring>
#include<cstdlib> 
#include<stack>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++) 
#define N 1000002
int n,len,ans,v[N],col,g[N];
stack<int>Q;
void coloring(int s){
	while (!Q.empty())Q.pop();
	Q.push(s);
	v[s]=col;
	while (!Q.empty()){
		int x=Q.top();
		Q.pop(); 
		int y=g[x];
		if (v[y]==col){
			ans++;
			return;
		}
		if (!v[y]){
			Q.push(y);
			v[y]=col;
		}
	}
}
int main(){
	int x;
	while (scanf("%d",&n)!=EOF){
		ans=col=len=0;
		rep(i,1,n){
			scanf("%d",&x);
			v[i]=0;
			g[i]=x;
			if (x==i){
				ans++;
				v[i]=++col;
			}
		}
		rep(i,1,n)
			if (!v[i]){
				++col;
				coloring(i);
			}
		printf("%d\n",ans); 
	}
	return 0;
}