#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++) 
const long long md=1000000007;
struct matrix{
	long long a[3][3]; 
}init;
int n;
matrix jc(matrix a,matrix b){
	matrix c;
	memset(c.a,0,sizeof(c.a));
	rep(i,1,2)
		rep(j,1,2)
			rep(k,1,2)
				c.a[i][j]=(a.a[i][k]*b.a[k][j]%md+c.a[i][j])%md;
    return c;
}
matrix qm(matrix a,int b){
	matrix c;
	memset(c.a,0,sizeof(c.a));
	if (!b){
		c.a[1][1]=c.a[2][2]=1;
		c.a[1][2]=c.a[2][1]=0;
		return c;
	}
	//if (b==1)return a;
	c=qm(a,b/2);
	c=jc(c,c);
	if (b%2!=0)c=jc(c,a);
	return c;
}
int main(){
	init.a[1][1]=init.a[1][2]=init.a[2][1]=1;
	init.a[2][2]=0;
	while (cin>>n){
		if (!n){
			cout<<0<<endl;
			continue;
		}
		matrix ans=qm(init,n-1);
		cout<<(ans.a[1][1])%md<<endl;
	}
	return 0; 
} 