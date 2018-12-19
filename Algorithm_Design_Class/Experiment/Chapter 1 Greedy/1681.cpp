#include<iostream>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
string finmin(int x){
	string s="";
	int num[10] = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
	if (x<=7){
       rep(i,1,9)
       	 if (x==num[i]){
       	 	s+=char(i+48);
       	    break;	
       	 }
    }
    else {
    	int eight=x/7;
        x%=7;
        if (x){
        	int tem;
         	if (x==1)tem=9;
            else{
            	rep(i,1,9)
             		if (x==num[i]){
               			tem=i;
                  		break;
                    }
            }
            bool start=true;
            while (eight){
            	bool flag = true;
            	int i;
             	if (start) i = 1;
              	else i = 0;
               	start=false;
                for (i;i<=tem;i++){
                	rep(j,0,7)
                 		if (num[i]+num[j]==x+num[8]){
                        	s+=char(i+48);
                            tem=j;
                            flag=false;
                            eight--;
                            break;
                         }
                        if (!flag)break;
                    }
                    if (flag){
                        s+=char(tem+48);
                        break;
                    }
                    x=num[tem];
                    if (!eight){
                        s+=char(tem+48); 
                        break;
                    }
            }
     	}
        rep(i,1,eight)
        	s+="8";
     }
     return s;  
}
string finmax(int x){
	string s="";
	if (x%2==1){
		x-=3;
		s="7";
	}
	rep(i,1,x/2)s+="1";
	return s;
}
int main(){
	int T,n;
	cin>>T;
	rep(t,1,T){
		cin>>n;
		string minn=finmin(n);
		string maxx=finmax(n);
		cout<<minn<<" "<<maxx<<endl;
	}
	return 0;	
}