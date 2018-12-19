#include<iostream>
#include<cstdio> 
#include<cmath>
using namespace std;
typedef long long ll;
ll extend_gcd(ll a,ll b,ll &x,ll &y){
	if (!b){
		x=1;y=0;
		return a;
	}
	long long d=extend_gcd(b,a%b,x,y);
	long long tmp=x;
	x=y;
	y=tmp-(a/b)*y;
	return d;
}
int main(){
	long long gcd,n,costa,pa,costb,pb,x,y;
	int T=0;
	while (cin>>n,n>0){
		cin>>costa>>pa>>costb>>pb;
		gcd=extend_gcd(pa,pb,x,y);
		if ((!pa&&!pb)||n%gcd){
			printf("Data set %d: cannot be flown\n",++T);
			continue;
		}
		x=x*(n/gcd);y=y*(n/gcd);
		int kmin=(int)ceil((double)(-x)*gcd/pb);
		int kmax=(int)floor((double)(y)*gcd/pa);
		ll k;
		ll tmp=pb*costa-pa*costb;
		if (tmp>0)k=kmin;
		else k=kmax;
		x=x+pb/gcd*k;y=y-pa/gcd*k;
	    printf("Data set %d: %lld aircraft A, %lld aircraft B\n",++T,x,y);
	}
	return 0;
}