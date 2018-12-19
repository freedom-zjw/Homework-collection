#include<iostream>
#include<queue>
#include<map>
#include<string>
#include<cstring> 
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	int s;
	string ans;
	node(){
		s=0;
		ans="";
	}
	node(int x,string str){
		s=x;
		ans=str;
	}
};
int states[11];
const int instruct[10][10]={
	0,0,0,0,0,0,0,0,0,0,
	4,1,2,4,5,0,0,0,0,0,
	6,1,2,3,4,5,6,0,0,0,
	4,2,3,5,6,0,0,0,0,0,
	6,1,2,4,5,7,8,0,0,0,
	9,1,2,3,4,5,6,7,8,9,
	6,2,3,5,6,8,9,0,0,0,
	4,4,5,7,8,0,0,0,0,0,
	6,4,5,6,7,8,9,0,0,0,
	4,5,6,8,9,0,0,0,0,0
};
void changstate(node &nextstate,int i,node no){
	nextstate=no;
	char c=char(i+48);
	nextstate.ans+=c; 
	rep(k,1,instruct[i][0])
		nextstate.s^=states[instruct[i][k]];
}
void bfs(string s){
	int nowstate = 0;
	for (int i=0;i<s.length();i++)
		if (s[i]=='b')nowstate|=states[i+1];
//	if (nowstate==0)return;
	queue<node>Q;
	map<int,int>f;
	Q.push(node(nowstate,""));
	while (!Q.empty()){
		node no=Q.front();
		Q.pop();
		for (int i=1;i<=9;i++){
			node nextstate;
			changstate(nextstate,i,no);
			if (nextstate.s==0){
				cout<<nextstate.ans<<endl;
				return;
			}
			//if (f[nextstate.s])continue;
			//f[nextstate.s]++;
			Q.push(nextstate);
		}
	}
}
int main(){
	int T;
	cin>>T;
	string s;
	rep(i,1,9)states[i]=(1<<(i-1));
	while(T--){
		cin>>s;
		bfs(s);
	} 
	return 0;
}