#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int a[210];
int f[210][210];
int dp(int l,int r){
	if (f[l][r])return f[l][r];
	if (l+1==r)return f[l][r]=a[l]*a[l+1]*a[r+1];
	int maxx=0;
	rep(i,l,r-1){
		int tmp=dp(l,i)+dp(i+1,r)+a[l]*a[i+1]*a[r+1];
		maxx=max(maxx,tmp);
	}
	return f[l][r]=maxx;
}
int main(){
	int n;
	while (cin>>n){
		rep(i,1,n){
			cin>>a[i];
			a[i+n]=a[i];
		}
		int ans=0;
		memset(f,0,sizeof(f));
		rep(i,1,n)
			ans=max(ans,dp(i,i+n-1));
		cout<<ans<<endl;
	}
	return 0;
}