#ifndef _EXPRESSION_H_
#define _EXPRESSION_H_
//--------------------    树结点类
class TreeNode{
	public:
        TreeNode(char _data,TreeNode* _left,TreeNode* _right);
        ~TreeNode();
        char GetData();//获取该结点的值 
        void SetData(char _data);//设置该结点的值 
        void SetLeft(TreeNode* _left);//设置左孩子 
        void SetRight(TreeNode* _right);//设置右孩子 
        TreeNode* GetLeft();//获取左孩子 
        TreeNode* GetRight();//获取右孩子 
	private:
        char Data; 
        TreeNode*left,*right;
};

//--------------------二叉树类  
class Tree{ 
	public:
        Tree();
        ~Tree();
        TreeNode*GetRoot();//获取根节点 
        void SetRoot(TreeNode* _root);//设置根节点 
        void ReadExpr(char* E);//用前缀表达式E建树 
        void WriteExpr(char* E);//得到中缀表达式储存在E 
        void MergeConst(char *E);//合并常量后将中缀表达式储存在E 
		void Diff(char *E, char V);//对E求V的偏导数
        void Assign(char v,int c);//将变量V赋值成C 
        static int Value(char* E);//对E求值 
        static Tree* CompoundExpr(char p,char* E1,char* E2);//构造复合表达式(E1)P(E2) 
        void Release();//删除本树 

	private:
        void DeleteTree(TreeNode* &p);//删除p为根结点的子树 
        void ReadExprRecursion(TreeNode* &p,char* E);
        void WriteExprRecursion(TreeNode* p,char* E);
        void AssignRecursion(TreeNode* p,char v,int c);
        int ValueRecursion(TreeNode* p);
        void Conbination(char *E,int i);//合并操作 
        int Priority(char c1,char c2);
        bool IsOperator(char c);
        bool IsNumber(char c);//判断是否是数字 
        int Calculate(int a,char op,int b);
        TreeNode *root;//根结点 
        int Expr_i,len;
};

#endif
