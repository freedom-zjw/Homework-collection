#include <iostream>
#include <cstdio>
#include <cstring>
 
using namespace std;
 
///////////////////////////////////////////////////////////////////////////////////////////////////
class SafeIntArray{
	public:
		SafeIntArray(int n){//构造函数，生成一个长度为n，初始值为0的数组 
			size=n;
			a=new int[n];
			for (int i=0;i<n;i++)a[i]=0;
		}
		virtual ~SafeIntArray(){//析构函数 
			delete [] a;
			cout<<1<<endl;
		}
		virtual void set(int index,int value){//set a[index]=value; 
			if (index>=size||index<0)//判断越界 
				cout<<"Outside the boundary!"<<endl;
			else a[index]=value;
		}
		virtual int get(int index){// get the value of a[index]
			if (index>=size||index<0){//判断越界 
				cout<<"Outside the boundary!"<<endl;
				return -1;
			}
			else return a[index];
		}
	private:
		int size;
		int *a;
};
class IndexIntArray:public SafeIntArray{
	public:
		//构造函数，创建一个下标在[l,r]的数组 
		IndexIntArray(int l,int r):SafeIntArray(r-l+1){//先创建基类对象 
			this->l=l;
			this->r=r;
			
		}
		~IndexIntArray(){
			cout<<2<<endl;
		}
		virtual void set(int index,int value){//set a[index]=value; 
			if (index<l||index>r)//判断越界 
				cout<<"Outside the boundary!"<<endl;
			else {
				index=index-l;
				SafeIntArray::set(index,value);//调用基类的set方法使a[index]=value 
			}
		}	
		virtual int get(int index){// get the value of a[index]
			if (index<l||index>r){//判断越界 
				cout<<"Outside the boundary!"<<endl;
				return -1;
			}
			else {
				index=index-l;
				return SafeIntArray::get(index);//调用基类的get方法获取a[index] 
			}
		}
	private:
		int l,r;
};

///////////////////////////////////////////////////////////////////////////////////////////////////
 
int main(int argc, char** argv) {
    
    int test;
    cin >> test;
    while (test --) {
        int size, n;
        cin >> size;
        SafeIntArray sia(size);
        // test SafeIntArray::set(int,int) of SafeIntArray
        cin >> n;
        while (n --) {
            int index, data;
            cin >> index >> data;
            sia.set(index, data);
        }
        // test SafeIntArray::get(int) of SafeIntArray
        cin >> n;
        while (n --) {
            int index;
            cin >> index;
            int value = sia.get(index);
            if (value != -1) {
                cout << value << endl;
            }
        }
 
        int begin, end;
        cin >> begin >> end;
        IndexIntArray iia(begin, end);
        // test IndexIntArray::set(int,int) of IndexIntArray
        cin >> n;
        while (n --) {
            int index, data;
            cin >> index >> data;
            iia.set(index, data);
        }
        // test IndexIntArray::get(int) of IndexIntArray
        cin >> n;
        while (n --) {
            int index;
            cin >> index;
            int value = iia.get(index);
            if (value != -1) {
                cout << value << endl;
            }
        }
    }
 
    return 0;
}