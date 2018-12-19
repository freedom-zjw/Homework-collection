#include<iostream>
#include<cstring>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define dto(i,u,v) for (int i=(u);i>=(v);i--)
class BigNum{
	public:
	    BigNum(){
    		memset(v,0,sizeof(v));
    	}
	    BigNum(int x){
    		memset(v,0,sizeof(v));
			do{
				v[++v[0]]=x%10;
				x/=10;
			}while(x>0);	
    	}
    	BigNum operator + (const BigNum & y){//高精度+ 
			BigNum & x = * this;
			BigNum c;
			int i;
			c.v[0]=max(x.v[0],y.v[0]);
			for(i=1;i<=c.v[0];i++){
				c.v[i]+=x.v[i]+y.v[i];
				c.v[i+1]=c.v[i]/10;
				c.v[i]%=10;
			}
			for(;c.v[i];i++)c.v[i+1]=c.v[i]/10,c.v[i]%=10;
			c.v[0]=i-1;
			return c;
		}
		BigNum operator * (const BigNum & y){//高精*高精 
			BigNum x = * this ;BigNum c;
			int i,j;
			c.v[0]=x.v[0]+y.v[0]-1;
			for(i=1;i<=x.v[0];i++)
				for(j=1;j<=y.v[0];j++)
					c.v[i+j-1]+=x.v[i]*y.v[j];
			i=1;
			for(; c.v[i]||i<=c.v[0] ; i++ )c.v[i+1]+=c.v[i]/10,c.v[i]%=10;
			c.v[0]=i-1;
			while((c.v[c.v[0]]==0)&&(c.v[0]>1))c.v[0]--;
			return c;
		}	
		void Print(){
			for(int i=v[0];i>0;i--)printf("%d",v[i]);
			printf("\n");
		}
	private:
		int v[550];
};
int main() {
	int T,n,x,c[55];
	cin>>T;
	rep(t,1,T){
		cin>>n>>x;
		BigNum X(x);
		BigNum Ans(0);
		BigNum y(1);
		rep(i,1,n+1)cin>>c[i];
		dto(i,n+1,1){
			Ans = Ans + BigNum(c[i])*y;
			y = y * X; 
		}
		Ans.Print();
	}
	return 0;
}
