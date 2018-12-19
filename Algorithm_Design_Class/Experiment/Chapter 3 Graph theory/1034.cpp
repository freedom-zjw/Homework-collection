#include<iostream>
#include<vector>
#include<cstring>
#include<set>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int n,m;
int widths[110];
vector<int> g[110];
set<int> root; 
void Initialize(){//初始化 
    root.clear();
    memset(widths,0,sizeof(widths));
    rep(i,1,n){
        g[i].clear();
        root.insert(i);
    }
}
void dfs(int x,int dep,int &ansdep,int &answidths){
    widths[dep]++;
    ansdep=dep>ansdep?dep:ansdep;
    answidths=answidths>widths[dep]?answidths:widths[dep];
    int len=g[x].size();
    rep(i,0,len-1)
        	dfs(g[x][i],dep+1,ansdep,answidths);
}
int work(){
    int x,y;
    int flag=1;
    rep(i,1,m){
            cin>>x>>y;
            g[x].push_back(y); 
            if (root.find(y)==root.end()||x==y)flag=0;//有两条入边或自环 
            else root.erase(y);
        }
    if (!flag)return 0;
    set<int>::iterator it;
    if (root.empty())return 0;//整体为一个大环。 
    int ansdep=0,answidths=0;
    for (it=root.begin();it!=root.end();it++)//遍历每棵树求解 
        dfs(*it,0,ansdep,answidths);
    cout<<ansdep<<" "<<answidths<<endl;
    return 1;
}
int main(){
    while (cin>>n>>m,n>0){
        Initialize();
        if (!work())cout<<"INVALID"<<endl;      
    }
    return 0;
}                          