#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int p[1230],v[10001],sum[1230];
int tot;
void init(){
	rep(i,2,10000){
		if (v[i])continue;
		p[++tot]=i;
		sum[tot]=sum[tot-1]+i;
		for (int j=i*i;j<=10000;j+=i)
			v[j]=1;
	}
}
int main(){
	ios::sync_with_stdio(false);
	init();
	int n;
	while (cin>>n&&n){
		int ans=0;
		rep(i,1,tot){
			rep(j,i,tot){
				if (sum[j]-sum[i-1]>n)break;
				if (sum[j]-sum[i-1]==n)ans++; 
			}
		}
		cout<<ans<<endl;
	}
	return 0;
}