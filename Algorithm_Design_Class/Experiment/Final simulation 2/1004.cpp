#include<iostream>
#include<cstdlib> 
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int n;
	while (cin>>n){
		if (!n)break;
		int a[n+1][n+1];
		rep(i,1,n)
			rep(j,1,n){
				cin>>a[i][j];
				if (a[i][j]==-1)a[i][j]=1000000000;
			}	
		rep(k,1,n)
			rep(i,1,n)
				rep(j,1,n)
					a[i][j]=min(a[i][j],a[i][k]+a[k][j]);
		int ans=0;
		rep(i,1,n)
			if (a[1][i]+a[i][n]==a[1][n])ans++;
		cout<<ans<<endl;
	}
	return 0;
}
