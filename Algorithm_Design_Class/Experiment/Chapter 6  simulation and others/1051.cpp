#include<iostream>
#include<cstdio>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
const float pi=3.1415927;
int main()  {  
    float diameter,time,distances,speed;  
    int revolutions,T=0;    
    while (cin>>diameter>>revolutions>>time,revolutions>0)  {  
        distances=pi*(diameter/12.0/5280.0)*revolutions;  
        speed=distances/(time/60/60);  
        printf("Trip #%d: %.2f %.2f\n", ++T, distances, speed);  
    }  
    return 0;  
}           