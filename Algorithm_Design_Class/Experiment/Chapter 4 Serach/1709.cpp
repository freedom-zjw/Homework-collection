#include<stdio.h>
#include<math.h>
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
const double delta=acos(-1.0)/4.0;
inline double dis(double x,double y,double xx,double yy){
	return sqrt( (x-xx)*(x-xx) + (y-yy)*(y-yy) );
}
double edx,edy,ans;
int limit_t;
inline void dfs(double x,double y,double dir,double t){
	double dist=dis(x,y,edx,edy);
	if (ans>dist)ans=dist;
	if (t==limit_t || ans<dist-10*(limit_t-t))return;
	dfs(x,y,dir-delta,t+1);
	x=x+cos(dir)*10.0;
	y=y+sin(dir)*10.0;
	dfs(x,y,dir,t+1);
}
int main(){
	int T,t;
	scanf("%d",&T);
	rep(tt,1,T){
		scanf("%d%lf%lf",&limit_t,&edx,&edy);
		ans=dis(0,0,edx,edy);
		dfs(0,0,0,0);
		printf("%.6lf\n",ans);
	}
	return 0;	
}                       