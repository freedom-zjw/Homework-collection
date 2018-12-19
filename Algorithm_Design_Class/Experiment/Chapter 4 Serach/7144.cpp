#include<iostream>
using namespace std;
#define rep(i,u,v)for (int i=(u);i<=(v);i++) 
int a[101];
bool istriangle(int a, int b, int c){
    if(a+b>c&&a+c>b&&b+c>a&&a-b<c&&a-c<b&&b-c<a) return true;
    return false;
}
int main() {
    int T,n;
    cin>>T;
    while(T--){
        int ans=0;
        cin>>n;
        rep(i,1,n)cin>>a[i];
        rep(i,3,n)
        	rep(j,2,i-1)
                rep(k,1,j-1)
                	if(istriangle(a[i],a[j],a[k]))ans++;
		cout<<ans<<endl;
    }
}               