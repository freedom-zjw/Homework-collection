#include<iostream>
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 510
#define maxint 1000000000
using namespace std;
int a[N][N],d[N],v[N];
int solve(int n){
    rep(i,1,n){
        d[i]=maxint;
        v[i]=0;
    }
    d[1]=0;
    int ans=0,minn,p;
    rep(k,1,n){
        minn=maxint,p=-1;
        rep(i,1,n)
            if (!v[i]&&minn>d[i])
                minn=d[p=i];
        v[p]=1;
        ans=ans<d[p]?d[p]:ans;
        rep(i,1,n)
            if (!v[i]&&d[i]>a[p][i])
                d[i]=a[p][i];
    }
    return ans;
}
int main(){
    int T,n;
    cin>>T;
    rep(tt,1,T){
        cin>>n;
        rep(i,1,n)
            rep(j,1,n)
                cin>>a[i][j];
        if (tt>1)cout<<endl;
        cout<<solve(n)<<endl;
    }
    return 0;
}                                 