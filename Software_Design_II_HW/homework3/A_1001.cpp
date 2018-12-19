#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
class Josephus{
    public:
        int n,s,m;
        int next[5002];
    Josephus(int n,int s,int m){ 
        this->n=n;
        this->s=s;
        this->m=m;
    }
    void pre(){
	   //准备工作，next[i]表示第i个人 的下一个人是谁，用next数组来模拟链表 
        rep(i,1,n-1)next[i]=i+1;
        next[n]=1;
    }
    void getans(){
        int count=0,fa=s-1;
        if (!fa)fa=n;
        while (1){
            int cnt=0;
            while (++cnt<m){//每次循环m个人得到出队的人，fa记录当前点的上一个点 
                fa=s;
                s=next[s];
            }
            cout<<s<<endl;
            count++;    
            if (count==n)return;
            next[fa]=next[s];//删除出队人，next[fa]的值改为s的下一个人即next[s] 
            s=next[s];//下一轮报数从s的下一个人开始 
        }
    }
};
int main(){
    int n,s,m;
    cin>>n>>s>>m;
    Josephus text(n,s,m);
    text.pre();
    text.getans();
    return 0;
}                           