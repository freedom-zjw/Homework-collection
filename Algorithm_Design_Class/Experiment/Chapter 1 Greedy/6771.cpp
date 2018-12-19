#include<iostream>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int a[7];
	int num[7]={20,20,20,25,25,30,30};
	while (cin>>a[0]){
		int flag=a[0],ans=0;
		rep(i,1,6)cin>>a[i],flag|=a[i];
		if (!flag)break;
		rep(i,0,6){
			if (!a[i])continue;
			ans+=a[i]/num[i];
			a[i]%=num[i];
			if (!a[i])continue;
			ans++;
			if (i<6&&a[i+1]>0)
				a[i+1]-=min(a[i+1],num[i]-a[i]);
		}
		cout<<ans<<endl;
	}
	return 0;	
}