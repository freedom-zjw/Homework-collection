#include<iostream>
#include<cmath>
#include<cstdlib>
#include<cstring>
#include<cstdio>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int ans,l,r;
int calc(int x){
    int num=1;
    while (x!=1){
        if (x%2)x=x*3+1;
        else x/=2;
        num++;
    }
    return num;
}
int main(){
    cin>>l>>r;
    rep(i,l,r)
        ans=max(ans,calc(i));
    cout<<ans<<endl;
    return 0;
}                                 