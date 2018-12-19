#include<iostream>
#include<cstdio>
#include<cstring>
#include<queue>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	int px,py,hx,hy,dep;
	node(){}
	node(int _px,int _py,int _hx,int _hy,int _dep){
		px=_px;py=_py;hx=_hx;hy=_hy;dep=_dep;
	}
};
const int dx[]={-1,1,0,0};
const int dy[]={0,0,-1,1};
int n,m,px,py,hx,hy;
int hdir[4],v[21][21][21][21];
char s[25][25];
queue<node>Q;
void bfs(){
	Q.push(node(px,py,hx,hy,0));
	v[px][py][hx][hy]=1;
	while (!Q.empty()){
		node n0=Q.front();
		Q.pop();
		rep(i,0,3){
			int npx=n0.px+dx[i],npy=n0.py+dy[i];
			int nhx=n0.hx+dx[hdir[i]],nhy=n0.hy+dy[hdir[i]];
			if (s[npx][npy]!='#' && s[npx][npy]!='!' && s[nhx][nhy]!='!'){
				if (s[nhx][nhy]=='#')nhx=n0.hx,nhy=n0.hy;
				if (v[npx][npy][nhx][nhy])continue;
				if (npx==nhx&&npy==nhy){//移动到同一格 
					printf("%d\n",n0.dep+1);
					return; 
				}
				if (npx==n0.hx&&npy==n0.hy && nhx==n0.px&&nhy==n0.py){
					//原本在相邻两个，移动中相遇 
					printf("%d\n",n0.dep+1);
					return;
				}
				v[npx][npy][nhx][nhy]=1;
				Q.push(node(npx,npy,nhx,nhy,n0.dep+1)); 
			}
		} 
	}
	printf("Impossible\n");
}
int main(){
	scanf("%d%d",&n,&m);
	rep(i,1,n)scanf("%s",s[i]+1);
	rep(i,1,n)
		rep(j,1,m)
			if (s[i][j]=='P')px=i,py=j;
			else if (s[i][j]=='H')hx=i,hy=j;
	scanf("%s",s[n+1]);
	rep(i,0,3){
		if (s[n+1][i]=='N')hdir[i]=0;
		else if (s[n+1][i]=='S')hdir[i]=1;
		else if (s[n+1][i]=='W')hdir[i]=2;
		else if (s[n+1][i]=='E')hdir[i]=3;
	}
	bfs();
	return 0;
}
 