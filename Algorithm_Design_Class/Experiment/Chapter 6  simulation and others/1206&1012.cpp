#include<iostream>
#include<cstdio>
#include<cmath> 
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	double x,y;
	node(){}
}p[100];
int n;
bool cmp(const node &n1,const node &n2){return n1.x<n2.x;} 
double distances(node &n1, node &n2){ 
	double x=n1.x-n2.x,y=n1.y-n2.y;
    return sqrt(x*x+y*y);
}  

void calc(node &n1,node &n2){
	n++;
	double len=distances(n1,n2);
	double mid=sqrt(4.0-len*len/4);
	double midx=(n1.x+n2.x)/2.0;
	double midy=(n1.y+n2.y)/2.0;
	double sin_=(n1.y-n2.y)/len;
	double cos_=(n2.x-n1.x)/len;
	p[n].x=sin_*mid+midx;
	p[n].y=cos_*mid+midy;
}
int main(){
	int T;
	cin>>T;
	rep(t,1,T){
		cin>>n;
		if (!n)break;
		rep(i,1,n){
			cin>>p[i].x;
			p[i].y=1.0;
		}
		sort(p+1,p+1+n,cmp);
		int l=1,r=n;
		while (r-l){
			rep(i,l,r-1)calc(p[i],p[i+1]);
			l=r+1;r=n;
		}
		printf("%d: %.4lf %.4lf\n",t,p[l].x,p[l].y);
		//printf("%.4lf %.4lf\n",p[l].x,p[l].y);
	}
	return 0;
}