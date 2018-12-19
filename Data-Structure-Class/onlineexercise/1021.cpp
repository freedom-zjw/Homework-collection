#include<cstdio>
#include<iostream>
#include<cstring>
#include<cstdlib>
#include<stack>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 100002
int n;
int match[N*2];
stack<int> Q;
int main(){
	int x,y;
	while (scanf("%d",&n)&&n){
		while (!Q.empty())Q.pop();
		memset(match,0,sizeof(match));
		rep(i,1,n){
  			scanf("%d%d",&x,&y);
  			match[x]=match[y]=i;
		}
		rep(i,1,2*n){
			if (!Q.empty()){
				int ret=Q.top();
				if (match[ret]==match[i])Q.pop();
				else Q.push(i);
			}
			else Q.push(i);
		}
		if (Q.empty())printf("Yes\n");
		else printf("No\n");
	}
	return 0;
}