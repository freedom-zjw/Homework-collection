#include<iostream>
#include<algorithm>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int w[110];
int dp[11000],bo[11000];
int main(){
	int L,S,T,M;
	cin>>L>>S>>T>>M;
	rep(i,1,M)cin>>w[i];
	sort(w+1,w+1+M);
	if (S==T){//单独处理S==T的情况 
		int ans=0;
		rep(i,1,M)
			if (w[i]%S==0)ans++;
		cout<<ans<<endl;
	}
	else {
		int k=S*T;
		w[0]=0;w[M+1]=L;
		rep(i,1,M+1){
			if (w[i]-w[i-1]>k){
				int delta=w[i]-w[i-1]-k;
				rep(j,i,M+1)
					w[j]-=delta;
			}
			bo[w[i]]=1;
		}	
		bo[w[M+1]]=0;
		memset(dp,-1,sizeof(dp));
		dp[0]=0;
		rep(i,0,w[M+1]){
			if (dp[i]==-1)continue;
			rep(j,i+S,i+T){
				if (dp[j]==-1||dp[i]+bo[j]<dp[j])
					dp[j]=dp[i]+bo[j];
			}
		}
		int ans=110;
		rep(i,w[M+1],w[M+1]+T)ans=min(ans,dp[i]);
		cout<<ans<<endl;
	} 
	return 0;
}