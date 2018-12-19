#include<iostream>
#include<cstring>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int T;
	string s;
	cin>>T;
	rep(t,1,T){
		cin>>s;
		int len=s.length();
		string str="";
		rep(i,0,len-1){
			str+=s[i];
			if (s[i]=='0'){
				sort(str.begin(),str.end(),greater<char>());
				cout<<str;
				str="";
			}
		}
		if (str!=""){
			sort(str.begin(),str.end(),greater<char>());
			cout<<str;
		}
		cout<<endl;
	}
	return 0;
}