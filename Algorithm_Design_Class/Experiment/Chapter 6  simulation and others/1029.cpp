#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--) 
struct node{
	int a[100];
	int len;
	node(){}
	node operator + (node &B){
		int len3=len>B.len?len:B.len;
		node n0;
		memset(n0.a,0,sizeof(n0.a)); 
		n0.len=len3;
		rep(i,1,len3){
			n0.a[i]+=a[i]+B.a[i];
			n0.a[i+1]+=n0.a[i]/10;
			n0.a[i]%=10;
		}
		while (n0.a[n0.len+1]){
			n0.len++;
			a[n0.len+1]+=n0.a[n0.len]/10;
			n0.a[n0.len]%=10;
		}
		return n0;
	}
}f[105],one;
int m,d;
int main(){
	ios::sync_with_stdio(false);
	f[0].a[1]=1;f[0].len=1;
	one.a[1]=1;one.len=1;
	while (cin>>m>>d){
		if (!m && !d)break;
		rep(i,1,m-1)f[i]=f[i-1]+one;
		rep(i,m,d)f[i]=f[i-1]+f[i-m];
		dto(i,f[d].len,1)cout<<f[d].a[i];
		cout<<endl;	
	}
	return 0;
}