#ifndef MYSTACK1_H_
#define MYSTACK1_H_
#include"myheader.h"
template<typename T> class Mystack{
	public:
    	Mystack();
        bool empty();
    	T top(); 
    	void push(T);
    	T pop();
    	int size(); 
    	~Mystack();
	private:
		struct node{
			T data;
			node *next;
			node():next(NULL){};
			node(const T &d,node *p=NULL):data(d),next(p){};
		};
    	node* top_pointer; 
    	int num;  
};
#endif