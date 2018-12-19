#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 105
int n;
int a[N],pa[N];
long long f[N][N];//f[i][j] 已经填好i个,还剩下j个比第i个小的方案数
 //ans=f[n][0] 
int main(){
    while (scanf("%d",&n),n>0){
        rep(i,1,n){
            scanf("%d",&a[i]);
            if (i>1){
                if (a[i-1]>a[i])pa[i-1]=0;
                else pa[i-1]=1;
            }
        }
        memset(f,0,sizeof(f));
        rep(i,0,n-1)f[1][i]=1;
        rep(i,1,n-1)
            rep(j,0,n-i)
                if (f[i][j]!=0){
                    if (pa[i]==0){
                        rep(k,0,j-1)f[i+1][k]+=f[i][j];
                    }
                    else {
                        rep(k,j,n-i-1)
                            f[i+1][k]+=f[i][j];
                    }
                }
        cout<<f[n][0]<<endl;
    }
    return 0;
}                                 