#include<iostream>
#include<cstdlib>
#include<algorithm>
using namespace std;
int check(int n,int m){
	if (n<m)swap(n,m);
	if (n%m==0)return 1;
	if (n/m>=2)return 1;
	return !check(n-m,m); 
}
int main(){
	int n,m;
	while (cin>>n>>m){
		if (!n && !m)break;
		if (check(n,m))cout<<"Singa wins"<<endl;
		else cout<<"Suny wins"<<endl;
	}
	return 0;
}