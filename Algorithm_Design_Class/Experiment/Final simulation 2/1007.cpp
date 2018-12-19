#include<iostream>
using namespace std;
int gcd(int a,int b){  
    if (!b)return a;
    else return gcd(b,a%b);
} 
int main(){
	int a,b,n,x,y;
    while (cin>>a>>b>>n){
    	int d=gcd(a,n);
		if (b%d!=0)cout<<0<<endl;
    	else cout<<d<<endl;
    }
}