#include<iostream>
#include<cstdio>
#include<cstdlib>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define reph(k,x) for (int k=first[x];k;k=e[k].next)
#define repw(k,x) for (int &k=work[x];k;k=e[k].next)
#define sqr(x) ((x)*(x))
const long long  oo=(long long )1000000000;
struct node{
	int y,oth,next;
	long long c;
}e[110000];
struct nn{
	long long x,y,z;	
}a[52];
int n,m,len,st,ed;
long long r;
int first[11000],h[11000],work[11000],list[11000];
long long ans;
void ins(int x,int y,long long  c){
	int k1=++len,k2=++len;
	e[k1].y=y;e[k1].c=c;e[k1].next=first[x];first[x]=k1;
	e[k2].y=x;e[k2].c=0;e[k2].next=first[y];first[y]=k2;
	e[k1].oth=k2;e[k2].oth=k1;
}
int bfs(){
	memset(h,-1,sizeof(h));
	int l=1,r=1;
	h[st]=0;list[1]=0;
	while (l<=r){
	 	int x=list[l++];
	 	reph(k,x){
	 		int y=e[k].y;
			 if (h[y]==-1&&e[k].c){
			 	h[y]=h[x]+1;
			 	list[++r]=y;
			 }
  		}
	}
	return h[ed]!=-1;
}
long long  dfs(int x,long long flow){
	if (x==ed)return flow;
	long long  ff=0,minn;
	repw(k,x){
		int y=e[k].y;long long tt=min(e[k].c,flow);
		if (h[y]==h[x]+1&&e[k].c)
			if (minn=dfs(y,tt)){
				e[k].c-=minn;e[e[k].oth].c+=minn;
				ff+=minn;flow-=minn;
				if (!flow)break;
			}
	}
	return ff;
}
int main(){
	int T,y,x;long long tt;
	freopen("starwar.in","r",stdin);
	freopen("starwar.out","w",stdout);
	scanf("%d%d%I64d",&n,&m,&r);
	st=0;ed=n+1;r*=r;
	rep(i,1,n){
		scanf("%I64d%I64d%I64d",&a[i].x,&a[i].y,&a[i].z);
		if (sqr(a[i].x)+sqr(a[i].y)+sqr(a[i].z)>r)ins(i,ed,oo);
	}
	rep(i,1,m){
		scanf("%d%d",&x,&y);
		long long t=sqr(a[x].x-a[y].x)+sqr(a[x].y-a[y].y)+sqr(a[x].z-a[y].z);
		ins(x,y,t);ins(y,x,t);
	}ans=0;
	while (bfs()){
		memcpy(work,first,sizeof(first));
		while(tt=dfs(st,oo))ans+=tt;
	}
	cout<<ans<<endl;
}