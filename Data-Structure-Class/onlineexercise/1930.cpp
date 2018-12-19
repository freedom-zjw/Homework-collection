#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
#include<cmath>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int T,n,ans,a[102],minn,pos;
	cin>>T;
	while (T--){
		cin>>n;
		ans=0;
		rep(i,1,n)cin>>a[i];
		rep(i,1,n-1){
			minn=a[i];
			pos=i;
			rep(j,i+1,n)
				if (a[j]<a[pos]){
					ans++;
					minn=a[j];
					pos=j;
				}
			swap(a[i],a[pos]);
		}
		cout<<ans<<endl;
	}
	return 0;
}