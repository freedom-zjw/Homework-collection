#include <iostream>
#include <algorithm>
#include <cstdio>
#include <cstring>
using namespace std;
int main(){
    int n;
    char ranks[125][6];
    char init[6] = "ABCDE";
    int i = 0;
    do {
        strcpy(ranks[i++], init);
    }while (next_permutation(init, init+5));

    while (cin >> n && n != 0) {
        char Str[110][6];
        getchar();  
        for (i = 0; i < n; i++) {
            gets(Str[i]);
        }
        int min = 999999;
        char res[6];
        for (i = 0; i < 120; i++) {
            int cnt = 0;
            for (int t = 0; t < n; t++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = j+1; k < 5; k++) {
                        if (strchr(Str[t],ranks[i][j]) > strchr(Str[t], ranks[i][k]))
                            cnt++;
                    }
                }
            }
            if (cnt < min) {
                min = cnt;
                strcpy(res,ranks[i]);
            }
        }
        cout << res << " is the median ranking with value " << min << "." << endl;
    }
    return 0;
}
