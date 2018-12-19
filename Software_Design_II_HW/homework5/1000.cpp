#include<cstdlib>
#include<iostream>
#include<cstring>
using namespace std;
class MyString{

	public:
    	MyString(const char*);
   	 	MyString(int = 0, char = '\0');  //initialize size_t number of char, terminated with '\0'
   	 	MyString(const MyString&);  //copy constructor
    	~MyString();  //destructor
    	const char* c_str(); //return private char*
    	static int getNumberOfObjects();
	private:
    	char *str;
    	static int numberOfObjects;//count the number of MyString objects

};
MyString::MyString(const char *s){//构造函数，将str初始化为s
    str=new char[strlen(s)+1];//注意长度并没有将结束符\0算在内，因此要+1，下同 
    strcpy(str,s);
    ++numberOfObjects;
}
MyString::MyString(int n, char ch){//构造函数，将str初始化长度为n且字符都是ch 
    str=new char[n+1];
    for(int i=0;i<n;i++)str[i]=ch;
    str[n]='\0';
    ++numberOfObjects;
}
MyString::MyString(const MyString &s){//拷贝构造函数，将当前对象初始化成与对象s一样 
    str=new char[strlen(s.str)+1];
    strcpy(str,s.str);
    ++numberOfObjects;
}
MyString::~MyString() {//析构函数，释放空间，同时将当前对象数-1 
    delete [] str;
    --numberOfObjects;
}
const char *MyString::c_str() {//返回私有成员str 
    return str;
}
int MyString::getNumberOfObjects() {//返回目前有多少个MyString对象 
    return numberOfObjects;
}
int MyString::numberOfObjects=0;//静态数据成员初始化 
int main(){
       MyString ms1(5, 'c');
       cout << ms1.c_str() << endl;
       MyString *ptr_ms2 = new MyString("Hello!");
       {
       		MyString ms3(ms1);
       }
       cout << ptr_ms2->c_str() << endl;
       delete ptr_ms2;
       cout << MyString::getNumberOfObjects() << endl;
       
       MyString ms4(30, 'd');
       cout << ms4.c_str() << endl;
       MyString *ptr_ms5 = new MyString("Accept!");
       {
       		MyString ms6(ms4);
       		cout<<ms6.c_str()<<endl; 
       }
       cout << ptr_ms5->c_str() << endl;
       delete ptr_ms5;
       cout << MyString::getNumberOfObjects() << endl;
       
	   return 0;
}