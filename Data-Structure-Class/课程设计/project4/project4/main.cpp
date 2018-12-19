#include"AdjListGraph.h"
#include"AdjMultiListGraph.h" 
#include<iostream>
#include<conio.h>
using namespace std;
int main(){
	while (1){
  loop1:system("cls");
		cout<<"请选择"<<endl; 
		cout<<"1.邻接表建图"<<endl;
		cout<<"2.邻接多重表建图"<<endl;
		cout<<"3.退出"<<endl;
		int choice;
		cin>>choice;
  loop2:switch(choice){
			case 1:
				{
					AdjListGraph G;
					G.ReadGraph();		 
				loop3:system("cls"); 
					cout<<"图读取成功"<<endl; 
					cout<<"请选择"<<endl; 
					cout<<"1.depth-first spanning tree with recursive algorithm"<<endl;
					cout<<"2.depth-first spanning tree with non-recursive algorithm"<<endl;
					cout<<"3.breadth-first spanning tree"<<endl;
					cout<<"4.返回"<<endl; 
					int choice2;
					cin>>choice2;
			  loop4:switch(choice2){
						case 1:
							G.dfs();
							G.print_tree();
							system("pause");
							goto loop3; 
							break;
						case 2:
							G.non_recursive_dfs();
							G.print_tree();
							system("pause");
							goto loop3;
							break;
						case 3:
							G.bfs();
							G.print_tree();
							system("pause");
							goto loop3; 
							break;
						case 4:
							goto loop1; 
							break;
						default:
							cout<<"输入不正确，请重新输入:"<<endl; 
							cin>>choice2;
							goto loop4;
							break; 
					}
				}
				break;
			case 2:
				{			  
					AdjMultiListGraph G;
					G.ReadGraph();		  
			  loop5:system("cls");
					cout<<"图读取成功"<<endl; 
					cout<<"请选择"<<endl; 
					cout<<"1.depth-first spanning tree with recursive algorithm"<<endl;
					cout<<"2.depth-first spanning tree with non-recursive algorithm"<<endl;
					cout<<"3.breadth-first spanning tree"<<endl;
					cout<<"4.返回"<<endl; 
					int choice3;
					cin>>choice3;
			  loop6:switch(choice3){
						case 1:
							G.dfs();
							system("pause");
							goto loop5; 
							break;
						case 2:
							G.non_recursive_dfs();
							system("pause");
							goto loop5;
							break;
						case 3:
							G.bfs();
							system("pause");
							goto loop5; 
							break;
						case 4:
							goto loop1; 
							break;
						default:
							cout<<"输入不正确，请重新输入:"<<endl; 
							cin>>choice3;
							goto loop6;
							break; 
					}
				}
				break;
			case 3:
				system("cls");
				cout<<"谢谢使用"<<endl;
				cout<<"put any key to continue"<<endl;
				getch();
				return 0;
				break;
			default:
				cout<<"输入不正确，请重新输入:"<<endl; 
				cin>>choice;
				goto loop2;
				break; 
		}
	}
	return 0;
} 
