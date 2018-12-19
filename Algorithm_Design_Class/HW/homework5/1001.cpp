#include<iostream>
#include<cstring> 
#include<algorithm>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
char s1[1010],s2[1010];
int f[1010][1010];
int len1,len2;
int main(){
	while (scanf("%s%s",s1+1,s2+1)!=EOF){
		memset(f,0,sizeof(f));
		len1=strlen(s1+1);
		len2=strlen(s2+1); 
		rep(i,1,len1)
			rep(j,1,len2){
				if (s1[i]==s2[j])
					f[i][j]=max(f[i][j],f[i-1][j-1]+1);
				f[i][j]=max(f[i-1][j],max(f[i][j],f[i][j-1]));
			}
		cout<<f[len1][len2]<<endl;
	}
	return 0;
}