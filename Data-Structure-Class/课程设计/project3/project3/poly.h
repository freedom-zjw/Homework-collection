#ifndef POLYNOMIAL_H_
#define POLYNOMIAL_H_
class polynomial {
public:
	polynomial();
	friend void del(polynomial *p);//删除多项式 
	friend void insert(polynomial *p, polynomial *head); //插入一个点到一个多项式里  
	friend polynomial* derivative(polynomial *p);//求导 
	friend void print(polynomial *p);//输出 
	float coef;//系数
	int exp;//指数	
	char v;
	polynomial *next;
};
#endif