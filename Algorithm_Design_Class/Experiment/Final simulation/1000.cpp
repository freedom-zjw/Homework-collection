#include<cstdio>
#include<iostream>
using namespace std;
int main() {
    int oldNumber, newNumber = 0;
    scanf("%d", &oldNumber);
    while(oldNumber != 0){
        newNumber *= 10;
        newNumber += oldNumber % 10;
        oldNumber /= 10;
    }
    printf("%d\n", newNumber);
    return 0;
}