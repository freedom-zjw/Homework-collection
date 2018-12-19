#include<iostream>
#include<cstring>
#include<cstdio>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int f[4*30][16];
void solve(int T,int n,int m) {
	memset(f,0,sizeof(f));
	f[0][(1<<m)-2]=1;//假设网格的上面还有一行且已经填满 
	for (int i=1;i<n*m;i++)//枚举格子 
		for (int k=0;k<(1<<m);k++)//枚举二进制状态 
			if (k&1) {//如果第i格被覆盖 
				if (i/m>0)f[i][k]=f[i-1][k>>1];//如果有上一行，就可以竖着放一个 
				if (i%m>0&&(k&2))f[i][k]+=f[i-1][((k>>1)^1)|(1<<m>>1)];
				//如果左边一格空着，就可以横着放一个 
			} 
			else f[i][k]=f[i-1][(k>>1)|(1<<m>>1)];
			//如果第i格没有被覆盖，则第i-m个格子一定是1，其他格子状态相同 
	cout<<T<<" "<<f[n*m-1][(1<<m)-1]<<endl;
}
int main() {
	int T,n,m;
	cin>>T; 
	rep(t,1,T){
		cin>>n;
		solve(t,n,4);
	}
	return 0;
}
