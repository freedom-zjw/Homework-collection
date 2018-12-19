#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int n;
	while (cin>>n,n){
		if (n&1)printf("Bob\n");
		else printf("Alice\n");
   }
	return 0;
	
}              