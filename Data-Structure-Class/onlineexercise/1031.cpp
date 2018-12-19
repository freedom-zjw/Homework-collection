#include<iostream>
#include<string>
#include<cstring>
#include<map>
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
using namespace std;
const int maxint=1000000;
int a[210][210];
int dis[210],vis[210];
int calc(int n,int s,int t){
    rep(i,1,n)
        dis[i]=maxint;
    dis[s]=0;
    memset(vis,0,sizeof(vis));
    rep(k,1,n){
        int p=s,minn=maxint;
        rep(i,1,n)
            if (!vis[i]&&minn>dis[i]){
                minn=dis[i];
                p=i;
            }
        vis[p]=1;
        rep(i,1,n)
            if (!vis[i]&&dis[p]+a[p][i]<dis[i]){
                dis[i]=dis[p]+a[p][i];
            }
    }
    if (!vis[t])return -1;
    else return dis[t];
}
int main(){
    int T,n,d,cnt;
    string s1,s2;
    cin>>T;
    rep(tt,1,T){
        map<string,int> f;
        cnt=0;
        cin>>n;
        rep(i,0,209)
            rep(j,0,209)
                a[i][j]=(i==j?0:maxint);
        rep(i,1,n){
            cin>>s1>>s2>>d;
            int &ret1=f[s1];
            int &ret2=f[s2];
            if (!ret1)ret1=++cnt;
            if (!ret2)ret2=++cnt;
            a[ret1][ret2]=a[ret2][ret1]=d;
        }
        cin>>s1>>s2;
        if (s1==s2)cout<<0<<endl;
        else if (!f[s1] || !f[s2])cout<<-1<<endl;
        else {

            int s=f[s1],t=f[s2];
            cout<<calc(cnt,s,t)<<endl;
        }
    }
}                        