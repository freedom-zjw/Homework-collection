#include<iostream>
#include<string>
#include<stack>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int check(int n,string s){
	stack<char> Q;
	rep(i,0,n-1){
		if (s[i]=='(' || s[i]=='{' || s[i]=='[')Q.push(s[i]);
		else if (s[i]==')'){
			if (Q.empty()||Q.top()!='(')return 0;
			Q.pop();
		}
		else if (s[i]==']'){
			if (Q.empty()||Q.top()!='[')return 0;
			Q.pop();
		}
		else if (s[i]=='}'){
			if (Q.empty()||Q.top()!='{')return 0;
			Q.pop();
		}
	}
	if (!Q.empty())return 0;
	return 1;
}
int main(){
	int n;
	while (cin>>n){
		string s;
		cin>>s;
		if (check(n,s))cout<<"YES"<<endl;
		else cout<<"NO"<<endl;
	}
	return 0;
}