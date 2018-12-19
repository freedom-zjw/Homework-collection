#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int a[5];
int target, res;
void dfs(int n){
    rep(i,0,n-1)
        if (a[i]>res&&a[i] <= target)
            res = a[i];
    if (res==target)return;
    if (n==1)return;
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            int left, right;
            left = a[i];
            right = a[j];
            a[j] = a[n - 1];
            //add and decrease a number
            a[i] = left + right;
            dfs(n - 1);
            //subtract and decrease a number
            a[i] = left - right;
            dfs(n - 1);
            //subtract and decrease a number
            a[i] = right - left;
            dfs(n - 1);
            //multiply and decrease a number
            a[i] = left * right;
            dfs(n - 1);
            //divide and decrease a number
            if (left != 0 && right % left == 0) {
                a[i] = right / left;
                dfs(n - 1);
            }
            if (right != 0 && left % right == 0) {
                a[i] = left / right;
                dfs(n - 1);
            }
            a[i] = left;
            a[j] = right;
        }
    }
}
int main(){
    int T;
    cin>>T;
    while (T--) {
        rep(i,0,4)cin >> a[i];
        cin >> target;
        res = -101;
        dfs(5);
        cout << res << endl;
    }
    return 0;
}