#ifndef _ADJMULTILISTGRAPH_H_
#define _ADJMULTILISTGRAPH_H_
#include<string>
#include<map> 
#include<vector>
using std::map;
using std::vector;
using std::string;
class PrintTree2{//输出树的类 
	public:
		string name,blank;
		int d,dep; 
		PrintTree2 *next;
}; 
class AdjMultiListGraph{//邻接多重表建图的类 
	public:
		AdjMultiListGraph();
		~AdjMultiListGraph();
		void ReadGraph();//读图 
		void dfs();//递归版深搜 
		void non_recursive_dfs();//非递归版深搜 
		void bfs();//广搜 
	private:
		void insert(int x,int y,int d);//建边 
		void DfsRecursion(int x,int dep,int d,int fa);//深搜过程 
		void insertPrintTree(int x,int dep,int d,string fa);//插入输出树的一条边 
		void DeletePrintTree(PrintTree2 *p);//删除树 
		void print_tree();//输出树 
		void printvisitvec(); //输出先根遍历和后根遍历 
		void preorder(int x);//先根遍历  
		void postorder(int x);//后根遍历 
		vector<int> visitorder;//搜索时访问的点顺序 
		int n,m,st,ncount,mcount;//n个点，m条边 
		string startpoint;//起始点 
		typedef struct edgenode{//存储边的结构体 
			int x,y,d;
			edgenode *xnext;
			edgenode *ynext;
		}*edge;
		struct node{//bfs队列中和非递归stack中点信息结构 
			int x,dep;
			node(){}
			node(int _x,int _dep){
				x=_x;dep=_dep;
			}
		};
		vector<edge>tree[50];//存储输出树 
		int visit[50];//判断点是否访问 
		edge firstedge[50];//每个点第一条边 
		PrintTree2 *head;//输出树的头结点 
		map<string,int> f;//hash点 
		map<int,string> name; //逆hash 
};
#endif
