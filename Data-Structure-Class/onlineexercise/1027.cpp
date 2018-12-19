#include<iostream>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	string BBS_ID,IP_Address;
}a[22];
struct node2{
	string p1,p2;
}b[11];
int n,m;
bool cmp(const node2 &n1,const node2 &n2){
	return n1.p1<n2.p1;
}
int main(){
	while (cin>>n,n>0){
		m=0;
		rep(i,1,n){
			cin>>a[i].BBS_ID>>a[i].IP_Address;
			rep(j,1,i-1)
				if (a[i].IP_Address==a[j].IP_Address){
					b[++m].p1=a[j].BBS_ID;
					b[m].p2=a[i].BBS_ID;
					break;
				}
		}
		sort(b+1,b+1+m,cmp);
		rep(i,1,n/2)
			cout<<b[i].p2<<" is the MaJia of "<<b[i].p1<<endl;
		cout<<endl;
	}
	return 0;
}