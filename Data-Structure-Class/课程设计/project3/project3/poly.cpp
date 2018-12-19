#include"poly.h"
#include<cstdio>
#include<iostream> 
using namespace std;
polynomial::polynomial() {
	next = NULL;
	coef = 0.0;
	exp = 0;
}
void del(polynomial *p) {//删除多项式 
	polynomial *p1, *p2;
	p1 = p->next; p2 = p1->next;
	while (p1->next) {
		delete p1;
		p1 = p2;
		p2 = p2->next;
	}
	p = NULL;
}
void insert(polynomial *p, polynomial *head) {  //插入一个点到一个多项式里   
	if (p->coef == 0) delete p;   //系数为0则删掉此结点 
	else {
		polynomial *last = head, *now = head->next;
		//now指向当前结点，last指向上一个结点 
		while (now&&p->exp<now->exp) {//按照指数从大到小的顺序查找插入位置   
			last = now;
			now = now->next;
		}
		if (now&&p->exp == now->exp) {//合并指数相同项 
			now->coef += p->coef;
			delete p;
			if (!now->coef) {//系数为0删除此结点             
				last->next = now->next;
				delete now;
			}
		}
		else {  //第一次出现的指数的结点 
			p->next = now;
			last->next = p;
		}
	}
}
polynomial* derivative(polynomial *p) {//求导 
	polynomial *h = p->next, *node, *ans_head;
	ans_head = new polynomial;
	ans_head->next = NULL;
	for (; h; h = h->next) {
		node = new polynomial;
		node->coef = (h->coef)*(h->exp);
		node->exp = (h->exp) - 1;
		node->v = h->v;
		insert(node, ans_head);
	}
	return ans_head;
}
void print(polynomial* p) { //输出多项式 
	polynomial* head = p->next;
	int cnt = 1;
	if (!head) {//若多项式为空，输出0
		cout << "0" << "\n";
		return;
	}
	while (head) {
		if (head->coef>0 && cnt != 1) putchar('+'); //不是首项的正系数项前添+号 
		if (head->coef != 1 && head->coef != -1) {//系数非1或-1
			cout << head->coef;
			if (head->exp)cout << head->v; //指数不为0才输出x 
			if (head->exp != 0 && head->exp != 1)cout << "^" << head->exp;//指数不为0且不为1才输出指数 
		}
		else {
			if (head->coef == 1 || head->coef == -1) {
				if (!head->exp)cout << 1 * head->coef;//指数为0 
				else {
					if (head->coef == -1)cout << "-";//系数为-1只输出- 
					if (head->exp)cout <<head->v; //指数不为0才输出x 
					if (head->exp != 0 && head->exp != 1)cout << "^" << head->exp;//指数不为0且不为1才输出指数 
				}
			}
		}
		head = head->next;
		cnt++;
	}
}