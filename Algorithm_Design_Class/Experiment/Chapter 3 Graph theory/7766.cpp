#include<iostream>
#include<algorithm>
#include<cstdio>
#include<vector>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct edge{
	int x,y,z;
	edge(int xx,int yy,int zz){
		x=xx;y=yy;z=zz;
	}
};
int f[200005];
vector<edge>e;
int findfa(int x){
	if (f[x]!=x)f[x]=findfa(f[x]);
	return f[x];
}
bool cmp(const edge &n1,const edge &n2){
	return n1.z<n2.z;
}
int main(){
	int n,m,x,y,z,ans;
	while (scanf("%d%d",&m,&n)!=EOF){
		if (!m&&!n)break;
		ans=0;e.clear();
		rep(i,1,n){
			scanf("%d%d%d",&x,&y,&z);
			e.push_back(edge(x,y,z));
			ans+=z;
		}
		rep(i,0,m)f[i]=i;
		sort(e.begin(),e.end(),cmp);
		rep(i,0,n-1){
			int x=findfa(e[i].x),y=findfa(e[i].y);
			if (x==y)continue;
			f[x]=y;
			ans-=e[i].z;
		}
		cout<<ans<<endl;
	}
	return 0;
}