#include<iostream>
using namespace std;
int main(){
    int times;
    cin >> times;
    while(times--){
        int num, goal;
        cin >> num >> goal;
        char charArray[num];
        for(int i = 0; i < num; i++){
            char newChar;
            cin >> newChar;
            charArray[i] = newChar;
        }
        for(int i = 1; i < num; i++){
            int j = i - 1;
            char temp;
            temp = charArray[i];
            while(j >= 0){
                if(charArray[j] == 'r') break;
                if(charArray[j] == 'y' && temp != 'r') break;
                if(charArray[j] == 'b' && temp == 'b') break;
                charArray[j+1] = charArray[j];
                j--;
            }
            charArray[j+ 1] = temp;
        }
        cout << charArray[goal - 1] << endl;
    }
    return 0;
}