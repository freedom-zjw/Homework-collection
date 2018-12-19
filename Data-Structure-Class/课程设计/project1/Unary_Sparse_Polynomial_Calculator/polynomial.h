#ifndef POLYNOMIAL_H_
#define POLYNOMIAL_H_
class polynomial{
	public:	
		polynomial();
		friend polynomial* creatpolyn(int n);// 建立多项式 
		friend void del(polynomial *p);//删除多项式 
		friend void insert(polynomial *p,polynomial *head); //插入一个点到一个多项式里  
		friend int compare(polynomial *a,polynomial *b); //比较某两项 
		friend polynomial* add(polynomial *a,polynomial *b);//加法 
		friend polynomial* sub(polynomial *a,polynomial *b);//减法 
		friend polynomial* mul(polynomial *a,polynomial *b);//乘法 
		friend polynomial* derivative(polynomial *p);//求导 
		friend float value(polynomial *head,float x);//求值 
		friend void print(polynomial *p);//输出 
	private:
		float coef;//系数
 		int exp;//指数	
	    polynomial *next;
};
#endif