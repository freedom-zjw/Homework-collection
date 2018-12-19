#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int p[80000],v[1000001],a[110];
int tot;
void init(){
	rep(i,2,1000000){
		if (!v[i])p[++tot]=i;
		for (int j=1;j<=tot;j++){
			if ((long long)p[j]*i>1000000)break;
			v[p[j]*i]=1;
			if (i%p[j]==0)break;
		}
	}
}
int check(int x){
	int ret=0;
	rep(i,1,a[0])
		ret=(ret*10+a[i])%x;
	return ret!=0;
}
int main(){
	ios::sync_with_stdio(false);
	init();
	int L;
	char K[110];
	while(cin>>K>>L){
		if (K[0]=='0'&&L==0)break;
		a[0]=strlen(K);
		rep(i,0,a[0]-1)a[i+1]=K[i]-'0';
		rep(i,1,tot){
			if (p[i]>=L){
				cout<<"GOOD"<<endl;
				break;
			}
			if (!check(p[i])){
				cout<<"BAD "<<p[i]<<endl;
				break;
			}
			if (i==tot){
				cout<<"GOOD"<<endl;
				break;
			}
		}
	}
	return 0;
}