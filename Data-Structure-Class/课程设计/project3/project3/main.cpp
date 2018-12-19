#include<iostream>
#include<cstdio>
#include<conio.h>
#include"Expression.h"
using namespace std;
int main(){
	Tree tree,*tree1;
 	char E1[100],E2[100],P,V;
  	int switchs,c,switchs2;
   	bool run=true,run2=true;
    while(run){
		system("cls"); 
		cout<<"请选择要执行的功能:"<<endl;
		cout<<"   1.输入前缀表达式并输出中缀表达式."<<endl;
		cout<<"   2.构造复合表达式."<<endl;
		cout<<"   3.对前缀表达式求值."<<endl;
		cout<<"   4.对前缀表达式求偏导." << endl;
		cout<<"   5.退出."<<endl;
		cin>>switchs;
		switch(switchs){
  			case 5:
				run=false;
				break;
			case 1:
				cout<<"请输入前缀表达式，按回车结束输入:"<<endl;
				cin>>E1;
				tree.ReadExpr(E1);
				while(run2){
					cout<<"如有变量要赋值请输入1，否则输入2"<<endl;
					cin>>switchs2;
					if(switchs2==1){
						cout<<"请输入相关数据,如x赋值为1则输入 x 1 :"<<endl;
						getchar();
						scanf("%c%d",&V,&c);
						tree.Assign(V,c);
					}
					else run2=false;
				}
				tree.WriteExpr(E2);
				cout<<"中缀表达式: "<<E2<<endl;
				cout<<"是否合并常量:1是2否"<<endl;
				cin>>switchs2;
				if (switchs2==1){
					tree.MergeConst(E2);	
					cout<<"合并常量后: "<<E2<<endl;	
				}
				run2=true;
				break;
    		case 2:
				cout<<"请输入前缀表达式1 中间运算符 前缀表达式2:"<<endl;
				getchar();
				scanf("%s %c %s",E1,&P,E2);
				tree1=Tree::CompoundExpr(P,E1,E2);
    			while(run2){
					cout<<"如有变量要赋值请输入1，否则输入2"<<endl;
					cin>>switchs2;
					if(switchs2==1){
						cout<<"请输入相关数据,如x赋值为1则输入 x 1 :"<<endl;
						getchar();
						scanf("%c%d",&V,&c);
						tree1->Assign(V,c);
					}
					else run2=false;
				}
				tree1->WriteExpr(E1);
				cout<<"中缀表达式:"<<E1<<endl;
				tree1->Release();
				delete(tree1);
				run2=true;
				break;
			
			case 3:
				cout<<"请输入相关数据.前缀表达式"<<endl;
				cin>>E1;
				tree.ReadExpr(E1);
				tree.WriteExpr(E2);
				cout<<"中缀表达式为:";
				cout<<E2<<endl;
				cout<<"计算结果为:";
				cout<<Tree::Value(E1)<<endl;
				break;
			case 4:
				cout << "请输入前缀表达式，按回车结束输入:" << endl;
				cin >> E1;
				tree.ReadExpr(E1);
				tree.WriteExpr(E2);
				cout << "请输入对哪个变量求偏导:" << endl;
				getchar();
				cin >> V;
				cout << "求偏导前：" << E2 << endl;
				tree.Diff(E2,V);
				break;
			default:
				cout<<"你的输入无效!请重新输入."<<endl;
				break;
		}
		tree.Release();
		if(run) cout<<endl;
		cout<<"please input any key to continue"<<endl;
		getch();
	}
	return 0;
}
