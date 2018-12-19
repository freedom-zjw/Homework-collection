#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int T,n,m,x,y;
	cin>>T;
	rep(t,1,T){
		cin>>n>>m;
		rep(i,1,m)cin>>x>>y;
		cout<<n-1<<endl;
	}
	return 0;
} 
