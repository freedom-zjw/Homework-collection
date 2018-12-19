#include<iostream>
#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<algorithm>
#include<cmath>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
    double x,y; 
}a[110];
struct edge{
    int x,y;
    double d;
}e[10010];
int n,k,len,fa[110];
bool cmp(const edge &A,const edge &B){
    return A.d<B.d;
} 
int getfa(int x){
    if (fa[x]!=x)fa[x]=getfa(fa[x]);
    return fa[x];
}
int main(){
    while (cin>>n>>k){
        len=0;
        rep(i,1,n){
            cin>>a[i].x>>a[i].y;
            fa[i]=i;
            rep (j,1,i-1){
                len++;
                e[len].x=i;
                e[len].y=j;
                e[len].d=(a[i].x-a[j].x)*(a[i].x-a[j].x)+(a[i].y-a[j].y)*(a[i].y-a[j].y);
                e[len].d=sqrt(e[len].d);
            } 
        }
        sort(e+1,e+1+len,cmp);
        rep(i,1,len){
            int fax=getfa(e[i].x);
            int fay=getfa(e[i].y);
            if (fax==fay)continue;
            fa[fax]=fay;
            if (--n==k-1){
                printf("%.2lf\n",e[i].d);
                break;
            }
        }
    }
    return 0; 
}                                 