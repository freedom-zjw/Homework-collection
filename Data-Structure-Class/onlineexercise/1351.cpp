#include<iostream>
#include<string>
#include<cstdio>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--)
string ans;
int bo[1000010],a[3000010],b[3000010];
int main(){
	int cases=0,c,n;
	while(scanf("%d%d",&c,&n)!=EOF){
		ans="";cases++;
		int cnt=0;
		rep(i,1,n)scanf("%d",&a[i]);
		dto(i,n,1)
			if (bo[a[i]]!=cases){
				bo[a[i]]=cases;
		  		b[++cnt]=a[i];
			}	
		printf("%d\n",cnt);
		printf("%d",b[cnt]);
		dto(i,cnt-1,1)printf(" %d",b[i]);
		printf("\n"); 
	}
	return 0;
} 