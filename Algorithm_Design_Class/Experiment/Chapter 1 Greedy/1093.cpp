#include<iostream>
#include<cstdio>
#include<algorithm> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	int low,up,rate;
}a[5];
int main(){
	int x,T=0;
	a[1].low=0;a[4].up=1000;
	while (cin>>a[1].up>>a[1].rate){
		rep(i,2,4){
			if (i<4)cin>>a[i].up;
			a[i].low=a[i-1].up+1;
			cin>>a[i].rate;
		}
		printf("Set number %d:\n",++T);
		while (cin>>x&&x){
			int ans,ansx=x;
			rep(i,1,4){
				if (a[i].low<=x&&x<=a[i].up)
					ans=x*a[i].rate;
				else if (a[i].low>x){
					if (ans>a[i].low*a[i].rate){
						ans=a[i].low*a[i].rate;
						ansx=a[i].low;
					}
				}
			}
			printf("Weight (%d) has best price $%d (add %d pounds)\n",x,ans,ansx-x);
		}
		puts("");
	}
	return 0;
}