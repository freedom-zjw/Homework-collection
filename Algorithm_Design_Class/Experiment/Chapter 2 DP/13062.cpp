#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--)
long long f[32][32];
int main(){
	f[1][1]=1;
	rep(i,1,31)
		rep(j,i,31){
			if (i-1>0)f[i][j]+=f[i-1][j];
			if(j-1>=i)f[i][j]+=f[i][j-1];
		}		
	int n;
	while (cin>>n&&n)
		cout<<f[n+1][n+1]<<endl;
	return 0;	
}