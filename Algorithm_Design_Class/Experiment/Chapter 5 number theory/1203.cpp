#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--)
int a[15],b[15];
int main(){
	ios::sync_with_stdio(false);
	int T,n;
	char s[15];
	cin>>T;
	rep(tt,1,T){
		cin>>s;
		n=strlen(s);
		rep(i,0,n-1)a[i]=s[n-1-i]-'0';
		int carry=0;
		rep(i,0,n-1){
			rep(j,0,9){
				int tmp=carry;
				b[i]=j;
				rep(x,0,i)
					rep(y,0,i){
						if (x+y>i)break;
						int z=i-x-y;
						tmp+=b[x]*b[y]*b[z];
					}
				if (tmp%10==a[i]){
					carry=tmp/10;
					break;
				}
			}
		}
		while (b[n-1]==0&&n-1>0)n--; 
		dto(i,n-1,0)cout<<b[i];
		cout<<endl;
	}
	return 0;
}