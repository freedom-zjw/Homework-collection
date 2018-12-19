#include<iostream>
using namespace std;
struct node{
	struct node* lc;
	struct node* rc;
};
int n,h1,h2,i,len;
string s;
void dfs(node* &now,int H1,int H2){
	while (1){
		if (++i==len)return;
		if (H1>h1)h1=H1;
		if (H2>h2)h2=H2;
		if (s[i]=='d'){
			node* n1=new node;
			n1->lc=NULL;n1->rc=NULL;
			if (now->lc==NULL){
				now->lc=n1;
				dfs(now->lc,H1+1,H2+1);
			}	
			else {
				node *p=now->lc;
				int cnt=1;
				while (p->rc!=NULL)p=p->rc,cnt++;
				p->rc=n1;
				dfs(p->rc,H1+1,H2+cnt+1);
			}
		}
		else return;
	}
}
void del(node* now){
	if (now==NULL)return;
	del(now->lc);
	del(now->rc);
	delete now;
}
int main(){
	int T=0;
	while (cin>>s){
		if (s[0]=='#')break;
		len=s.length();
		node* root=new node;
		root->lc=NULL;root->rc=NULL;
		h1=h2=0;i=-1;
		dfs(root,0,0);
		printf("Tree %d: %d => %d\n",++T,h1,h2);
		del(root);
	}
	return 0;
}