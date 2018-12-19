#include<iostream>
#include<sstream>
using namespace std;
int main(){
	string s="",ss;
	char ch;
	int Letternum=0,Wordnum=0;
	//Letternum统计字符数，Wordnum统计单词数 
	while (cin.get(ch)){
		if (ch!='\n'){//不是换行符的字符加进s
			s+=ch;
			Letternum++;
		}
		else s+=" ";
		//若是换行符则用空格代替，这一步很重要，方便后面分离单词 
	}
	istringstream str(s);
	while (str>>ss)Wordnum++;//分离单词并统计 
	cout<<"Letters: "<<Letternum<<endl;
	cout<<"Words: "<<Wordnum<<endl;
	return 0;
}
