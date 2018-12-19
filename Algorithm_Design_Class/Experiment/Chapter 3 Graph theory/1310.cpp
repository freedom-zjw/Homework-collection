#include<iostream>
#include<cstdio>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 200010
struct node{
	int x,lc,rc;
}t[N];
void preorder(int now){
	if (now==-1)return;
	printf(" %d",t[now].x);
	preorder(t[now].lc);
	preorder(t[now].rc);
}
void inorder(int now){
	if (now==-1)return;
	inorder(t[now].lc);
	printf(" %d",t[now].x);
	inorder(t[now].rc);
}
void postorder(int now){
	if (now==-1)return;
	postorder(t[now].lc);
	postorder(t[now].rc);
	printf(" %d",t[now].x);
}
int main(){
	int x,n,T=0;
	while (scanf("%d",&n)!=EOF){
		if (++T>1)printf("\n");
		if (!n){
			printf("Inorder:\n");
			printf("Preorder:\n");
			printf("Postorder:\n");
			continue;
		}
		scanf("%d",&t[0].x);
		t[0].lc=t[0].rc=-1;
		rep(i,2,n){
			scanf("%d",&t[i].x);
			t[i].lc=t[i].rc=-1;
			int now=0;
			while (1){
				if (t[now].x>=t[i].x){
					if(t[now].lc==-1){
						t[now].lc=i;
						break;
					} 
					now=t[now].lc;
				}
				else {
					if(t[now].rc==-1){
						t[now].rc=i;
						break;
					} 
					now=t[now].rc;
				}
			}		
		}
		printf("Inorder:");inorder(0);printf("\n");
		printf("Preorder:");preorder(0);printf("\n");
		printf("Postorder:");postorder(0);printf("\n");
	}
	return 0;
} 