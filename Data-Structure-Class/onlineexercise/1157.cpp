#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
#include<cmath>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--)
int main(){
    int n,x,ans;
    while (cin>>n,n){
        ans=-999999999;
        rep(i,1,n)
            cin>>x,ans=max(ans,x);
        cout<<ans<<endl;
    }
    return 0;
}                                 