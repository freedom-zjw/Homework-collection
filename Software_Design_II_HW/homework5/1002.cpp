#include<iostream>
using namespace std;
class Number{
	public:
		Number(int=0);
		Number &add(int);
		Number &sub(int);
		void print(); 
	private:
		int value;
};
Number::Number(int value){//构造函数，初始化 
	this->value=value;
}
Number& Number::add(int _value){//+了_value后返回当前对象的引用 
	value+=_value; 
	return *this;
} 
Number& Number::sub(int _value){//减了_value返回当前对象的引用
	value-=_value; 
	return *this;
} 
void Number::print(){//输出value值 
	cout<<value<<endl;
}
int main(){
	//1+2+3+4;
    Number n(1);
    n.add(2).sub(3).add(4);
    n.print();
    
    //1-2+9-9+120+45-89+7
    Number m(1);
    m.sub(2).add(9).sub(9).add(120).add(45).sub(89).add(7);
    m.print();
    
    //10000-3467+999999-88765+1234+3+8-6751+2345-5557
    Number k(10000);
    m.sub(3467).add(999999).sub(88765).add(1234).add(3).add(8).sub(6751).add(2345).sub(5557);
    m.print();
    return 0;
}