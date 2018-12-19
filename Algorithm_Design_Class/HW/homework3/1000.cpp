#include<iostream>
#include<queue>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct Hnode{
   int val; 
}; 
bool operator <(const Hnode &A,const Hnode &B){
        return A.val>B.val;
}
priority_queue<Hnode>Q;
int n;
int ans;
void solve(){
    while (Q.size()>1){
        Hnode n1=Q.top();Q.pop();
        Hnode n2=Q.top();Q.pop();
        ans+=n1.val+n2.val;
        n1.val+=n2.val;
        Q.push(n1);
    }
}
int main(){
    char x;
    cin>>n;
    ans=0;
    Hnode t;
    rep(i,1,n){
        cin>>x>>t.val;      
        Q.push(t); 
    }
    solve();
    cout<<ans<<endl;
    return 0;
}                       