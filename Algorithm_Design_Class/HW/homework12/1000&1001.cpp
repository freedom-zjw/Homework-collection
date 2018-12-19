#include<iostream>
#include<cstdio>
#include<algorithm>
#include<set>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)  
int n,Q,ans;
int a[1000010];
multiset<int>s; 
int main(){
	while (scanf("%d%d",&n,&Q)!=EOF){
		rep(i,1,n)scanf("%d",&a[i]);
		sort(a+1,a+1+n,greater<int>());
		s.clear();ans=0;
		multiset<int> :: iterator it; 
		rep(i,1,n){
			if (!s.empty())it=s.lower_bound(a[i]);
			if (s.empty() || it==s.end()){
				ans++;
				if (Q-a[i]>0)s.insert(Q-a[i]);
			}
			else {
				int x=(*it)-a[i];
				s.erase(it);
				if (x>0)s.insert(x);
			}
		}
		printf("%d\n",ans);
	}
}