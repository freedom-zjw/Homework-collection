#include<cstdio>
#include<cstring>
#include<iostream>
#include<cstdlib>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 1010
int bo[N][N],who[N][N],cnt[N],color[N],sta[N],dfn[N],low[N],bk[N];
int topp,n,tot,times;
void tarjan(int u){
    int i,v;
    dfn[u]=low[u]=++times;
    sta[topp++]=u;
    rep(v,1,n)
      if(!bo[u][v]){
         if(!dfn[v]){
            tarjan(v);
            if(low[v]<low[u])low[u]=low[v];
            if(low[v]==dfn[u]){
              who[tot][0]=u;
              for(i=1,sta[topp]=-1;sta[topp]!=v;i++)
                who[tot][i]=sta[--topp];
              if(i>2){
                cnt[tot]=i;
                tot++;
              }
            }
        }
        else if(low[u]>dfn[v])low[u]=dfn[v];
      }
}
int paint(int fa,int x,int now,int k){
    rep(y,0,cnt[k]-1)
      if((!bo[who[k][x]][who[k][y]])&&y!=fa){
        if(!color[y]){
          color[y]=3-now;
          if(paint(x,y,3-now,k))return 1;
        }
        else if(color[x]==color[y])return 1;
      }
    return 0;
}
int main(){
	freopen("ball.in","r",stdin);
	freopen("ball.out","w",stdout);
	int m,x,y,ans;
 	scanf("%d%d",&n,&m); 
	 memset(who,-1,sizeof(who));
    tot=topp=times=0;
 	rep(i,1,m){
        scanf("%d%d",&x,&y);
        bo[x][y]=bo[y][x]=1;
    }
    rep(i,1,n)bo[i][i]=1;
    rep(i,1,n)
      if(!dfn[i])tarjan(i);
    rep(k,0,tot-1){
      rep(i,0,cnt[k]-1)color[i]=0;
      color[0]=1;
      if(paint(-1,0,1,k))
        rep(i,0,cnt[k]-1)
          bk[who[k][i]]=1;
    }
    ans=0;
    rep(i,1,n)
      if(!bk[i])ans++;
    printf("%d\n",ans);
    return 0;  
}