#include<iostream>
#include<queue>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	int weight;
	int idx;
}a[102];
int n,m;
deque<node> list;
priority_queue<int>Q;
int main(){
	int T;
	cin>>T;
	while (T--){
		cin>>n>>m;
		int ans=0;
		while (!Q.empty())Q.pop();
		while (!list.empty())list.pop_front();
		rep(i,1,n){
			cin>>a[i].weight;
			a[i].idx=i-1;
			list.push_back(a[i]);
			Q.push(a[i].weight);
		}
		while (1){
			node no=list.front();
			list.pop_front();
			if (no.weight==Q.top()){
				Q.pop();
				ans++;
				if (no.idx==m)break;
			}
			else list.push_back(no);
		}
		cout<<ans<<endl;
	}
	return 0;
}