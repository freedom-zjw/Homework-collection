#include <bits/stdc++.h>  
const double INF=10e7;  
  
using namespace std;  
  
int T,n,cnt;  
double a[25][25],dp[25][1100000];  
  
struct point{       //结点结构体   
    int x,y;  
}pt[25];  
  
double d(point a,point b){      //结点间距离   
    return sqrt((a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y));  
}  
  
int main()  
{  
    scanf("%d",&T);  
    while(T--)  
    {  
        cnt=1;  
        scanf("%d",&n);  
        for(int i=2;i<n;i++) cnt<<=1;      //组合数（除起点终点外）   
          
        for(int i=0;i<n;i++)             //输入   
            scanf("%d %d",&pt[i].x,&pt[i].y);  
          
        for(int i=0;i<n;i++)             //建边   
            for(int j=0;j<n;j++)  
                a[i][j]=d(pt[i],pt[j]);  
                  
        for(int i=0;i<n;i++)             //初始化   
            for(int j=0;j<cnt;j++)  
                dp[i][j]=INF;  
          
        for(int i=0;i<n;i++)             //起点确定，定下初始条件   
            dp[i][0]=a[i][0];                 
        for(int i=1;i<cnt;i++)               //从有元素考虑起   
            for(int j=1;j<n-1;j++)  
            {  
                for(int k=1;k<n-1;k++)  
                {  
                    if((1<<k-1)&i)        //k is in the set  
                        dp[j][i]=min(dp[j][i],a[j][k]+dp[k][i-(1<<k-1)]); //状态转移方程   
                }  
            }  
        double ans=INF;  
        for(int i=1;i<n;i++)  
            ans=min(ans,dp[i][cnt-1]+a[i][n-1]);  
          
        printf("%.2lf\n",ans);  
    }  
      
    return 0;  
}                            