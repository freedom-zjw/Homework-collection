#include<iostream>
#include<cstdio>
#include<queue>
#include<cstring> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
const int dx[]={-1,0,1,0,-1};
const int dy[]={0,1,0,-1,0}; 
int n,m,edx,edy,stx,sty;
int v[22][22][4]; 
char s[22][22];
struct node{
	int x,y,step,dir;
	//dir为方向，0，1，2，3分别表示上右下左 
	node(){}
	node(int xx,int yy,int s,int d){
		x=xx;y=yy;step=s;dir=d;
	}
};
queue<node>Q;
void bfs(){
	while (!Q.empty())Q.pop();
	rep(i,0,3){
		Q.push(node(stx,sty,0,i));
		v[stx][sty][i]=1;
	}
	while (!Q.empty()){
		node n0=Q.front();Q.pop();
		rep(i,n0.dir,n0.dir+1){
			int x=n0.x+dx[i],y=n0.y+dy[i];
			if (x<=0||x>n||y<=0||y>m)continue;
			if (s[x][y]=='X')continue;
			if (v[x][y][i%4])continue;
			if (s[x][y]=='F'){
				printf("%d\n",n0.step+1);
				return;
			}
			v[x][y][i%4]=1;
			Q.push(node(x,y,n0.step+1,i%4));
		}
	}
}
int main(){
	int T;
	scanf("%d",&T);
	rep(tt,1,T){
		scanf("%d%d",&n,&m);
		getchar(); 
		rep(i,1,n)gets(s[i]+1);
		memset(v,0,sizeof(v));
		rep(i,1,n)
			rep(j,1,m)
				if (s[i][j]=='S')stx=i,sty=j;
				else if (s[i][j]=='F')edx=i,edy=j;
		bfs();
	}
	return 0;
}