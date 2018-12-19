#include<iostream>
#include<cstring>
#include<queue>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
const int dx[]={1,0,-1,0,0,0};
const int dy[]={0,1,0,-1,0,0};
const int dz[]={0,0,0,0,1,-1};
int minx,maxx,miny,maxy,minz,maxz,ans;
struct node{
	int x,y,z;
	node (){}
	node(int xx,int yy,int zz):x(xx),y(yy),z(zz){}
	bool legal(){ return x>=minx&&x<=maxx && y>=miny&&y<=maxy && z>=minz&&z<=maxz; }   
};
bool bo[101][101][101];
void bfs(int x,int y,int z){
	bo[x][y][z]=true;
	int flag=1;
	queue<node>Q;
	Q.push(node(x,y,z));
	while (!Q.empty()){
		node now=Q.front();
		Q.pop();
		rep(i,0,5){
			node next(now.x+dx[i],now.y+dy[i],now.z+dz[i]);
			if (!next.legal()) flag=false;
			else if (!bo[next.x][next.y][next.z]){
				bo[next.x][next.y][next.z]=true;
				Q.push(next);
			}
		}
	}
	if (flag)ans++;
}
int main(){
	int T,x,y,z,n;
	cin>>T;
	rep(tt,1,T){
		cin>>n;
		ans=0;
		memset(bo,false,sizeof(bo));
		minx=miny=minz=100;
		maxx=maxy=maxz=0;
		rep(i,1,n){
			cin>>x>>y>>z;
			bo[x][y][z]=true;
			maxx=maxx>x?maxx:x;
			maxy=maxy>y?maxy:y;
			maxz=maxz>z?maxz:z;
			
			minx=minx<x?minx:x;
			miny=miny<y?miny:y;
			minz=minz<z?minz:z;
		}	
		rep(i,minx,maxx)
			rep(j,miny,maxy)
				rep(k,minz,maxz)
					if (!bo[i][j][k])
						bfs(i,j,k);
		cout<<ans<<endl;
	}
}