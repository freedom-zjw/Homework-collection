#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int p[100002],v[1300001];
int tot;
void init(){
	rep(i,2,1299709){
		if (!v[i])p[++tot]=i;
		for (int j=1;j<=tot;j++){
			if ((long long)p[j]*i>1299709)break;
			v[p[j]*i]=1;
			if (i%p[j]==0)break;
		}
	}
}
int main(){
	ios::sync_with_stdio(false);
	init();
	int n,ans;
	while(cin>>n&&n){
		ans=0;
		rep(i,1,tot){
			if (p[i]==n)break;
			if (p[i]<n&&i==tot)break;
			if (p[i]>n)break;
			if (p[i]<n&&p[i+1]>n){
				ans=p[i+1]-p[i];
				break;
			}
		}
		cout<<ans<<endl;
	}
	return 0;
}