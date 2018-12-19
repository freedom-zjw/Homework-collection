/*
n个点，m条边，求一颗生成树，使得其最大权值边-最小权值边的权值最小。
枚举所有生成树求最优值
 */
#include<stdio.h>
#include<algorithm>
#include<string.h>
#define inf 0x3f3f3f
#define M 1007
using namespace std;
int pre[M];
struct E
{
    int u,v;
    int val;
} edg[M*20];
int n,m;
void init()
{
    for(int i=0; i<=n; i++)
        pre[i]=i;
}
int cmp(E a,E b)
{
    return a.val<b.val;
}
int find(int x)
{
    while(x!=pre[x])
        x=pre[x];
    return x;
}
int main()
{
    while(scanf("%d%d",&n,&m),n|m)
    {
        memset(edg,0,sizeof(edg));
        for(int i=0; i<m; i++)
            scanf("%d%d%d",&edg[i].u,&edg[i].v,&edg[i].val);
        sort(edg,edg+m,cmp);
        int minn=inf,flag=0,ii=0;
        for(int i=0; i<m-n+2; i++)
        {
            init();
            for(int j=ii,k=0; j<5050; j++)
            {
                int x=find(edg[j].u);
                int y=find(edg[j].v);
                if(x!=y)
                {
                    pre[x]=y;
                    k++;
                }
                if(k==n-1)
                {
                    flag=1;
                    ii++;
                    if(edg[j].val-edg[i].val<minn)
                        minn=edg[j].val-edg[i].val;
                    break;
                }
            }
        }
        if(flag)
            printf("%d\n",minn);
        else printf("-1\n");
    }
    return 0;
}
