#include<algorithm>
#include<iostream>
using namespace std;
#define rep(i,u,v) for(int i=(u);i<=(v);i++)
#define N 100001
double a[N],ans;
int main(){
	int n;
	while (scanf("%d",&n)!=EOF){
		rep(i,1,n)scanf("%lf",&a[i]);
		sort(a+1,a+1+n);
		ans=a[2]-a[1];
		rep(i,3,n)ans=min(ans,a[i]-a[i-1]);
		printf("%.6lf\n",ans);
	}
	return 0;
} 
