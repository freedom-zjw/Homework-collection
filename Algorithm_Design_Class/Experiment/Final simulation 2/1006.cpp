#include<cstdio>
#include<cstring>
using namespace std;
#define lowbit(x)  ((x)&(-x))
#define N 100001
int n;
long long c[N];
void add(int x){
	for (;x<=n;x+=lowbit(x))c[x]++;
}
long long getsum(int x){
	int sum=0;
	for (;x>0;x-=lowbit(x))sum+=c[x];
	return sum;
}
int main(){
	while (scanf("%d",&n)!=EOF){
		memset(c,0,sizeof(c));
		long long ans=0;
		int x;
		for (int i=1;i<=n;i++){
			scanf("%d",&x);
			ans+=getsum(n)-getsum(x);
			add(x);
		}
		printf("%lld\n",ans);
	}
	return 0;
}