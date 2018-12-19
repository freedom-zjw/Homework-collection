#include<iostream>
#include<cstdio>
#include<queue>
#include<map>
using namespace std;
#define rep(i,u,v) for(int i=(u);i<=(v);i++)
struct node{
	string sta;
	int step;
	node(){}
	node(string ss,int d){
		sta=ss;step=d;
	}
};
int n,wnum,bnum,a[15];
string orista="";
queue<node>Q;
map<string,int>f;
int check(string sta){
	int l=0,r=n-1,cnt1=0,cnt2=0;
	while (l<n&&sta[l]=='1')l++,cnt1++;
	if (cnt1!=wnum)return 0; 
	while (r>=0&&sta[r]=='2')r--,cnt2++;
	if (cnt2!=bnum)return 0;
	return 1;
}
void bfs(){
	if (check(orista)){
		printf("0\n");
		return;
	}
	Q.push(node(orista,0));
	f[orista]=1;
	while (!Q.empty()){
		node n0=Q.front();Q.pop();
		int l=0,r=n-1;
		while (l<n&&n0.sta[l]=='1')l++;
		while (r>=0&&n0.sta[r]=='2')r--;
		rep(i,l,r){
			string nextsta=n0.sta;
			if (n0.sta[i]=='1'){//白子  
				if (i-1>=0&&n0.sta[i-1]=='0'){//可以向左一步 
					nextsta[i-1]='1';
					nextsta[i]='0';
				}
				else if (i-2>=0&&n0.sta[i-2]=='0'){//可以跳一步
 					nextsta[i-2]='1';
					nextsta[i]='0';
				}
				else continue;
			}
			else if (n0.sta[i]=='2'){
				if (i+1<n&&n0.sta[i+1]=='0'){//可以向右一步 
					nextsta[i+1]='2';
					nextsta[i]='0';
				}
				else if (i+2<n&&n0.sta[i+2]=='0'){//可以跳一步
 					nextsta[i+2]='2';
					nextsta[i]='0';
				}
				else continue;
			}
			int &ret=f[nextsta];
			if (ret!=0)continue;
			ret++;
			if (check(nextsta)){
				printf("%d\n",n0.step+1);
				return;
			}
			Q.push(node(nextsta,n0.step+1));
		}
	}
}
int main(){
	scanf("%d",&n);
	rep(i,1,n){
		scanf("%d",&a[i]);
		if (a[i]==1)wnum++;
		else if (a[i]==2)bnum++;
		char x=char(a[i]+48);
		orista+=x; 
	}
	bfs();
	return 0;
}