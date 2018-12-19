
//ÂôÅ£ÄÌ
#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int T,n,m,x,y,flag;
	cin>>T;
	rep(tt,1,T){
		cin>>x>>y>>n>>m;	
		int ans=0,cnt=0;
		flag=1;
		while (1){
			if (n>=x||m>=y){
				ans+=n/x+m/y;
				int n1=n%x + n/x + m/y;
				int m1=m%y + n/x + m/y;
				if (n1>=n&&m1>=m){
					flag=0;
					break;
				}
				n=n1;m=m1;
			}
			else break;
			cnt++;
			if (cnt>100){
				flag=0;
				break;
			}
		}
		if (!flag)cout<<"INF"<<endl;
		else cout<<ans<<endl;
	}
	return 0;
} 