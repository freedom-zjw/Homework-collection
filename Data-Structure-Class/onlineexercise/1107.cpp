
#include <iostream>
#include <vector>
#include <cstring>
#include <algorithm>

std::string s;
std::string factors[100];
std::vector<std::string> ans;
int sum;
int count;
bool location[10];
bool digit[10];

void getSum() {
   int weight = 1;
   sum = 0;
   for (int c = factors[count - 1].size() - 1; c >= 0; --c) {
      sum += (factors[count - 1][c] - '0') * weight;
      weight *= 10;
   }
}

void init() {
   count = 0;
   factors[0] = "";
   for (int c = 0; c < s.size(); ++c) {
       if (s[c] == ' ') {
          factors[++count] = "";
       } else {
          factors[count] += s[c];
       }
   }
   count++;
   for (int c = 1; c < count - 1; ++c)
      std::reverse(factors[c].begin(), factors[c].end());

   memset(location, false, sizeof(location));
   memset(digit, false, sizeof(digit));
   if (!ans.empty())
     ans.clear();
   getSum();
}

void check() {
   for (int c = 0; c < 11; ++c) {
     int num[10] = {0};    
     for (int d = 1; d <= count - 2; ++d) {
       if (c < factors[d].size()) {
         if (num[factors[d][c] - '0'] == 1)
           return;
         else
           num[factors[d][c] - '0'] = 1; 
       }
     }
   }
     
   int temp = 0;
   for (int d = 1; d < count - 1; ++d) {
      int aaa = 0;
      int weight = 1;
      for (int c = 0; factors[d][c] != '\0'; ++c) {
         aaa += (factors[d][c] - 48) * weight;
         weight *= 10;
      }
      temp += aaa;
   }

   if (temp == sum) {
     std::string ss = factors[0];
     for (int c = 1; c < count - 1; ++c) {
        ss  += " ";
        bool pre = false;
        std::string sss = factors[c];
        std::reverse(sss.begin(), sss.end());
        for (int d = 0; sss[d] != '\0'; ++d) {
           if (sss[d] > '0') {
             ss += sss[d];
             pre = true;
           } else {
             if (pre)
               ss += sss[d];
           }
        }
        if (pre == false)
          ss += '0';
     }
     ans.push_back(ss);
   }
}

std::string insert(std::string ss, int loc, int dig) {
   char a = 48 + dig;
   std::string temp = "";
   if (loc == 0) {
     temp += a;
     temp += ss;
   } else {
     int c;
     for (c = 0; c < loc && ss[c] != '\0'; ++c)
        temp += ss[c];
     temp += a;
     for (; ss[c] != '\0'; ++c)
        temp += ss[c];
   }
   return temp;
}

void dfs(int num) {
   if (num == count - 1) {
     check();
   } else {
     std::string temp = factors[num];
     for (int c = 0; c <= temp.size(); ++c) {
        if (location[c] == true)
           continue;
        location[c] = true;
        for (int d = 0; d <= 9; ++d) {
           if (digit[d] == true)
             continue;
           digit[d] = true;
           factors[num] = insert(temp, c, d);
           dfs(num + 1);
           digit[d] = false;
        }
        location[c] = false;
     }
     factors[num] = temp;
   }
}

void print() {
   if (ans.empty()) {
     std::cout << factors[0] << " 0" << std::endl;
   } else {
     std::sort(ans.begin(), ans.end());
     for (int c = 0; c < ans.size(); ++c)
        std::cout << ans[c] << std::endl;
   }
}

int main() {
  while (1) {
      getline(std::cin, s);
      if (s == "0")
        break;

      init();
      dfs(1);
      print();
  }
  return 0;
}