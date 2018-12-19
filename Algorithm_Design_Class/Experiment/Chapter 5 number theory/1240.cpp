#include<iostream>
using namespace std;
int change(int n){
	int ans=0,pow=1;
	while (n){
		int x=n%10;
		x=x>4?x-1:x;
		ans=ans+pow*x;
		pow*=9;n/=10;
	}
	return ans;
}
int main(){
	ios::sync_with_stdio(false);
	int n;
	while (cin>>n&&n){
		cout<<n<<": "<<change(n)<<endl;
	}
	return 0;
}