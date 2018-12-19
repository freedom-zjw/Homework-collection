#include<iostream>
using namespace std;
char s[100010],ss[200010];
int ans,p[200010];
void manacher()
{  int i,len=0,mx=1,idx;ans=0;bool bo=false;
   for (i=0;s[i];i++){
         ss[++len]='#';
         ss[++len]=s[i];
   }
   ss[++len]='#';ss[++len]=0;ss[0]='$';
   p[1]=1;
   for (i=2;i<len;i++)
   {  if (mx>i)
         p[i]=min(p[2*idx-i],mx-i);
         else p[i]=1;
      while (ss[i+p[i]]==ss[i-p[i]])p[i]++;
      ans=max(ans,p[i]);
      if (p[i]+i>mx)
      {   mx=p[i]+i;
          idx=i;
      }
      
   }
}
int main(){   
    while (gets(s))
    {  if (s[0]!='\0'){
         manacher();
         printf("%d\n",ans-1);
       }
    }
}