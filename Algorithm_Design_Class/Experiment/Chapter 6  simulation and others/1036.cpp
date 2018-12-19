#include<cstdio>
#include<cstring>
#include<cstdlib>
#include<iostream>
using namespace std; 
char keys[11];
char flag[11];//表示这一列是否已经被处理过
char line[101];//密文
char plaintext[101];//明文
void Decrypt(){//解密 
    int i,index,column, count,keysLength, lineLength;
    char c;
    memset(flag, 0, sizeof(flag));
    lineLength=strlen(line);//密文总字符数
    keysLength=strlen(keys);//列数
    count=lineLength/keysLength;
    plaintext[lineLength]=0;
    
    //找出最小字母 
    for(column=0;column<keysLength;column++){
        c='Z'+1;
        for(i=0;i<keysLength;i++)
            if(flag[i]==0 && keys[i]<c)
                c=keys[i],index=i;
        flag[index]=1;
        for(i=0;i<count;i++){
            //明文的第index列
            plaintext[keysLength*i + index]=line[count*column+i];
        }
    }    
}
int main(){
    while(1){
        gets(keys);
        if(strcmp(keys,"THEEND")==0) break;
        gets(line);
        Decrypt();
        printf("%s\n", plaintext);
    }
}               