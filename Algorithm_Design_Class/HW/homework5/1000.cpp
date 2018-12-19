#include<iostream>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int a[5010],f[5010];
int main(){
	int n,maxlen=0,minal;
	cin>>n;
	rep(i,1,n){
		cin>>a[i];
		f[i]=1;
		rep(j,1,i-1)
			if (a[i]>=a[j])
				f[i]=max(f[i],f[j]+1);
		if (f[i]>maxlen || maxlen==f[i]&&minal>a[i]){
			maxlen=f[i];
			minal=a[i];
		}	
	} 
	cout<<maxlen<<" "<<minal<<endl; 
} 