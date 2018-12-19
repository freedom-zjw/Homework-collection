#include<iostream>
#include<cstdio>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int a[100010];
int main(){
   int n,m;
   while (scanf("%d%d",&n,&m)!=EOF){
   		if (n==0&&m==0)break;
   		rep(i,1,n)
   			scanf("%d",&a[i]);
        sort(a+1,a+1+n);
        int j=1;
        while (j<=n){
        	if (j>1)printf(" ");
        	printf("%d",a[j]); 
        	j+=m;
		}
		printf("\n");
   }
}                