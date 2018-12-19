#include<iostream>
#include<algorithm>
#include<cmath> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int countA,countB,maxA,maxB,ans;
	cin>>countA>>countB>>maxA>>maxB;
	if (maxA==0 && maxB==0)cout<<0<<endl;
	else if (maxA==0)cout<<min(countB,maxB)<<endl;
	else if (maxB==0)cout<<min(countA,maxA)<<endl;
	else { 
		int blocka=ceil((double)countA/maxA);
		int blockb=ceil((double)countB/maxB);
		ans=0; 
		if(countA<blockb)ans+=countA+(countA+1)*maxB;
		else if (countB<blocka)ans+=countB+(countB+1)*maxA;
		else ans=countA+countB;
		cout<<ans<<endl;
	}
	return 0;
}