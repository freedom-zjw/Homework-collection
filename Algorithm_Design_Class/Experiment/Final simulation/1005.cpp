#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define rep2(i,u,v) for (int i=(u);i<=(v);i+=2)
#define N 10000000
int p[N],v[N+1],huiwen[800];
int tot,tot2;
void check(int x){
	int res=0,old=x;
	while (x){
		res=res*10+x%10;
		x/=10;
	}
	if (res==old)huiwen[++tot2]=old;
}
void init(){
	p[++tot]=2;
	rep(i,3,N){
		if (!v[i]){
			p[++tot]=i;
			check(i);
		}
		for (int j=1;j<=tot;j++){
			if ((long long)p[j]*i>N)break;
			v[p[j]*i]=1;
			if (i%p[j]==0)break;
		}
	}
}
int main(){
	init();
	int n,m;
	while (scanf("%d%d",&n,&m)!=EOF){
		if (!n && !m)break;
		rep(i,1,tot2)
			if (huiwen[i]>=n){
				for (int j=i;j<=tot2&&huiwen[j]>=n&&huiwen[j]<=m;j++)
					printf("%d\n",huiwen[j]);
				break;
			}
	}
}