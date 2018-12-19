#include<iostream>
#include<cstdio>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int dp[31],sum[31];
int main(){
    int n;
    dp[0]=1;
    sum[0]=dp[0]*2;
    dp[2]=3;
    sum[2]=dp[2]*2+sum[0];
    rep(i,3,30){
        if (i%2==1){//n为奇数无方案 
            dp[i]=0;
            sum[i]=sum[i-1]; 
        }
        else {//n为偶数 
            dp[i]=dp[i-2]*dp[2]+sum[i-4];
            sum[i]=sum[i-1]+dp[i]*2; 
        }
    } 
    while (cin>>n){
        if (n==-1)break;
        cout<<dp[n]<<endl;
    }
    return 0; 
}                                 