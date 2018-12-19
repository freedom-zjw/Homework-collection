#ifndef BIGNUM_H_
#define BIGNUM_H_
#include"myheader.h"
class Bignum{
    public:
        Bignum();
		Bignum(int n);//数字转化 
    	Bignum(string str);//字符串转化 
    	friend string transform(const Bignum &y); 
    	bool operator <= (const Bignum & y);//比较是否比y小 
    	bool operator == (const Bignum & y);//比较是否和y相等 
    	Bignum operator + (const Bignum & y);//高精度+ 
    	Bignum operator - (const Bignum & y);//高精度- 
    	Bignum operator / (const int & y);//高精/单精
    	Bignum operator * (const Bignum & y);//高精*高精 
    	Bignum operator / (Bignum & y);//高精/高精
    	friend Bignum calc(Bignum r1,char ch,Bignum r2);//两个大数进行ch运算 
    	void Print();//输出大整数 	
    private:
        const static int Maxn=1100,exp=10,remain=5;//exp为精度10位，remain是保留5位小数 
   	    int v[Maxn],flag;//flag=0,正数，flag=1负数 
};
#endif 