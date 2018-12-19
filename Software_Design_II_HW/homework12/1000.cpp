#include<iostream>
#include<fstream>
#include<cstring>
#include<string>
#include<algorithm>
using namespace std;
#define f(ss)  while (fin.get(ch))ss+=ch;
string f1="File1.txt";
string f2="File2.txt";
string f3="File3.txt";
int check(string s1,string s2){
	int len1=s1.length();
	int len2=s2.length();
	int len=min(len1,len2);
	for (int i=0;i<len;i++)
		if (s1[i]!=s2[i])return i;
	if (len1==len2)return -1;
	return len;
}
int main(){
	ifstream fin(f1.c_str());
	ofstream fout(f3.c_str());
	char ch;
	string s1="",s2="",s3;
	f(s1);
	fin.close();
	fin.clear();
	fin.open(f2.c_str());
	f(s2);
	fin.close();
	fin.clear();
	int position=check(s1,s2);
	if (position==-1)fout<<"Same"<<endl;
	else fout<<"Different "<<position<<endl;
	fout.close();
	fout.clear();
	return 0;
}