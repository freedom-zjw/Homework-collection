#include<iostream>
#include<string>
#include<algorithm>
#include<cmath> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
bool cmp(const string &s1,const string &s2){
	return s1.length()<s2.length(); 
}
int n,k; 
int main(){
	int T=0;
	string s[1001];
	while (cin>>n>>k,n>0){
		if (++T>1)cout<<endl;
		cout<<"Case "<<T<<": ";
		rep(i,1,n)cin>>s[i];
		sort(s+1,s+1+n,cmp);
	    int sum=0,minn=100,maxx=0,cnt=0,flag=1;
	    rep(i,1,n){
	    	cnt++;
	    	sum+=(int)s[i].size();
	    	minn=min(minn,(int)s[i].size());
	    	maxx=max(maxx,(int)s[i].size());
	    	if (cnt==k){
	    		if (!(sum-minn*k<=2*k && maxx*k-sum<=2*k)){
	    			flag=0;
	    			break;
				}
				cnt=sum=0;
				maxx=0;minn=100;
			}
		}
		if (flag)cout<<"yes"<<endl;
		else cout<<"no"<<endl;
	}
	return 0;
} 