#include<iostream>
using namespace std;
int main(){
	int T=0,L,P,V;
	while (cin>>L>>P>>V,V>0)
		cout<<"Case "<<++T<<": "<<(V/P)*L+min(L,V%P)<<endl;
	return 0;
}