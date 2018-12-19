#include<iostream>
#include<cstdio>
#include<algorithm>
#include<set>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 300010
struct item{
	int m,v;
}a[N];
bool cmp(const item &A,const item &B){
	return A.v>B.v;
}
multiset<int>bag;
int n,k,x;
long long ans=0;
int main(){
	scanf("%d%d",&n,&k);
	rep(i,1,n)
		scanf("%d%d",&a[i].m,&a[i].v);
	sort(a+1,a+1+n,cmp);
	rep(i,1,k){
		scanf("%d",&x);
		bag.insert(x);
	}
	rep(i,1,n){
		multiset<int>::iterator it;
		it=bag.lower_bound(a[i].m);
		if (it==bag.end())continue;
		ans+=a[i].v;
		bag.erase(it);
	}
	cout<<ans<<endl; 
	return 0;
}