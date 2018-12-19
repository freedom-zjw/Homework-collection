#include <stdio.h>
#include <deque>
#include <memory.h>
using namespace std;
struct Node {
    int x,y,p;
};
char matrix[120][120];
int visited[120][120][120];
int n,m,d;
int dir[4][2]={{0,1},{0,-1},{-1,0},{1,0}};
deque<Node> q;
bool to_visit(int x, int y, int p) {
    if (x>=0&&x<m&&y>=0&&y<n&&matrix[x][y]=='P'&&visited[x][y][p]==0) 
        return true;
    else
        return false;
} 
int main() {
    int i,j, size;
    Node tmp, tmp2;
    int ans,flag;
    while (scanf("%d",&m)!=EOF) {
        scanf("%d%d",&n, &d);
        for (i=0;i<m;i++) {
            scanf("%s",&matrix[i]);
        }
        memset(visited,0,sizeof(visited));
        tmp.x=0;
        tmp.y=0;
        tmp.p=d;
        ans=flag=0;
        visited[tmp.x][tmp.y][tmp.p]=1;
        q.clear();
        q.push_back(tmp);
        while (!q.empty()) {
            size=q.size();
            while (size--) {
                tmp=q.front();
                q.pop_front();
                if (tmp.x==m-1&&tmp.y==n-1) {
                    flag=1;
                    break;
                }
                //ans++;
                for (i=0;i<4;i++) {
                    if (to_visit(tmp.x+dir[i][0], tmp.y+dir[i][1],tmp.p)==true) {
                        tmp2.x=tmp.x+dir[i][0];
                        tmp2.y=tmp.y+dir[i][1];
                        tmp2.p=tmp.p;
                        visited[tmp2.x][tmp2.y][tmp2.p]=1;
                        q.push_back(tmp2);
                    }
                }
                for (i=0;i<4;i++) {
                    for (j=0;j<=tmp.p;j++) {
                        tmp2.x=tmp.x+dir[i][0]*j;
                        tmp2.y=tmp.y+dir[i][1]*j;
                        tmp2.p=tmp.p-j;
                        if (to_visit(tmp2.x,tmp2.y,tmp2.p)==true) {
                            visited[tmp2.x][tmp2.y][tmp2.p]=1;
                            q.push_back(tmp2);
                        }
                    }
                }
            }
            if (flag==1)
                break;
            ans++;
        }
        if (flag==1)
            printf("%d\n", ans);
        else
            printf("impossible\n");
    }
    return 0;
}                      