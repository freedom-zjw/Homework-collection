#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++) 
const int md=1000000007;
int f[102],n;
int main(){
	f[0]=0;
	f[1]=1;
	rep(i,2,100)f[i]=(f[i-1]+f[i-2])%md;
	while (cin>>n)
		cout<<f[n]<<endl;
	return 0; 
}