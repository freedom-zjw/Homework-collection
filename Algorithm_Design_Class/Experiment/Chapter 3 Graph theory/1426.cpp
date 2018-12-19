#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N
struct node{
	int flag;
	int ch[10];
	void init(){
		memset(ch,0,sizeof(ch));
		flag=0;
	}
}t[101000];
int flag,m;
void insert(char *s){
	int p=0;
	int len=strlen(s);
	for (int i=0;i<len;i++){
		int x=s[i]-'0';
		if (!t[p].ch[x]){
			t[p].ch[x]=++m;
			t[t[p].ch[x]].init();
		}
		p=t[p].ch[x];
		if (t[p].flag==2){//当前点是某个字符串的终点 
			flag=0;
			return;
		}
		else if(t[p].flag==1&&i==len-1){
		//当前点是当前字符串的终点，且同时被另一个字符串经过 
			flag=0;
			return;
		}
		t[p].flag=1;
	}	
	t[p].flag=2;
}
int main(){
	int T,n;
	char s[15];
	cin>>T;
	rep(tt,1,T){
		cin>>n;
		flag=1;m=0;
	    t[0].init();
		rep(i,1,n){
			cin>>s;
			insert(s);
		}
		if (flag)cout<<"YES"<<endl;
		else cout<<"NO"<<endl;	
	} 
	return 0;
}