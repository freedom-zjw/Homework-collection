#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int check(int n,int base){
    int sum=0;
    while(n){
        sum+=n%base;
        n/=base;
    }    
    return sum;
}    
int main(){
    rep(i,1000,9999){
        int t=check(i,10);
        if(t==check(i,12)&&t==check(i,16))
             cout<<i<<endl;
    }  
    return 0;  
}                          