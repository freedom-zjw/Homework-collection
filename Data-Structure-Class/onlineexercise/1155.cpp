#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v)  for (int i=(u);i<=(v);i++)
string s1="I can post the letter";
string s2="I can't post the letter";
int a[210][210],n;
int main(){
    int m,x,y;
    while (cin>>n&&n){
        cin>>m; 
        memset(a,0,sizeof(a));
        rep(i,1,m){
            cin>>x>>y;
            a[x][y]=1;
        }
        rep(k,0,n-1)
            rep(i,0,n-1)
                rep(j,0,n-1)
                    if (a[i][k]&&a[k][j])
                        a[i][j]=1;
        if (a[0][n-1])cout<<s1<<endl;
        else cout<<s2<<endl;    
    }
    return 0;
}                              