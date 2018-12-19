#include <iostream>
#include <iomanip>
#include <cmath>
using namespace std;
class Bank{
	public:
		Bank(string _name){
			name = _name;
		}
		string getName()const{
			return name;
		}
		virtual void printMessage() const = 0;
		virtual void deposit(double m) = 0;//存钱 
		virtual void withdraw(double m) = 0;//取钱 
		virtual double getMoney() const = 0;
	protected:
		string name;
};
class ICBC:public Bank{
	public:
		ICBC(double _money = 0):Bank("ICBC"){
			money = _money;
		}
		void printMessage()const{
			cout << "Welcome to Industrial and Commercial Bank of China" << endl;
		}
		void deposit(double m){
			money += m;
		}
		void withdraw(double m){
			if (money > m) money -= m;
			else money = 0;
		}
		double getMoney() const{
			return money;
		}
	private:
		double money;
};
class ABC:public Bank{
	public:
		ABC(double _money = 0):Bank("ABC"){
			money = _money;
		}
		void printMessage() const{
			cout << "Welcome to Agricultural Bank of China" << endl;
		}
		void deposit(double m){
			money += m;
		}
		void withdraw(double m){
			if (money > m) money -= m;
			else money = 0;
		}
		double getMoney() const{
			return money;
		}
	private:
		double money;
};
class AccountManager{
	public:
		AccountManager(int _size);
		~AccountManager();
		void setBank(int i, Bank *bank);
		Bank* getBank(int i);
		void deposit(Bank *bank, double money);
		void withdraw(Bank *bank, double money);
		void transfer(Bank *bank1, Bank *bank2, double money);
		void printAccount() const;	//print the welcome message, bank name and money for each array element
	private:
		Bank* *bankList;//bank array, where each element is a (Bank*) type
		int size;	//the length of the bank array
};
AccountManager::AccountManager(int x){//初始化size，申请一个size大小的bankList数组 
    size=x;
    bankList =new Bank*[size];
}
AccountManager::~AccountManager(){ //delete掉 对象数组bankList所占的空间 
	for (int i=0;i<size;i++)
		delete bankList[i];
    delete bankList;
}
void AccountManager::setBank(int i, Bank *bank){//bankList[i]为bank指针 
    bankList[i] = bank;
}
Bank* AccountManager::getBank(int i){ //返回 bankList[i]
    return bankList[i];
}
void AccountManager::deposit(Bank *bank, double money){
    bank->deposit(money); //调用传入的bank类的deposit函数参数为money 来实现对传入的bank的存钱 
}
void AccountManager::withdraw(Bank *bank, double money){
    bank->withdraw(money);//调用传入的bank类的withdraw函数参数为money 来实现对传入的bank的取钱 
}
void AccountManager::transfer(Bank *bank1, Bank *bank2, double money){//bank1转账money给bank2 
    int _money=bank1->getMoney();
    _money=_money<money?_money:money;//这里注意bank1的钱可能不足money 
    bank1->withdraw(_money);  
    bank2->deposit(_money);
}
void AccountManager::printAccount() const{//输出每个bankList[i]的信息 
    for( int i = 0 ; i < size ; i ++ ){
        bankList[i]->printMessage();
        cout<<bankList[i]->getName()<<" "<<bankList[i]->getMoney()<<endl;
    }
}

int main(){
	AccountManager accountManager(2);
 
	Bank *bank1 = new ICBC (10);
	accountManager.setBank(0, bank1);
	Bank *bank2 = new ABC(150);
	accountManager.setBank(1, bank2);
 
	accountManager.printAccount();
 
	accountManager.deposit(accountManager.getBank(0), 200);
	accountManager.deposit(accountManager.getBank(1), 200);
	accountManager.printAccount();
 
	accountManager.withdraw(accountManager.getBank(0), 188);
	accountManager.withdraw(accountManager.getBank(1), 600);
	accountManager.printAccount();
 
	accountManager.transfer(accountManager.getBank(0), accountManager.getBank(1), 100);
	accountManager.printAccount();
 
	return 0;
}
