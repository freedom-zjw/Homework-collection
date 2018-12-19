#include<cstdio>
#include<cstring>
#include<iostream>
using namespace std;
int root,total,cnt,n,x;
struct node
{  int f,c,son[2],same,dd,sum,maxx,maxl,maxr,rev;
}t[500001];
int que[500001],queue,num[500001];
inline void updatasize(int x)
{     if (x==0)return;
      t[x].c=t[t[x].son[1]].c+t[t[x].son[0]].c+1;
      t[x].sum=t[x].dd+t[t[x].son[0]].sum+t[t[x].son[1]].sum;
      t[x].maxl=max(t[t[x].son[0]].maxl,t[t[x].son[0]].sum+t[x].dd+max(0,t[t[x].son[1]].maxl));
      t[x].maxr=max(t[t[x].son[1]].maxr,t[t[x].son[1]].sum+t[x].dd+max(0,t[t[x].son[0]].maxr));
	  t[x].maxx=max(max(t[t[x].son[0]].maxx,t[t[x].son[1]].maxx),
	                max(0,t[t[x].son[0]].maxr)+max(0,t[t[x].son[1]].maxl)+t[x].dd);    
}
inline void reverse(int x) {        
      if (x == 0) return;        
      int tt=t[x].son[1];t[x].son[1]=t[x].son[0];t[x].son[0]=tt;
	  tt=t[x].maxl;t[x].maxl=t[x].maxr;t[x].maxr=tt; 
      t[x].rev=1-t[x].rev;
}      
inline void makesame(int x,int cc)
{     if (x==0)return;
      t[x].dd=cc;
      t[x].sum=t[x].c*t[x].dd;
      t[x].maxl=t[x].maxr=t[x].maxx=max(t[x].sum,t[x].dd);
      t[x].same=1;
}
inline void push_down(int x) {      
    if (x == 0) return;        
    if (t[x].rev) {            
         reverse(t[x].son[0]);            
         reverse(t[x].son[1]);            
        t[x].rev=0;       
    }      
	if(t[x].same)
	{    makesame(t[x].son[0],t[x].dd);
         makesame(t[x].son[1],t[x].dd);
	     t[x].same=0;
	}
} 
inline void rotate(int x,int w)
{
	int y=t[x].f;
	push_down(y);push_down(x);
	t[y].son[1-w]=t[x].son[w];
	if (t[x].son[w]!=0)t[t[x].son[w]].f=y;
	if (t[y].f!=0)
	   if (t[t[y].f].son[0]==y)t[t[y].f].son[0]=x;
	   else t[t[y].f].son[1]=x;
    t[x].f=t[y].f;
    t[x].son[w]=y;
    t[y].f=x;
    updatasize(y);updatasize(x);
}
inline void splay(int x,int y)
{   push_down(x);
	while(t[x].f!=y)
	{   if (t[t[x].f].f==y)
        {if (t[t[x].f].son[0]==x)rotate(x,1);else rotate(x,0);}
         else {
        	if (t[t[t[x].f].f].son[0]==t[x].f){
        		if (t[t[x].f].son[0]==x){rotate(t[x].f,1);rotate(x,1);}
        		else {rotate(x,0);rotate(x,1);}
        	}
        	else {
			    if (t[t[x].f].son[1]==x){rotate(t[x].f,0);rotate(x,0);}
				else {rotate(x,1);rotate(x,0);}  
			}
        }
	}
	if(y==0)root=x;
}
inline void addnode(int &x,int dd,int y)
{   
    if (queue!=0){x=que[queue];queue--;}
	else x=++total;t[x].rev=0;
	t[x].f=y;t[x].dd=dd;
	t[x].c=1;t[x].same=0;
	t[x].son[0]=t[x].son[1]=0;
	t[x].maxx=t[x].maxl=t[x].maxr=dd=t[x].sum;
}
inline void ins(int &x,int l,int r,int y)
{   if (l>r)return;
    int mid=(l+r)/2;
	addnode(x,num[mid],y);
    ins(t[x].son[0],l,mid-1,x);
    ins(t[x].son[1],mid+1,r,x);
    updatasize(x);
}	   
inline void select(int k, int f) {   
	      int x = root; k += 1;        
	      while (1) { 
		      push_down(x);            
		      if (k==t[t[x].son[0]].c + 1) break;            
			  if (k<=t[t[x].son[0]].c) x=t[x].son[0];            
			  else {k-=t[t[x].son[0]].c+1;x=t[x].son[1];}        
	       }        
           splay(x, f);     
}
inline void del(int x)
{  que[++queue]=x;
   if (t[x].son[0]!=0)del(t[x].son[0]);
   if (t[x].son[1]!=0)del(t[x].son[1]);
}
int main()
{   int i,j,m,x,y,cc;char s[10];
 //   freopen("sequence.in","r",stdin);
 //  freopen("sequence.out","w",stdout);
	scanf("%d%d",&n,&m);
	t[0].maxl=t[0].maxr=t[0].maxx=-1001;
	root=total=queue=0;addnode(root,0,0);
	addnode(t[root].son[1],0,root);t[root].c=2;
	for (i=1;i<=n;i++)scanf("%d",&num[i]);
	ins(t[2].son[0],1,n,2);	updatasize(t[root].son[1]);updatasize(root);
    for (i=1;i<=m;i++)
    {
    	scanf("%s",s);
    	if (s[0]=='G'){
    		scanf("%d%d",&x,&y);
    		select(x-1,0);
    		select(x+y,root);
    		printf("%d\n",t[t[t[root].son[1]].son[0]].sum);
		}
    	else if (s[0]=='M'&&s[2]=='X'){
    		select(0,0);select(t[root].c-1,root);
    		printf("%d\n",t[t[t[root].son[1]].son[0]].maxx);		
		}
    	else if (s[0]=='I'){
    		scanf("%d%d",&x,&y);
    		for (int j=1;j<=y;j++)scanf("%d",&num[j]);
    		select(x,0);select(x+1,root);
    		ins(t[t[root].son[1]].son[0],1,y,t[root].son[1]);
    		updatasize(t[root].son[1]);updatasize(root);
		}
    	else if (s[0]=='D'){
    		scanf("%d%d",&x,&y);
   		    select(x-1, 0);        
		    select(x+y,root);
		    del(t[t[root].son[1]].son[0]);t[t[root].son[1]].son[0]=0;
		    updatasize(t[root].son[1]);updatasize(root);
   		}
    	else if (s[0]=='R'){
    		scanf("%d%d", &x, &y);        
		    select(x-1, 0);        
		    select(x+y,root);
			reverse(t[t[root].son[1]].son[0]);  
		}
    	else {
  		   scanf("%d%d%d",&x,&y,&cc);
  		   select(x-1,0);
  		   select(x+y,root);
  		   makesame(t[t[root].son[1]].son[0],cc);
		}
   	}
 }