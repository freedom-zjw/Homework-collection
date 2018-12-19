#include<iostream>
using namespace std;
class String {
public:
  	String();             // str = ""
  	String(const char*);    // str = "abc" 
  	String(const String&);   // str = other_string
  	String& operator=(const char *);
  	String& operator=(const String&);
    String operator+(const String&);
    ~String();
 
  	char& operator[](int i);
  	char operator[](int i) const;
 
  	int size() const;
 
  	String& operator+=(const String&);
  	String& operator+=(const char*);
 
  	friend ostream& operator<<(ostream&, const String&);
  	friend istream& operator>>(istream&, String&);
 
  	friend bool operator==(const String& x, const char* s);
  	friend bool operator==(const String& x, const String& y);
  	friend bool operator!=(const String& x, const char* s);
  	friend bool operator!=(const String& x, const String& y);
  	private:
  		char *str;
};
////////////////////////////
#include<cstring>                                                                                                                                                                                          
String::String(){//构造函数，让str="" 
    str=new char[2];
    str="";
}
String::String(const char* s){//构造函数,str=s 
    str=new char [strlen(s)+1];
    strcpy(str,s);
}
String::String(const String& s){//构造函数,str=s.str 
    str=new char[strlen(s.str)+1];
    strcpy(str,s.str);
}
String::~String() {//析构函数 ，删除str，释放内存 
    delete [] str;
}
String& String::operator=(const char *s){//重载=，str=s 
    str=new char [strlen(s)+1];
    strcpy(str,s);
    return *this;
}
String& String::operator=(const String &s){//重载=，str=s.str 
    str=new char[strlen(s.str)+1];
    strcpy(str,s.str);
    return *this;
}
String String::operator+(const String& s){//重载+，str=str+s.str 
    String tmp;
    tmp.str=new char [strlen(str)+strlen(s.str)+1];
    strcpy(tmp.str,str);
    strcat(tmp.str,s.str);
    return tmp;
}
char& String::operator[](int i){//返回str[i],可作左值 
    return str[i];
}
char String::operator[](int i)const{//返回str[i]，不可作左值 
    return str[i];
}
int String::size()const{//返回str长度 
    return strlen(str);
}
String& String::operator+=(const char *s){//重载+= ，str=str+s 
    int len=strlen(str)+strlen(s);
    char *s_old=str;
    str=new char [len+1];
    strcpy(str,s_old);
    delete[] s_old;
    strcat(str,s);
    return *this;
}
String& String::operator+=(const String &s){//重载+=，str=str+s.str 
    int len=strlen(str)+strlen(s.str);
    char *s_old=str;
    str=new char [len+1];
    strcpy(str,s_old);
    delete[] s_old;
    strcat(str,s.str);
    return *this;
}
ostream& operator<<(ostream& os, const String& s){//重载<<，输出s.str 
    if (s.str!=NULL)
        os<<s.str;
    return os;
}
istream& operator>>(istream& is, String& s){//重载>>，读入字符串ss,赋值给s.str 
    string ss;
    is>>ss;
    s.str=new char [ss.length()+1];
    strcpy(s.str,ss.c_str());
    return is;
}
bool operator==(const String &x, const char *s){//重载==，判断x.str和s是否相等 
    if (strlen(x.str)!=strlen(s))return false;
    for (int i=0;i<strlen(x.str);i++)
        if (x.str[i]!=s[i])return false;
    return true;
}
bool operator==(const String &x, const String &y){//重载==，判断x.str和y.str是否相等 
    if (strlen(x.str)!=strlen(y.str))return false;
    for (int i=0;i<strlen(x.str);i++)
        if (x.str[i]!=y.str[i])return false;
    return true;
}
bool operator!=(const String &x, const String &y){//重载！=，判断x.str和y.str是否不等 
    if (strlen(x.str)!=strlen(y.str))return true;
    for (int i=0;i<strlen(x.str);i++)
        if (x.str[i]!=y.str[i])return true;
    return false;
}
bool operator!=(const String &x, const char *s){//重载！=，判断x.str和s是否不等 
    if (strlen(x.str)!=strlen(s))return true;
    for (int i=0;i<strlen(x.str);i++)
        if (x.str[i]!=s[i])return true;
    return false;
}            

///////////////////////////
String f1(String a, String b){
	a[2] = 'x';
	char c = b[2];
  	cout << "in f: " << a << ' ' << b << ' ' << c << '\n';
  	return b;
}
 
void f2(String s, const String& r){
  	char c1 = s[1];  // c1 = s.operator[](1).operator char()
  	s[1] = 'c';    // s.operator[](1).operator=('c')
 
    char c2 = r[1];  // c2 = r.operator[](1)
	//  r[1] = 'd';    // error: assignment to non-lvalue char, r.operator[](1) = 'd'
}
 
void f(){
  	String x, y, s;
  	cout << "Please enter two strings\n";
  	cin >> x >> y;
  	cout << "x= " << x << " , y = " << y << '\n';
 
    y = f1(x,y);
    cout << y << endl;
    
    f2(x,y);
 
  	cout << "s = \"" << s << "\"" << endl;
  	s = "abc";
  	cout << "s = \"" << s << "\"" << endl;
 
    cout << "\"" << x << "\" + \"" << y << "\" = " << "\"" << x+y << "\"\n";
 	String z = x;
  	if (x != z) cout << "x corrupted!\n";
  	x[0] = '!';
  	if (x == z) cout << "write failed!\n";
  	cout << "exit: " << x << ' ' << z << '\n';  
 
    z = s;
  	if (s != z) cout << "s corrupted!\n";
  	s[0] = '!';
  	if (s == z) cout << "write failed!\n";
  	cout << "exit: " << s << ' ' << z << '\n';  
 
}
 
int main()
{
  int T;
  cin >> T;
  while (T--)
  {
    f();
  }
}