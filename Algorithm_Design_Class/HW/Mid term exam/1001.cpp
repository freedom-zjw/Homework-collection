
//ÆßĞÇµÆĞøÃü
#include<iostream>
#include<algorithm>
#include<cstring>
using namespace std;
#define rep(i,u,v)  for (int i=(u); i<=(v); i++)
struct node{
	int x,y;
}a[21];
int n, f[21];
bool cmp(const node &n1,const node &n2){
	return n1.x<n2.x|| n1.x==n2.x&&n1.y<n2.y;
}
int main(){
	int T;
	cin>>T;
	rep(tt,1,T){
		cin>>n;
		rep(i,1,n)cin>>a[i].x>>a[i].y;
		sort(a+1,a+1+n,cmp);
		memset(f,0,sizeof(f));
		int ans=1;	
		rep(i,1,n){
			f[i]=1;
			rep(j,1,i-1)
				if (a[i].x>a[j].x&& a[i].y>a[j].y){
					f[i]=max(f[i],f[j]+1);
					ans=max(ans,f[i]);
				}
		}
		cout<<ans<<endl;
	}
	return 0;
} 