#include<iostream>
#include<vector>
#include<cstdio> 
using namespace std;
#define N 30010
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct node{
	vector<int>child;
	int minID,sizes;
}t[N];
int n,k,tot,ans,root,st,cnt=0,start_from_root,minID;
int bo[N];
vector<int>g[N];
void build_tree(int x){
	bo[x]=-1;
	t[x].child.clear();
	t[x].sizes=1;t[x].minID=x;
	int len=g[x].size();
	rep(i,0,len-1){
		int y=g[x][i];
		if (!bo[y]){
			build_tree(y);
			t[x].child.push_back(y);
			t[x].sizes+=t[y].sizes;
			t[x].minID=min(t[x].minID,t[y].minID);
		}
	}
	bo[x]=0;
}
void dfs(int x){
	//深搜寻找取哪一部分，start_from_root=1表示取切掉的边的靠近
	//根节点那部分start_from_root=0表示取切掉的边远离根结点那边 
	//st记录的是被切那条边远离根结点的那个结点 
	int len=t[x].child.size();
	rep(i,0,len-1){
		int y=t[x].child[i];
		int res=tot-t[y].sizes;
		if (t[y].sizes>=res){//取靠近根结点那部分 
			if (res<ans)continue;
			else if (res>ans||res==ans&&t[y].minID>t[st].minID){
				ans=res;
				start_from_root=1;
				minID=root;
				st=y;
			}
		} 
		else {//取远离根结点那部分 
			if (t[y].sizes<ans)continue;
			else if (t[y].sizes>ans||(!start_from_root&&t[y].minID<minID)){
				ans=t[y].sizes;
				start_from_root=0;
				minID=t[y].minID;
				st=y;
			}
		}
		dfs(y);
	}
}
void del(int x, int v){
  int len=t[x].child.size();
  rep(i,0,len-1){
    int y=t[x].child[i];
    del(y,v);
  }
  bo[x]=v;
}
int main(){
	int x,y;
	scanf("%d%d",&n,&k);	
	rep(i,1,n-1){
		scanf("%d%d",&x,&y);
		g[x].push_back(y);
		g[y].push_back(x);
	}
	rep(i,1,k-1){
		root=1; 
		//用还没被拿走的编号最小的金块为根节点建树 
		while (bo[root] && root<n)root++; 
		build_tree(root);
		/////////////////////////////// 
		
		//////////////////深搜寻找切哪条边 
		tot=t[root].sizes;
		ans=0;
		dfs(root); 
		/////////////////// 
		
		//**删去被拿走的金块 
		if (start_from_root)del(root,1),del(st,0);//删除靠近根那部分 
    	else del(st,1);//删除远离根那部分 
    	//**************** 
    	printf("%d ", ans);
    	cnt+=ans; 	  	
	}
	printf("%d\n",n-cnt);
	return 0;
}
