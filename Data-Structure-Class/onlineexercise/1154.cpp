#include<iostream>
#include<algorithm>
using namespace std;
int main(){
    int T,n,a[1002];
    cin>>T;
    for (int tt=1;tt<=T;tt++){
        cin>>n;
        for (int i=1;i<=n;i++)
            cin>>a[i];
        sort(a+1,a+1+n);
        for (int i=1;i<=n;i++)
            cout<<a[i]<<endl;
    }
}                