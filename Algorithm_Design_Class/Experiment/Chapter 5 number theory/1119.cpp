#include<iostream>
#include<cmath>
using namespace std;
int main(){
	int n;
	while (cin>>n&&n){
		int num=2+(n-1960)/10;
		int len=1;
		for (int i=1;i<=num;i++)len*=2;
		double sum=log(1),bit=log(2)*len;
		for (int i=2;;i++){
			if (sum+log(i)>=bit){
				cout<<i-1<<endl;
				break;
			}
			sum+=log(i);
		}
	}
	return 0;
}