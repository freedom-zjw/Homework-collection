#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--)
int base;
string a,b;
int change(string str){
    int len=str.length(),num=0,now=1;
    dto(i,len-1,0){
        if (str[i]>='0'&&str[i]<='9')
            num=num+now*(str[i]-'0');
        else if (str[i]>='A'&&str[i]<='Z')
            num=num+now*(str[i]-'A'+10);
        now*=base;
    }
    return num;
}
int rechange(int x){
    char str[60];
    int len=-1;
    while (x>=base){
        int y=x%base;
        if (y<=9)str[++len]=char(y+48);
        else str[++len]=char(y+55);
        x/=base;
    }
    if (x<=9)str[++len]=char(x+48);
    else str[++len]=char(x+55);
    dto(i,len,0)
        cout<<str[i];
    cout<<endl;     
}
int main(){
    int T;
    cin>>T;
    while (T--){
        cin>>base;
        cin>>a>>b;
        int ret1=change(a);
        int ret2=change(b);
        rechange(ret1/ret2);
        rechange(ret1%ret2);
    }
    return 0;
}                                