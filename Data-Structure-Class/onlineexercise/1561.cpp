#include<cstdio>
#include<cstring>
#include<iostream>
#include<cstdlib>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int p[10005],n,m,bo[1100001];
void pre(){ 
    for (int i=2;m<10000;i++){
        if (!bo[i]){
            int j=i;
            p[++m]=i;
            while (j<=1100000){
                bo[j]=1;
                j+=i;
            }
        }
    }
}
int main(){
    pre();
    cin>>n;
    cout<<p[n]<<endl;
    return 0;
}                          