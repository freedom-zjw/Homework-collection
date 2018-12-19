#include <iostream>
#include <vector>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int calc(char s[], int x){
    int ans=0;
    for (int i=0;s[i];i++){
        ans=(ans*10+(s[i]-'0'))%x;
    }
    return ans;
}

int main(){
    int T,n,x,a[110];
    char s[410];
    cin>>T;
    rep(tt,1,T){
        cin>>n;
        rep(i,1,n)cin>>a[i];
        cin>>s;
        cout<<"(";
        rep(i,1,n){
            int ret=calc(s,a[i]);
            if (i!=n)cout<<ret<<",";
            else cout<<ret<<")\n";
        }
    }
    return 0;
}                           
