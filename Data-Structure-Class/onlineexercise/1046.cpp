#include <iostream>
#include <cmath>
#include <algorithm>
using namespace std;
#define MAX_SIZE 300
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
struct Period {
  float average;
  int length;
  int end;
}periods[MAX_SIZE*MAX_SIZE];
int quarters, requested, minn;
int a[MAX_SIZE + 1]; 
bool operator< (const Period &a, const Period &b) {
  if (fabs(a.average - b.average) > 1e-6) return a.average > b.average;
  if (a.length != b.length) return a.length > b.length;
  return a.end < b.end;
}
int main() {
  int T;
  cin >> T;
  rep(run,1,T){
		cin >> quarters >> requested >> minn;
    	rep(i,1,quarters)cin >> a[i];
    	int count = 0;
    	rep(i,1,quarters){
      		int sum = 0;
      		rep(j,i,quarters){
        		sum += a[j];
        		if (j - i + 1 >= minn) {
          			periods[count].length = j - i + 1;
          			periods[count].end = j;
          			periods[count].average = (double)sum / (j - i + 1);
          			count++;
        		}
      		}
   		}
    	sort(periods, periods + count);
		cout << "Result for run " << run << ":" << endl;
    	for (int i = 0;i < requested && i < count; i++) {
     		 cout << periods[i].end - periods[i].length + 1;
      		 cout << "-" << periods[i].end << endl;
    	}
  }
  return 0;
}