#include<iostream>
using namespace std;
class MyVector{
	public:
         MyVector(int = 0, int = 0, int = 0);
         MyVector(const MyVector &);
         void display();
         friend MyVector add(MyVector &v1, MyVector &v2);
         friend MyVector sub(MyVector &v1, MyVector &v2);
         friend int dot(MyVector &v1, MyVector &v2);
         friend MyVector cross(MyVector &v1, MyVector &v2);
	private:
         int x, y, z;
};
MyVector::MyVector(int x,int y,int z){//构造函数，初始化
	this->x=x;
	this->y=y;
	this->z=z;
}
MyVector::MyVector(const MyVector &myVector){//构造函数，初始化x,y,z的值与另一个对象的对应值一样 
	x=myVector.x;
	y=myVector.y;
	z=myVector.z;
}
void MyVector::display(){//输出向量 
	cout<<"("<<x<<","<<y<<","<<z<<")"<<endl;
}
MyVector add(MyVector &v1,MyVector &v2){//向量加法，返回一个MyVector对象 
	return MyVector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
}
MyVector sub(MyVector &v1,MyVector &v2){//向量减法，返回一个MyVector对象 
	return MyVector(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
}
MyVector cross(MyVector &v1,MyVector &v2){//向量叉乘，返回一个MyVector对象 
	return MyVector(v1.y*v2.z-v2.y*v1.z,v1.z*v2.x-v2.z*v1.x,v1.x*v2.y-v2.x*v1.y);
}
int dot(MyVector &v1,MyVector &v2){//向量点乘 
	return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
}
int main(){
		 MyVector a;
         MyVector b(1);
         MyVector c(2,3);
         MyVector d(3,4,5);
         MyVector e(c);
 
         MyVector x = add(a, b);
         x.display();

         MyVector y = sub(b, c);
         y.display();

         MyVector z = cross(c, d);
         z.display();

         int w = dot(d, e);
         cout << w << endl;
         
		 cout<<endl; 
         MyVector f(-650,1020); 
         MyVector g(-9820,-12);
         MyVector k=add(f,g);
         k.display();
         
		 k=sub(f,g);
         k.display();
         
         k=cross(f,g);
         k.display();
         
         cout<<dot(f,g)<<endl; 
         return 0;
}