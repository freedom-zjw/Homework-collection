#include<stdio.h>
#define rep(i,u,v) for (int i=(u);i<=(v);i++) 
double degree[86401];
double abs(double x){return x<0?-x:x;}
void prepare(){
	double hourdegree,minutedegree,d;  
    rep(i,0,23){  
        rep(j,0,59){  
            rep(k,0,59) {  
                hourdegree=(i % 12) * 30 + (60.0 * j  + k) / 120;  
                minutedegree=j * 6 + 1.0 * k / 10;  
                d=hourdegree>minutedegree ? 360-hourdegree+minutedegree : minutedegree-hourdegree;  
                degree[i * 3600 + j * 60 + k] = d;  
            }  
        }  
    }  
}
const double eps = 0.08; 
bool check(double d1, double d2) {  
    if (d1 == 0) {  
        return abs(d1-d2)<eps || abs(360.0-d2)<eps;  
    }  
    return abs(d1-d2)<eps;  
}  
int main(){
	int h, m, s;  
    double A;  
	prepare();
	while (scanf("%lf %d:%d:%d", &A, &h, &m, &s)!=EOF){
	    if (A==-1)break;
		int st=h*3600+m*60+s;
		rep(i,st,86399){
			if (check(A,degree[i])){
				printf("%02d:%02d:%02d\n",i/3600,i%3600/60,i%3600%60);  
				break;
			}
			if (i==86399)i=-1;
		}
	}
	return 0;
}