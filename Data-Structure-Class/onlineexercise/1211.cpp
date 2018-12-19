#include<iostream>
#include<cstdio>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
    int n,m,L,x,y;
    scanf("%d%d%d",&n,&m,&L);
    int a[n+1][n+1],ans[n+1][n+1],b[n+1][n+1];
    memset(a,0,sizeof(a));
    memset(ans,0,sizeof(ans));
    rep(i,1,m){
        scanf("%d%d",&x,&y);
        a[x][y]++;
    }
    rep(i,1,n)ans[i][i]=1;
    while(L){
        if (L&1){
            memset(b,0,sizeof(b));
            rep(i,1,n)
                rep(j,1,n)
                    rep(k,1,n)
                        b[i][j]+=ans[i][k]*a[k][j];
            memcpy(ans,b,sizeof(b));
        }
        L>>=1;
        memset(b,0,sizeof(b));
            rep(i,1,n)
                rep(j,1,n)
                    rep(k,1,n)
                        b[i][j]+=a[i][k]*a[k][j];
            memcpy(a,b,sizeof(b));
    }
    scanf("%d",&m);
    rep(i,1,m){
        scanf("%d%d",&x,&y);
        printf("%d\n",ans[x][y]);
    }
}                   