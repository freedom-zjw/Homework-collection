#include<iostream>
#include<cstring>
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
char s[10][10],ans_str[10][10],dfs_str[10][10];
int r[10][10],c[10][10],num[9][10],ans,len;
struct node{
	int x,y,block,choice;
	node(){}
	node(int xx,int yy){
		x=xx; y=yy; choice=0;
		block=(x/3)*3 + y/3;
	}
	void getChoiceNum(){
		rep(i,1,9)
			if (!r[x][i] && !c[y][i] && !num[block][i])
				choice++;
	}
}point[81];
bool cmp(const node &n1,const node &n2){return n1.choice<n2.choice;}
void dfs(int now){
	if (now==len){
		if (++ans==1)memcpy(ans_str,dfs_str,sizeof(dfs_str));
		return;
	}
	int x=point[now].x,y=point[now].y,block=point[now].block; 
	rep(i,1,9)
		if (!r[x][i] && !c[y][i] && !num[block][i]){
			dfs_str[x][y]=char(i+48);
			r[x][i]=c[y][i]=num[block][i]=1;
			dfs(now+1);
		 	r[x][i]=c[y][i]=num[block][i]=0;
		}
}
int main(){
    int T;
    cin>>T;
    rep(tt,1,T){
        memset(r,0,sizeof(r));
        memset(c,0,sizeof(c));
        memset(dfs_str,0,sizeof(dfs_str));
        memset(num,0,sizeof(num));
        len=0;ans=0;
        rep(i,0,8){
            cin>>s[i];
            rep(j,0,8)
                if (s[i][j] != '_'){
                    int x=s[i][j]-'0';
                    r[i][x]=c[j][x]=1;    //第i行和第j行都不能再填x 
                    dfs_str[i][j]=char(x+48); 
                    num[(i/3)*3 + j/3][x]=1;//当前格所在的小九宫格不能再填x 
                }
                else {
                	point[len++]=node(i,j);
				}
        }
        rep(i,0,len-1)point[i].getChoiceNum();//计算每个空位置可填的数字数 
        sort(point,point+len,cmp);//按可填数字数从小到大排序 
        dfs(0);
        if (tt>1)cout<<endl;
        if (!ans)cout<<"Puzzle "<<tt<<" has no solution"<<endl;
        else if (ans>1)cout<<"Puzzle "<<tt<<" has "<<ans<<" solutions"<<endl;
        else {
            cout<<"Puzzle "<<tt<<" solution is"<<endl;
            rep(i,0,8)
                cout<<ans_str[i]<<endl;
        }
    }
    return 0;
}                        