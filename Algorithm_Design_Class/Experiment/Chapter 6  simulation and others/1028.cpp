#include <iostream>
#include <string>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int calc(string ss){
    int len=(int)ss.length(),sum=0;
    rep(i,0,len-1)ss[i] = ss[i] - '0';
    while (ss[len-1]%2==0) {  //²»¶Ï/2 
        rep(i,0,len-1){
            if (i+1<len)ss[i+1]+=(ss[i]%2)*10;
            ss[i]=ss[i]/2;
        }
        sum++;
    }
    return sum + 1;
}

int main(){
    int T,cases=0;
    string s;
    cin>>T;
    while (cases!=T) {
        cin>>s;
        cases++;
        cout<<"Case "<<cases<< ": "<<calc(s)<<endl;
        if (cases!=T)cout << endl;
    }
    return 0;
}                   