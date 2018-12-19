#include<cstdio>
#include<stack>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int main(){
	int n,cnt,a[1005];
	while(scanf("%d",&n),n){
		while(scanf("%d",&a[0]),a[0]){
			stack<int>s;cnt=0;
			rep(i,1,n-1)scanf("%d",&a[i]);
			rep(i,1,n){
				s.push(i);
				while(s.top()==a[cnt]){
					if(!s.empty()){
						cnt++;
						s.pop();
					}
					if(s.empty())break;
				}    
			}
			if(cnt==n) printf("Yes\n");
			else printf("No\n");
		}
		printf("\n");

	}
}