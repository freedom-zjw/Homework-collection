#include <iostream>
#include <set>
using namespace std;
class SetOperation{
	public:
		SetOperation(int a[],int sizeA,int b[],int sizeB){
			setA.clear();
			setB.clear();
			//for (int i=0;i<sizeA;i++)setA.insert(a[i]);
			//for (int i=0;i<sizeB;i++)setB.insert(b[i]);	
			setA.insert(a,a+sizeA);
			setB.insert(b,b+sizeB);
		}
		set<int> Union(){
			set<int> setC;
			setC.clear();
			for (set<int>::iterator it = setA.begin(); it != setA.end(); it++)
                   setC.insert(*it);
   		    for (set<int>::iterator it = setB.begin(); it != setB.end(); it++)
                   setC.insert(*it);
			return setC;
		}
		set<int> Intersetion(){
			set<int> setC;
			setC.clear();
			for (set<int>::iterator it = setA.begin(); it != setA.end(); it++){
                   set<int>::iterator it_=setB.find(*it);
                   if (it_!=setB.end())setC.insert(*it_);
			}
			return setC;
		}
	private:
		set<int> setA;
		set<int> setB;

};

void printSet(const set<int> &s) {
         bool first = true;
         for (set<int>::iterator it = s.begin(); it != s.end(); it++) {
                   if (!first) cout << " ";
                   else first = false;
                   cout << *it;
         }
         cout << endl;

}

int main(int argc, char *argv[]) {
	int a[7] = {2,3,5,6,8,9,10};
	int b[4] = {2,3,5,6};
	SetOperation s1(a, 7, b, 4);		
	printSet(s1.Union());
	printSet(s1.Intersetion());
	return 0;
}