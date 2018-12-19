#include<iostream>
#include<cstdio>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	char c;
	int l,r;
}t[1010];
int bo[1010],root;
void dfs(int x){
	cout<<t[x].c;
	if (t[x].l)dfs(t[x].l);
	if (t[x].r)dfs(t[x].r);
}
int main(){
	int n,idx,l,r;
	char c;
	while (cin>>n){
		memset(bo,0,sizeof(bo));
		rep(i,1,n){
			scanf("%d %c %d %d",&idx,&c,&l,&r);
			t[idx].c=c;
			t[idx].l=l;
			t[idx].r=r; 
			bo[idx]++;
			bo[l]++;
			bo[r]++;
		}
		rep(i,1,1000)
			if(bo[i]==1){
				root=i;
				break;
			}	
		dfs(root);
		cout<<endl;
	}
	return 0;
} 