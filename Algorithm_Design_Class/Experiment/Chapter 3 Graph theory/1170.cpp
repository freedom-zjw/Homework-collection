#include<iostream>
#include<cstring>
#include<algorithm>
#include<map>
#include<vector>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n,m,d;
int root[1002],sizes[1002];
map<string,int>num;
map<int,string>name;
vector<int>g[1002];
vector<pair<string,int> >ans;
bool cmp(pair<string,int>n1,pair<string,int>n2){
	return n1.second>n2.second || 
	       n1.second==n2.second && n1.first<n2.first;
}
void dfs(int now,int x,int dai){
	sizes[now]=0;
	for (int i=0;i<g[now].size();i++){
		if (dai+1==d)sizes[x]++;
		else {
			int y=g[now][i];
			dfs(y,x,dai+1);
		}
	}
}
int main(){
	int T,k;
	cin>>T;
	string s1,s2;
	rep(tt,1,T){
		cin>>n>>d;
		num.clear();name.clear();
		ans.clear();m=0;
		memset(root,0,sizeof(root));
		rep(i,1,n){
			cin>>s1>>k;
			int &x=num[s1];
			if (!x){
				x=++m;
				name[x]=s1;
				g[x].clear();
			}
			rep(j,1,k){
				cin>>s2;
				int &y=num[s2];
				if (!y){
					y=++m;root[y]=1;
					name[y]=s2;
					g[y].clear();	
				}
				g[x].push_back(y);
			}
		}
		rep(i,1,m){
			dfs(i,i,0);
			if (sizes[i]>0)
				ans.push_back(make_pair(name[i],sizes[i]));
		}
		if (tt>1)cout<<endl;	
		cout<<"Tree "<<tt<<":"<<endl;
		sort(ans.begin(),ans.end(),cmp);
        for (int i=0;i<ans.size();i++){
        	if (i>2&&ans[i].second!=ans[i-1].second)break;
        	cout<<ans[i].first<<" "<<ans[i].second<<endl;
        }
	}
	return 0;
} 