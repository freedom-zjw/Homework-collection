#include<iostream>
#include<cstdio>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
char s[30];
int n;
void dfs(int x,int len){
	if (x==n)return;
	s[len]=char(x+65);
	s[len+1]=0;
	printf("%s\n",s);
	dfs(x+1,len+1);
	dfs(x+1,len);
}
int main(){
    scanf("%d",&n);
    memset(s,0,sizeof(s));
    printf("\n");
    dfs(0,0);
	return 0;
}