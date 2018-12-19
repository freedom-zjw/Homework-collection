#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
    int T,N,M,C,P,S;
    cin>>T;
    while (T--){
        cin>>N>>M>>C>>P>>S;
        rep(i,1,S){
            if (M>=P&&C*(S-i+1)>=P){
                N+=M/P;
                M%=P;
            }
            M+=N*C;
        }
        cout << M << endl;
    }
    return 0;
}