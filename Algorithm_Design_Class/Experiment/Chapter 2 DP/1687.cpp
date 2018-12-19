#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int f[101][101];
int main(){
    int n,k;
    rep(i,0,100){
        f[i][0]=1;
        f[0][i]=0;
    }
    rep(i,1,100)
        rep(j,1,100)
               f[i][j]=(f[i-1][j]*(j+1)+f[i-1][j-1]*(i-j))%2007;
    while(cin>>n>>k)
    	cout<<f[n][k]<<endl;
    return 0;
}
