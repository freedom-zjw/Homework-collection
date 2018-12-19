#include<cstdio>
#include<cstring>
#include<iostream>
#define N 1010
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
using namespace std;
int n,len,dfn[N],low[N],belong[N],sta[N],instack[N];
int vis,cnt,top,size[N],ru[N],first[N];
struct node
{  int x,y,next;
}edge[1000010];
void ins(int x,int y)
{ edge[++len].x=x;edge[len].y=y;edge[len].next=first[x];first[x]=len;}
void tarjan(int x)
{  dfn[x]=low[x]=++vis;
   instack[x]=1;sta[++top]=x;
   for (int k=first[x];k;k=edge[k].next)
   {    int y=edge[k].y;
        if (!dfn[y])
        {   tarjan(y);
            if (low[x]>low[y])low[x]=low[y];
        }
        else if (instack[y]&&low[x]>dfn[y])low[x]=dfn[y];
   }
    if (low[x]==dfn[x])
       {  int u;  cnt++;
          do{
      	     u=sta[top--];
      	     instack[u]=0;
      	     belong[u]=cnt;
      	     size[cnt]++;
          }while (x!=u);
       }
}
void printff()
{   int tot=0,ans=0;
    rep(i,1,cnt)
       if (!ru[i]){
           tot++;
           if (tot>1){
           	   printf("0\n");
           	   return;
           }
           ans=i;
	   }
    printf("%d\n",size[ans]);
}
int main()
{   int T,k,x;
	scanf("%d",&T);
	while (T-->0)
	{    scanf("%d",&n);
	     len=vis=cnt=top=0;
		 rep(i,1,n){
		     first[i]=0;
		     dfn[i]=low[i]=belong[i]=0;
		     instack[i]=0;ru[i]=size[i]=0;
		 }
         rep(i,1,n)
         {    scanf("%d",&k);
              rep(j,1,k){
			     scanf("%d",&x);
			     ins(x,i);
              }
         }
         rep(i,1,n)
		   if (!dfn[i])tarjan(i);
         rep(i,1,len)
            if (belong[edge[i].x]!=belong[edge[i].y])
                ru[belong[edge[i].y]]++;
         printff();
	}
}