#include<iostream>
using namespace std;
class Complex //复数类
{
	public:
		Complex(double real = 0, double imag = 0);
		Complex operator+(Complex& com); 
		Complex operator-(Complex& com);
		Complex operator*(Complex& com);
		Complex operator/(Complex& com);
		double& operator[](int i); 
		double operator[](int i) const;
		friend ostream& operator<<(ostream& os, Complex& com);//友元函数重载提取运算符"<<"
		friend istream& operator>>(istream& is, Complex& com);//友元函数重载插入运算符">>"
	private:
		double real;//实数
		double imag;//虚数
};
Complex::Complex(double real,double imag){//构造函数，初始化当前对象的real和imag 
	this->real=real;
	this->imag=imag;
}
Complex Complex::operator+(Complex &com){//重载运算符+ 
	Complex tmp;
	tmp.real=real+com.real;
	tmp.imag=imag+com.imag;
	return tmp;
}
Complex Complex::operator-(Complex &com){//重载运算符- 
	Complex tmp;
	tmp.real=real-com.real;
	tmp.imag=imag-com.imag;
	return tmp;
}
Complex Complex::operator*(Complex &com){//重载运算符* 
	Complex tmp;
	//(a+bi)*(c+di)=(ac-bd)+(ad+bc)i;
	tmp.real=real*com.real-imag*com.imag;
	tmp.imag=real*com.imag+imag*com.real;;
	return tmp;
}
Complex Complex::operator/(Complex& com){//重载运算符/ 
    //(a+bi)/(c+di)=[(ac+bd)+(bc-ad)i]/(c^2+d^2) 
    Complex tmp;
	double div = (com.real*com.real)+(com.imag*com.imag);
    tmp.real = ((real*com.real)+(imag*com.imag))/div;
    tmp.imag = ((imag*com.real)-(real*com.imag))/div;
    return tmp;
}
double& Complex:: operator[](int i){//重载中括号，返回值可做左值 
    if (!i)return real;
    else return imag;
}
double Complex:: operator[](int i) const{//重载中括号，返回值不可做左值 
    if (!i)return real;
    else return imag;
}
ostream& operator<<(ostream& os, Complex& com){//友元函数重载提取运算符"<<" 
    if (com.real==0&&com.imag==0)os<<0;//实部和虚部都为0 
    else {//实部和虚部不都为0 
    	if (com.real!=0)os<<com.real;//
    	if (com.real!=0&&com.imag>0)os<<'+';//虚部为正数时要添+号 
    	if (com.imag!=0&&com.imag!=1&&com.imag!=-1)os<<com.imag;//注意虚部绝对值为1时不用输出1 
    	if (com.imag!=0){
    		if (com.imag==-1)os<<'-';//虚部为-1，由于前面没输出-1，所以补一个-号 
    		os<<'i';
		}
	}
    return os;
}
istream& operator>>(istream& is, Complex& com){//友元函数重载插入运算符">>" 
    is >> com.real>> com.imag;
    return is;
}
int main(){
	int T;
	cin >> T;
	while (T--) {
		Complex com1, com2;
		cin >> com1;
		cout << com1 << endl;
		cout << com1[0] << " " << com1[1] << endl;
 
		cin >> com2;
		cout << com2 << endl;
		cout << com2[0] << " " << com2[1] << endl;
 
		Complex c1 = com1 + com2;
		cout << "(" << com1 << ")+(" << com2 << ")=" << c1 << endl;
 
		Complex c2 = com1 - com2;
		cout << "(" << com1 << ")-(" << com2 << ")=" << c2 << endl;
 
		Complex c3 = com1 * com2;
		cout << "(" << com1 << ")*(" << com2 << ")=" << c3 << endl;
 
		Complex c4 = com1 / com2;
		cout << "(" << com1 << ")/(" << com2 << ")=" << c4 << endl;
	}
    return 0;
}