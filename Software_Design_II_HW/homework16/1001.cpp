#include<iostream>
#include<algorithm>
#include<vector>
using namespace std;
struct Student{
	string name;
	int score;
	Student(string name_,int score_){
		name=name_;
		score=score_;
	}
	bool operator < (const Student B)const{
		return score<B.score;
	}
};
vector<Student>myVector;
int main(){
	int n,m,x;
	string s;
	cin>>n>>m;
	for (int i=1;i<=n;i++){
		cin>>s>>x;
		myVector.push_back(Student(s,x));
	}
	sort(myVector.begin(),myVector.end());
	for (int i=1;i<=m;i++){
		cin>>x;
		x--;
		cout<<myVector[x].name<<" "<<myVector[x].score<<endl;
	}
	return 0;
}