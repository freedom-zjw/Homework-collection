#include<iostream>
using namespace std;
int main(){
    int T;
	int a, b, l, r;
    cin>>T;
    while(T--){
        cin>>a>>b>>l>>r;
        bool flag=false;
        int ans;
        for(ans = r; ans >= l; ans--)
            if(a % ans == 0 && b % ans == 0){
				flag = true; 
				break;
			}
        if(flag) cout << ans << endl;
        else cout << "No answer" << endl;
    }
    return 0;
}