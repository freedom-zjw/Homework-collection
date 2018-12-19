#include <fstream>
#include <iostream>
#include <string>
using namespace std;

int main(){
	string what1="";
	string what2="";

	ifstream fin1("File1.txt");
	char ch1;
	while(fin1.get(ch1)){
		what1+=ch1;
	}
	
	ifstream fin2("File2.txt");//or fin1.close();fin1.clear();fin1.open("File2.txt");
	char ch2;
	while(fin2.get(ch2)){
		what2+=ch2;
	}
	
	ofstream fout3("File3.txt");//file3
	if(what1!=what2){
		fout3<<"Different\n";
	}
	else{
		fout3<<"Same\n";
	}
	
}