#include"main_control.h"
#include"polynomial.h"
#include<iostream>
using namespace std;
void MainControl::How_to_use(){
	cout<<"                欢迎使用该简便一元多项式计算器               "<<endl;
	cout<<"          ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "<<endl;
	cout<<"           操作说明：                                        "<<endl;
	cout<<"                   1.建立多项式a和b.                         "<<endl;
	cout<<"                   2.输出多项式a+b.                          "<<endl;
	cout<<"                   3.输出多项式a-b.                          "<<endl;
	cout<<"                   4.输出多项式a*b.                          "<<endl;
	cout<<"                   5.对多项式a求导.                          "<<endl;
	cout<<"                   6.对多项式b求导.                          "<<endl;
	cout<<"                   7.计算各多项式在x处的值.                  "<<endl;
	cout<<"                   8.退出.                                   "<<endl;
	cout<<"          ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "<<endl;
}
void MainControl::start_work(){
	How_to_use();
	polynomial* ret[7];
	for (int i=0;i<5;i++)
		ret[i]=NULL;
	int n,x;
	for (int type=0;;type=0){
        cin>>type;		
		if (ret[0]||ret[1]){
			system("cls");
			How_to_use();
			cout<<"多项式a：";print(ret[0]);cout<<endl;
			cout<<"多项式b：";print(ret[1]);cout<<endl;
			cout<<endl;
		}
		if (type==1){//建立多项式a和b 
			for (int i=0;i<7;i++)
				if (ret[i])del(ret[i]);	
			cout<<"请输入多项式a的项数:"<<endl;
	        cin>>n;	        
	        ret[0]=creatpolyn(n);//建立多项式a
	        
	        cout<<"请输入多项式b的项数:"<<endl;
	        cin>>n;
	        ret[1]=creatpolyn(n);//建立多项式b
	        
	        system("cls");
			How_to_use();
			cout<<"多项式a：";print(ret[0]);cout<<endl;
			cout<<"多项式b：";print(ret[1]);cout<<endl;
			cout<<endl;
		}
		else if (type==2){//加法 
			if(!ret[2])ret[2]=add(ret[0],ret[1]);
			cout<<"多项式a+b:\n(";print(ret[0]);cout<<")+(";print(ret[1]);cout<<")=(";print(ret[2]);cout<<")"<<endl;
		}
		else if (type==3){//减法 
			if(!ret[3])ret[3]=sub(ret[0],ret[1]);
			cout<<"多项式a-b:\n(";print(ret[0]);cout<<")-(";print(ret[1]);cout<<")=(";print(ret[3]);cout<<")"<<endl;
		}
		else if (type==4){//乘法 
			if(!ret[4])ret[4]=mul(ret[0],ret[1]);
			cout<<"多项式a*b:\n(";print(ret[0]);cout<<")*(";print(ret[1]);cout<<")=(";print(ret[4]);cout<<")"<<endl;
		}
		else if (type==5){//a求导 
			if(!ret[5])ret[5]=derivative(ret[0]);
			cout<<"对多项式a求导得a’= (";print(ret[5]);cout<<")"<<endl; 
		} 
		else if (type==6){//b求导 
			if(!ret[6])ret[6]=derivative(ret[1]);
			cout<<"对多项式b求导得b’= (";print(ret[6]);cout<<")"<<endl; 
		}
		else if (type==7){//求值 
			cout<<"请输入x的值"<<endl;
			cin>>x;
			if (ret[0]!=NULL){
				cout<<"a: (";print(ret[0]);cout<<")="<<value(ret[0],x)<<endl;
			}
			if (ret[1]!=NULL){
				cout<<"b: (";print(ret[1]);cout<<")="<<value(ret[1],x)<<endl;
			}
			if (ret[2]!=NULL){
				cout<<"多项式a+b的值:\n(";print(ret[0]);cout<<")+(";print(ret[1]);cout<<")="<<value(ret[2],x)<<endl;
			}
			if (ret[3]!=NULL){
				cout<<"多项式a-b的值:\n(";print(ret[0]);cout<<")-(";print(ret[1]);cout<<")="<<value(ret[3],x)<<endl;
			}
			if (ret[4]!=NULL){
				cout<<"多项式a*b的值:\n(";print(ret[0]);cout<<")*(";print(ret[1]);cout<<")="<<value(ret[4],x)<<endl;
			}
		    if (ret[5]!=NULL){
				cout<<"多项式a求导的值  a':\n(";print(ret[5]);cout<<")="<<value(ret[5],x)<<endl;
		    }
			if (ret[6]!=NULL){
				cout<<"多项式b求导的值  b':\n(";print(ret[6]);cout<<")="<<value(ret[6],x)<<endl;
			}
		}
		else if (type==8)break;
		else cout<<"输入非法，请重新输入:"<<endl; 
	}
	for (int i=0;i<7;i++)
		if (ret[i])del(ret[i]);
	system("cls");
	cout<<"谢谢使用"<<endl; 
}
