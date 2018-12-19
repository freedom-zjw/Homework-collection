#include<iostream>
#include<queue>
#include<map>
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
using namespace std;
struct node{
    int a[2][4],dep;
    string s;
    node(){
        rep(i,0,3){
            a[0][i]=i+1;
            a[1][i]=i+1+4;
            s="";
        }
        dep=0;
    }
}ed;
int n;
map<int,int>f;
int HASH(node no){
    int x=0;
    rep(i,0,3)x=x*10+no.a[0][i];
    rep(i,0,3)x=x*10+no.a[1][i];
    int &ret=f[x];
    if (ret!=0)return 0;
    ret=1;
    return 1;
}
int check(node no){
    rep(i,0,1)
        rep(j,0,3)
            if (no.a[i][j]!=ed.a[i][j])
                return 0;
    return 1;
}
void change(node &next,int ty,node &no){
    if (ty==1){
        rep(i,0,1){
            next.a[0][i]=no.a[0][i+2];
            next.a[1][i]=no.a[1][i+2];
            next.a[0][i+2]=no.a[0][i];
            next.a[1][i+2]=no.a[1][i];
        }
        next.s=no.s+"A";
    }
    else if (ty==2){
        rep(i,0,2){
            next.a[0][i]=no.a[0][i+1];
            next.a[1][i]=no.a[1][i+1];
        }
        next.a[0][3]=no.a[0][0];
        next.a[1][3]=no.a[1][0];
        next.s=no.s+"B";
    }
    else {
        rep(i,0,1)
            rep(j,0,3)
                next.a[i][j]=no.a[i][j];
        next.a[0][1]=no.a[0][2];
        next.a[0][2]=no.a[1][2];
        next.a[1][2]=no.a[1][1];
        next.a[1][1]=no.a[0][1];
        next.s=no.s+"C";    
    }
    next.dep=no.dep+1;
}
void bfs(){
    node st;
    queue<node> Q;
    Q.push(st);
    if (check(st)){
        cout<<0<<endl;
        return;
    } 
    if (!n){
    	cout<<-1<<endl;
    	return;
    }
    while (!Q.empty()){
        node no=Q.front();
        Q.pop();
        rep(i,1,3){
            node next_state;
            change(next_state,i,no);
            if (!HASH(next_state))continue;
            if (check(next_state)){
                cout<<next_state.s.length()<<" "<<next_state.s<<endl;
                return;
            }
            if (next_state.dep<n)Q.push(next_state);
        }
        
    }
    cout<<-1<<endl;
    return;
}
int main(){
    while (cin>>n){
        if (n==-1)break;
        f.clear();
        rep(i,0,1)
            rep(j,0,3)
                cin>>ed.a[i][j];
        bfs();
    }
    return 0;
}                                 