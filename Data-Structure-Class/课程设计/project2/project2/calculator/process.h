#ifndef PROCESS_H_
#define PROCESS_H_
#include"bignum.h"
#include"mystack.cpp"
#include"myheader.h"
class process{
	public:
		void Introduce();//介绍 
		void start();//开始工作 
		int check();//检查表达式是否合法 
		void work();//开始计算 
		void deletenum();
		void printstack(int step);
	private:
		char s[1010];
		int len,st;
		Bignum zero;
		Mystack<Bignum>q1;//数字栈 
		Mystack<char>q2;//操作符栈
		string  operators;//操作符栈的内容
		string  operand;//数字栈的内容 
		map<char,int>f;
};
#endif