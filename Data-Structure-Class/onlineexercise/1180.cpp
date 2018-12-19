#include<iostream>
using namespace std;
#define MAX 250
int main(){
    //freopen("1.txt", "r", stdin);
    int b,e,i,j;
    char text[MAX];
    while(1){
        cin.getline(text,MAX);
        i=j=b=e=0;
        if(text[i]=='-')break;
        while(text[i]!=' '){
            b*=10;
            b+=int(text[i]-'0');
            i++;
        }
        i++;
        while(text[i]!=' '){
            e*=10;
            e+=int(text[i]-'0');
            i++;
        }
        i++;
        b+=i;e+=i;
        while(1){
            if(i==b)break;
            if(text[i]=='<'){
                i++;
                if(text[i]=='/'){
                    while(text[i]!='>'){
                        i++;
                        j--;
                    }
                    i++;
                    j--;
                }
                else{
                    text[j++]='<';
                    while(text[i]!='>')text[j++]=text[i++];
                    text[j++]=text[i++];
                }
            }
            else i++;
        }
        while(1){
            if(i==e)break;
            text[j++]=text[i++];
        }
        int times=0;
        while(1){
            if(text[i]==0)break;
            if(text[i]=='<'){
                i++;
                if(text[i]=='/'){
                    if(times==0){
                        text[j++]='<';
                        while(text[i]!='>')text[j++]=text[i++];
                        text[j++]=text[i++];
                    }else{
                        times--;
                        while(text[i]!='>')i++;
                        i++;
                    }
                }else{
                    times++;
                    while(text[i]!='>')i++;
                    i++;
                }
            }else i++;
        }
        text[j]=0;
        cout<<text<<endl;
    }
    return 0;
}