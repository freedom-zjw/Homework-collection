#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
int cost[10] = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
int w[36][36][36];
int calc(int x) {  
    if (x==0) return 6;  
    int ans= 0;  
    if (x<0)ans++,x=-x;  
    while (x) {  
        ans+=cost[x%10];  
        x/=10;  
    }  
    return ans;  
} 
void init() {  
    int x,y,z;  
    rep(i,-999,999){  
        x=calc(i);  
        rep(j,-999,999){  
            y=calc(j);  
            z=i+j;  
            if (-999<=z&&z<=999)w[x][y][calc(z)]++;  
            z=i-j;  
            if (-999<=z&&z<=999)w[x][y][calc(z)]++;  
            z=i*j;  
            if (-999<=z&&z<=999)w[x][y][calc(z)]++;   
            if (!j)continue;  
            z=i/j;  
            if (-999<=z&&z<=999)w[x][y][calc(z)]++;  
        }  
    }  
}
int main(){
	std::ios::sync_with_stdio(false); 
	init();
	int x, y, z;
    while (cin>>x&&x) {  
        cin>>y>>z; 
        if (w[x/5][y/5][z/5]==1)cout<<w[x/5][y/5][z/5]<<" solution for "<<x<<" "<<y<<" "<<z<<endl; 
        else cout<<w[x/5][y/5][z/5]<<" solutions for "<<x<<" "<<y<<" "<<z<<endl;  
    }  
} 