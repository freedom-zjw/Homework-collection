#ifndef _ADJLISTGRAPH_H_
#define _ADJLISTGRAPH_H_
#include<string>
#include<map> 
using std::map;
using std::string;
class PrintTree{//输出树的类 
	public:
		string name,blank;
		int d,dep; 
		PrintTree *next;
}; 
class AdjListGraph{//邻接表建图的类 
	public:
		AdjListGraph();
		~AdjListGraph();
		void ReadGraph();//读图 
		void dfs();//递归版深搜 
		void bfs();//广搜 
		void non_recursive_dfs();//非递归版深搜 
		void print_tree();//输出树 
	private:
		void DfsRecursion(int x,int dep,int d,int fa);//递归版深搜 过程 
		void insert(int x,int y,int d);//建边 
		void insertPrintTree(int x,int dep,int d,string fa);//插入树的边 
		void DeletePrintTree(PrintTree *p);//删除树 
		int n,m,st,ncount,mcount;//n个点，m条边 
		string startpoint;//起点 
		struct edgenode{//边信息结点 
			int y,d,next;
		}e[2000];
		struct node{//bfs队列中和非递归stack中点信息结构 
			int x,dep;
			node(){}
			node(int _x,int _dep){
				x=_x;dep=_dep;
			}
		};
		int visit[50],firstedge[2000];//判断点是否访问 ，每个点第一条边  
		PrintTree *head;//输出树的头结点 
		map<string,int> f;//hash点 
		map<int,string> name; //逆hash 
};
#endif
