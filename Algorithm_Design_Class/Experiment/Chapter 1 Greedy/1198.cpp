#include<iostream>
#include<string>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
bool cmp(const string &s1,const string &s2){
	return s1+s2<s2+s1;
}
int main(){
	int T,n;
	string str[9];
	cin>>T;
	rep(t,1,T){
		cin>>n;
		rep(i,1,n)
			cin>>str[i];
		sort(str+1,str+1+n,cmp);
		rep(i,2,n)str[1]+=str[i];
		cout<<str[1]<<endl; 
	}
	return 0; 
}