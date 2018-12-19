#include<iostream>
#include<cstring>
#include<cstdio>
#include<cstdlib>
using namespace std;
#define INF 0x5fffffff
struct node
{ int y,next,c,oth;
}edge[1500];
int len,n,ed,st,first[22],h[22],list[32];
void ins(int x,int y,int c)
{   int k1=++len,k2=++len;
    edge[k1].y=y;edge[k1].c=c;edge[k1].next=first[x];
    edge[k1].oth=k2;first[x]=k1;
    edge[k2].y=x;edge[k2].c=0;edge[k2].next=first[y];
    edge[k2].oth=k1;first[y]=k2;
}
bool bfs()
{   int x,y,k,head,tail;
    memset(h,-1,sizeof(h));
    list[1]=st;h[st]=0;
    for (head=tail=1;head<=tail;head++){
        x=list[head];
        for (k=first[x];k!=-1;k=edge[k].next){
            y=edge[k].y;
            if (edge[k].c&&h[y]==-1){
            	h[y]=h[x]+1;
            	list[++tail]=y;
            }
		}
	}
	return h[ed]!=-1;
}
int dfs(int x,int flow){
	int ff,minf,k,y;
	if (x==ed)return flow;ff=0;
	for (k=first[x];k!=-1;k=edge[k].next)
	{   y=edge[k].y;
	    if (edge[k].c&&h[y]==h[x]+1&&ff<flow&&(minf=dfs(y,min(edge[k].c,flow-ff))))
	    {     edge[k].c-=minf;ff+=minf;
	          edge[edge[k].oth].c+=minf;
	    }
	}
	if (!ff)h[x]=-1;
	return ff;
}
int main()
{   int cases,i,ans,tt,x,j,k;char s[5];
	scanf("%d",&cases);
	while (cases-->0){ans=0;len=0;
		scanf("%d%d",&n,&ed);st=0;ed++;
		memset(first,-1,sizeof(first));
		for (i=1;i<=n;i++){
			scanf("%s",s);
			if (s[0]=='I')ins(st,i,INF);
			scanf("%d",&k);
			for (j=1;j<=k;j++){
				scanf("%d",&x);
				ins(i,x+1,INF);
				ins(x+1,i,1);
			}
		}
		while (bfs())
		  while (tt=dfs(st,INF))ans+=tt;
        if (ans>=INF)printf("PANIC ROOM BREACH\n");
        else printf("%d\n",ans);
	}
	
}
