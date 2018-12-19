#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
#include<algorithm>
#include<cmath>
using namespace std;
#define N 100010
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define sqr(x)((x)*(x))
const double INF=1e17;
struct node{
    double x,y;
}a[N],b[N],tmp[N];
int n;
double ans;
bool cmp1(const node &n1,const node &n2){
    if (n1.x<n2.x)return true;
    if (n1.x==n2.x&&n1.y<n2.y)return true;
    return false;
}
bool  cmp2(node n1,node n2){return n1.y<n2.y;}
void meger(int l,int m,int r){
    int k=l,k1=l,k2=m+1;
    while (k<=r){
        if ((k2>r)||(k1<=m&&a[k1].y<a[k2].y))tmp[k++]=a[k1++];
        else tmp[k++]=a[k2++];
    }
    rep(i,l,r)a[i]=tmp[i];
}
void find(int l,int r){
    if (l==r)return ;
    if (l+1==r){
        double tmp=sqr(a[l].x-a[r].x)+sqr(a[l].y-a[r].y);
        tmp=sqrt(tmp);
        if (tmp<ans)ans=tmp;
        return;
    }
    int mid=(l+r)/2,tot=0;
    double midl=(a[mid].x+a[mid+1].x)/2.0;
    find(l,mid);find(mid+1,r);
    meger(l,mid,r);
    rep(i,l,r)
        if (fabs(a[i].x-midl)<ans)b[++tot]=a[i];
    if(tot!=0){
        rep(i,1,tot-1)
            rep(j,i+1,i+6){
                if (j>tot)break;
                double tmp=sqr(b[i].x-b[j].x)+sqr(b[i].y-b[j].y);
                tmp=sqrt(tmp);
                if (tmp<ans)ans=tmp;
            }
    }
}
int main(){
        scanf("%d",&n);
        rep(i,1,n)scanf("%lf%lf",&a[i].x,&a[i].y);
        sort(a+1,a+1+n,cmp1);
        ans=INF;find(1,n);
        printf("%.2lf\n",ans);
}                                 