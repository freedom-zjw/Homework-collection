#include<iostream>
#include<string>
#include<map>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n,m,ans;
string s;
map<string,int>f;
void dfs(int x,string str){
	if (x==m){
		str+="\0";
		int &ret=f[str];
		if (!ret)ret=1,ans++;
		return;
	}
	if (s[x]!='?')dfs(x+1,str+s[x]);
	else {
		dfs(x+1,str+"0");
		dfs(x+1,str+"1");
	}
}
int main(){
	while (cin>>m>>n){
		if (!n && !m)break;
		ans=0;f.clear();
		rep(i,1,n){
			cin>>s;
			dfs(0,"");
		}
		cout<<ans<<endl;
	}
	return 0;
}