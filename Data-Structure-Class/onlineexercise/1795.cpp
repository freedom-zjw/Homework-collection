#include <cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define sqr(x)  ((x)*(x))
int S(int a, int b){
	int l =sqr(a-30) + sqr(b-30);
	if (l < 400)
		return 1;
	return 0;
}
int M(int a, int b){
	int l = sqr(a-100) + sqr(b-30);;
	if (l < 100)
		return 2;
	return 0;
}
int L(int a, int b){
	int l = sqr(a-170) + sqr(b-30);;
	if (l < 25)
		return 3;
	return 0;
}
int main(){
	int t;
	cin>>t;
	while (t--){
		int n;
		cin>>n;
		int ans = 0;
		rep(i,1,n){
			int a, b;
			cin>>a>>b;
			ans += S(a, b) + M(a, b) + L(a, b);
		}
		cout<<ans<<endl;
	}
	return 0;
}                                