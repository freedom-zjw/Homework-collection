#include<iostream>
#include<list>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
list<int> Q;
int main(){
	int n,x;
	string op;
	while (cin>>n){
		Q.clear();
		rep(i,1,n){
			cin>>op;
			if (op=="push_back"){
				cin>>x;
				Q.push_back(x);
			}
			else if (op=="push_front"){
				cin>>x;
				Q.push_front(x);
			}
			else if (op=="print"){
				list<int>::iterator it;
				cout<<"List:"<<endl;
				for (it=Q.begin();it!=Q.end();it++)
					cout<<(*it)<<endl;
			}
			else if (op=="pop_back")Q.pop_back();
			else if (op=="pop_front")Q.pop_front();
		}
	}
	return 0;
}