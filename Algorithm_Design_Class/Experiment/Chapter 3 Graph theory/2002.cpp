#include<iostream>
#include<cstdio>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	int x,y;
	void add(int xx,int yy){
		x=xx;y=yy;
	}
}list[752*752];
const int dx[]={0,-1,-1,-1,0,1,1,1};
const int dy[]={-1,-1,0,1,1,1,0,-1};
int W,H,ans=0,sum;
char s[753][753];
int bo[753][753];
void floodfill(int stx,int sty){
	int l=1,r=1;
	list[1].add(stx,sty);
	bo[stx][sty]=1;
	while (l<=r){
		int x=list[l].x,y=list[l].y;
		l++;sum++;
		rep(i,0,7){
			int xx=x+dx[i];
			int yy=y+dy[i];
			if (xx<=0||xx>H)continue;
			if (yy<=0||yy>W)continue;
			if (bo[xx][yy]||s[xx][yy]=='*')continue;
			list[++r].add(xx,yy);
			bo[xx][yy]=1;
		}
	}
}
int main(){
	cin>>W>>H;
	rep(i,1,H)scanf("%s",s[i]+1);
	memset(bo,0,sizeof(bo));
	rep(i,1,H)
		rep(j,1,W)
			if (s[i][j]=='.'&& !bo[i][j]){
				sum=0;
				floodfill(i,j);
				if (sum>ans)ans=sum;
			}
	cout<<ans<<endl;
	return 0;
} 
