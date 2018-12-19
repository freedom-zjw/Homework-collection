#include<iostream>
#include<algorithm>
#include<cmath>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int s1[502],s2[502],dp[502][502];
int main(){
	int T,n,m;
	cin>>T;
	while (T--){
		cin>>n>>m;
		rep(i,1,n)cin>>s1[i];
		rep(i,1,m)cin>>s2[i];
		sort(s1+1,s1+1+n);
		sort(s2+1,s2+1+m);
        rep(i,1,n)
        	rep(j,i,m){
                if (i==j)dp[i][i]=dp[i-1][i-1]+abs(s1[i]-s2[i]);
        		else dp[i][j]=min(dp[i-1][j-1]+abs(s1[i]-s2[j]),dp[i][j-1]);		
			}
		 cout<<dp[n][m]<<endl;
	}
	return 0;
} 