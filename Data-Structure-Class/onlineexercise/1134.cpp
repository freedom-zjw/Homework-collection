#include<cstdio>
#include<iostream>
#include<cstring>
#include<cstdlib>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 100010
struct node{
	int now,need;
}a[10010];
int n;
long long s;
bool cmp(const node &n1,const node &n2){
	return n1.need<n2.need||(n1.need==n2.need&&n1.now>n2.now);
}
int main(){
	while (scanf("%d%I64d",&n,&s)!=EOF){
		if (n==0&&s==0)break;
		rep(i,1,n)
			scanf("%d%d",&a[i].now,&a[i].need);
		sort(a+1,a+1+n,cmp);
		bool flag=true;
		rep(i,1,n){
			if (!(s>=a[i].need)){
				flag=false;
				break;
			}
			else s+=a[i].now;
		}
		if (flag)printf("YES\n");
		else printf("NO\n");
	}
	return 0;
}