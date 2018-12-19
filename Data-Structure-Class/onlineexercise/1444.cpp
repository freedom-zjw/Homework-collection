#include<iostream>
#include<cstring>
#include<iostream>
#include<cstdlib>
#include<cmath>
#include<vector>
#include<queue>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 10001
int n;
int prim[1063],INDEX[N]; 
vector<int>g[1063];
int check(int x){
	rep(i,2,(int)sqrt(x))
		if (x%i==0)return 0;
	return 1;
	
}
void pre(){
	rep(i,1001,9999)
		if (check(i)){
			prim[++n]=i;
			INDEX[i]=n;
		}
	rep(i,1,n){
		rep(j,0,9){
			int x=prim[i];
			if (j%2){
				x=x/10;
				x=x*10+j;
				if (INDEX[x]!=0)g[i].push_back(INDEX[x]);
			}
			int p1=prim[i]%10;
			x=prim[i]/100;
			x=x*100+j*10+p1;
			if (INDEX[x]!=0)g[i].push_back(INDEX[x]);
			
			p1=prim[i]%100;
			x=prim[i]/1000;
			x=x*1000+j*100+p1;
			if (INDEX[x]!=0)g[i].push_back(INDEX[x]);
			
			if (j!=0){
				p1=prim[i]%1000;
				x=j*1000+p1;
				if (INDEX[x]!=0)g[i].push_back(INDEX[x]);
			}
		}
	}
}
struct node{
	int x,dep;
	node(){}
	node(int xx,int dd){
		x=xx;dep=dd;
	}
};
void bfs(int s,int t){
	queue<node>Q;
	Q.push(node(INDEX[s],0));
	int visit[1100];
	memset(visit,0,sizeof(visit));
	visit[INDEX[s]]=1;
	while (!Q.empty()){
		node no=Q.front();
		Q.pop();
		if (no.x==INDEX[t]){
			cout<<no.dep<<endl;
			return;
		}
		int len=g[no.x].size();
		rep(i,0,len-1){
			int y=g[no.x][i];
			if (!visit[y]){
				visit[y]=1;
				Q.push(node(y,no.dep+1));
			}
		}
		
	}
	cout<<"Impossible"<<endl;
}
int main(){
	pre();
	int T,s,t;
	cin>>T;
	rep(i,1,T){
		cin>>s>>t;
		if (!INDEX[s]||!INDEX[t])cout<<"Impossible"<<endl;
		else if (s==t)cout<<0<<endl;
		else bfs(s,t); 
	}
	return 0;
}