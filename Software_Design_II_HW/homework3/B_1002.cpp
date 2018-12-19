#include <iostream>
#include <string>
#include <iomanip>
 
using namespace std;
 
class Date {
    public:
        Date();
        Date(const Date&);
        Date(const string&);
        Date(int, int = 2, int = 29);
        void setDate(const string&);
        void printDate() const;
        int getYear() const;
        int getMonth() const;
        int getDay() const;
    private:
        bool checkFormat(const string&) const;
        bool validate();
        int year, month, day;
        bool pass;
};
 
/******** this part should be submitted ********/
Date::Date() {
    year=2006;
    month=day=1;
    validate();
}
 
Date::Date(const Date& anotherDate) {
    year=anotherDate.year;
    month=anotherDate.month;
    day=anotherDate.day;
    pass=anotherDate.pass;
    validate();
}
 
Date::Date(const string& input) {
    setDate(input);
}
 
Date::Date(int year, int month, int day) {
    this->year=year;
    this->month=month;
    this->day=day;
    validate();
}
 
void Date::printDate() const {
    if (!pass)cout<<"Invalid Date"<<endl;
    else {
    	//控制输出格式，不足位补前导0 
        if (year<1000)cout<<"0";
        if (year<100)cout<<"0";
        if (year<10)cout<<"0";
        cout<<year<<"-";
        if (month<10)cout<<"0";
        cout<<month<<"-";
        if (day<10)cout<<"0";
        cout<<day<<endl;
    }
}
 
int Date::getYear() const {
    return year;
}
 
int Date::getMonth() const {
    return month;
}
 
int Date::getDay() const {
    return day;
}
 
void Date::setDate(const string& input) {
    
    if(checkFormat(input)){
        year=month=day=0;//取出字符串中相应位代表的数字赋值给相应变量 
        for (int i=0;i<4;i++)
            year=year*10+(input[i]-'0');
        for (int i=5;i<7;i++)
            month=month*10+(input[i]-'0');
        for (int i=8;i<10;i++)
            day=day*10+(input[i]-'0');
        validate();
    }
    else{
        year=month=day=233;
        validate();
    }
}
 
bool Date::checkFormat(const string& input) const {
    if(input.length()!=10)return false;//正确的输入格式长度一定为10位 
    else{
        for (int i=0;i<4;i++)
            if(input[i]<'0'||input[i]>'9')return false;
        
        if(input[4]!=':')return false;
        
        for (int i=5;i<7;i++)
             if(input[i]<'0'||input[i]>'9')return false;
        
        if(input[7]!=':')return false;
        
        for (int i=8;i<10;i++)
            if(input[i]<'0'||input[i]>'9')return false;
    }
    return true;
}
 
bool Date::validate() {
    pass=true;
    if(month<1||month>12)pass=false;
    else {
        switch(month){
            case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                if(day<0||day>31)pass=false;
                break;
            case 2:
                if(day<0||day>28)pass=false;
                break;
            case 4:case 6:case 9:case 11:
                if(day<0||day>30)pass=false;
                 break;
            default:
                pass=false;
                break;
        }
        if ((year%4==0&&year%100!=0)||year%400==0){//注意闰年的特殊判断 
            if (month==2){
                if (day<=29&&day>0)pass=true;
                else pass=false;
            }
        }
    }
}                      
/******** this part should be submitted ********/
 
int main() {
	int T;
	int flag;
	Date* myDate;
	
	string dateString;
	int year, month, day;
	
	cin >> T;
	while (T--) {
		cin >> flag;
		if (flag == 1) {
			cin >> dateString;
			myDate = new Date(dateString);			
		}
		else if (flag == 2) {
			cin >> year;
			myDate = new Date(year);
		}
		else if (flag == 3) {
			cin >> year >> month;
			myDate = new Date(year, month);
		}
		else if (flag == 4) {
			cin >> year >> month >> day;
			myDate = new Date(year, month, day);
                }
                else if (flag == 5){
                        myDate = new Date(2016,3,7);
                        myDate -> printDate();
                        cin >> dateString;
                        myDate->setDate(dateString);
                        cout << setw(1);
                        cout << myDate->getYear() << ' ';
                        cout << myDate->getMonth() <<' ';
                        cout << myDate->getDay() << endl;
                }
                else if (flag == 6){
                        myDate = new Date();
                        myDate ->printDate();
                        delete myDate;
                        cin >> year >> month >> day;
                        myDate = new Date( Date(year,month,day) );

                        cout << setw(1);
                        cout << myDate->getYear() << ' ';

                        cout << myDate->getMonth() <<' ';
                        cout << myDate->getDay() << endl;
		}
 
		myDate->printDate();
                delete myDate;
	}
	
	return 0;
 
}
 