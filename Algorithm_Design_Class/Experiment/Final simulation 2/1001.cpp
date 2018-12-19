#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int T,L;
	cin>>T;
    rep(t,1,T){
    	cin>>L;
    	rep(i,1,L/3)
    		rep(j,i,(L-i)/2){
		    	int k=L-i-j;
		    	if (i*i+j*j==k*k)
	    			cout<<i<<" "<<j<<" "<<k<<endl;
		    }
	    cout<<endl;
    }
    return 0;
}