#include<iostream>
#include<cstring> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)  
int n,st,ed;
int a[3][110],v[110];
int main(){
	ios::sync_with_stdio(false);
	while (cin>>n){
		rep(i,1,n)cin>>a[1][i];
		rep(i,1,n)cin>>a[2][i];
		cin>>st>>ed;st++;ed++;
		memset(v,0,sizeof(v));
		rep(k,1,2){
			int cnt=1;
			rep(i,st,ed)v[ a[k][i] ]=k;
			rep(i,1,n)
				if (!(i>=st&&i<=ed)){
					while (v[ a[3-k][cnt] ]==k)cnt++;
					cout<<a[3-k][cnt++];
					if (i<n)cout<<" ";
				}
				else {
					cout<<a[k][i];
					if (i<n)cout<<" ";
				}
			cout<<endl;		
		}
	}
	return 0;
}