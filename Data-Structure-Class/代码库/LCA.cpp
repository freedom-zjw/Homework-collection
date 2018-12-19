#include<iostream>
#include<cmath>
using namespace std;
struct node{int y,d,next;}edge[20010];
struct nnode{int dis,par[18],dep;}t[10010];
int n,m,len,first[10010],maxx;
inline void ins(int x,int y,int d)
{  edge[++len].y=y;edge[len].d=d;
   edge[len].next=first[x];first[x]=len;
}
inline void build(int x,int fa,int dis)
{   int i,k;
    t[x].par[0]=fa;t[x].dep=t[fa].dep+1;
	t[x].dis=dis;maxx=maxx>t[x].dep?maxx:t[x].dep; 
    for (i=1;t[x].dep-(1<<i)>=1;i++)
	   t[x].par[i]=t[t[x].par[i-1]].par[i-1];
    for (k=first[x];k!=-1;k=edge[k].next)
	   if (edge[k].y!=fa)build(edge[k].y,x,dis+edge[k].d);
}
inline int LCA(int x,int y)
{ int i;
  if (t[x].dep<t[y].dep){int tt;tt=x;x=y;y=tt;}
  for(i=maxx;i>=0;i--)
     if (t[x].dep-t[y].dep>=(1<<i))
        x=t[x].par[i];
  if (x==y)return x;
  for (i=maxx;i>=0;i--)
     if (t[x].dep>(1<<i)&&t[x].par[i]!=t[y].par[i])
     {   x=t[x].par[i];y=t[y].par[i];
     }
  return t[x].par[0];
}
int main()
{  int i,x,y,d,root;len=0;maxx=18;
   scanf("%d%d",&n,&m);
   memset(first,-1,(n+1)*sizeof(int));
   for (i=1;i<n;i++){
   	   scanf("%d%d%d",&x,&y,&d);
   	   ins(x,y,d);ins(y,x,d);
   }
   build(1,0,0);
   for (i=1;i<=m;i++)
   {  scanf("%d%d",&x,&y);
      printf("%d\n",t[x].dis+t[y].dis-2*t[LCA(x,y)].dis);
   }
}