#include<cstdio>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++) 
bool cmp(int a, int b){
    return a>b;
}
int main(){
    int t, n, a[20001], i, j, ans;
    scanf("%d", &t);
    while(t--){
        ans=0;
        j=3;
        scanf("%d",&n);
        rep(i,1,n)
            scanf("%d", &a[i]);
        sort(a+1,a+1+n,cmp);
        while(j<=n){
			ans+=a[j];
			j+=3;
        }
        printf("%d\n", ans);
    }
    return 0;
}                          