#include<iostream>
#include<algorithm>
using namespace std;
#define rep(i,u,v)for (int i=(u);i<=(v);i++)
#define INF 10000000
int n,m,q;
int a[110][110];
int main(){
	int x,y,d;
	scanf("%d%d%d",&n,&m,&q);
	rep(i,1,n)
		rep(j,1,n)
			if (i!=j)
				a[i][j]=INF;
    rep(i,1,m){
    	scanf("%d%d%d",&x,&y,&d);
    	a[x][y]=min(a[x][y],d);
    }
    rep(k,1,n)
    	rep(i,1,n)
    		rep(j,1,n)
    				a[i][j]=min(a[i][j],a[i][k]+a[k][j]);
	rep(i,1,q){
		scanf("%d%d",&x,&y);
		if (a[x][y]==INF)printf("-1\n");
		else printf("%d\n",a[x][y]);
	}
	return 0;
} 