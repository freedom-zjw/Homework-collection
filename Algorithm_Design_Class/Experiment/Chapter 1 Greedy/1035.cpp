#include<iostream>
#include<string> 
#include<map>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int T,ans,n;
	string str,match_str;
	map<char,string>match;
	match['A']="T";
	match['T']="A";
	match['C']="G";
	match['G']="C";
	
	cin>>T;
	rep(t,1,T){
		map<string,int>f;
		ans=0;
		cin>>n;
		rep(i,1,n){
			cin>>str;
			match_str="";
			int len=str.length();
			rep(j,0,len-1)
				match_str+=match[str[j]];
			int &ret=f[match_str];
			if (!ret)f[str]++;
			else {
				ret--;
				ans++;
			}
		}
		cout<<ans<<endl;
	}
	return 0;
}