#include<iostream>
using namespace std;
template <typename T, int capacity>
class Array{
	public:
		Array(); // Construct an empty Array.
		~Array(); // Deconstructor
	    bool empty(); // Return true if the array is empty.
	    void push(T value); // Push back an element into the array.
		T pop(); // Remove the last element of the array and returns it.
	    int size(); // Return the number of elements in the array.
		T& operator[] (int index);  // Return the element by index.
	private:
		T* elements; // Point to an array that stores elements.
		int num; // The number of the elements in the Array.
};
class ArrayException{
	public:
		ArrayException(const char *msg);
		const char *what() const;
	private:
		const char *message;
};
template <typename T, int capacity>
Array<T,capacity>::Array(){
	elements=new T[capacity];
	num=0;
}
template <typename T, int capacity>
Array<T,capacity>::~Array(){
	delete[] elements;
	num=0;
}
template <typename T, int capacity>
bool Array<T,capacity>::empty(){
	return num==0;
}
template <typename T, int capacity>
void Array<T,capacity>::push(T value){
	if (num==capacity)
		throw ArrayException("Array Full Exception");
	elements[num++]=value;
}
template <typename T, int capacity>
T Array<T,capacity>::pop(){
	if (!num)
		throw ArrayException("Array Empty Exception");
	return elements[--num];
}
template <typename T, int capacity>
int Array<T,capacity>::size(){
	return num;
}
template <typename T, int capacity>
T& Array<T,capacity>::operator[](int index){
	if (index>=num||index<0)
		throw ArrayException("Out of Range Exception");
	return elements[index];
}
ArrayException::ArrayException(const char *msg){
	message=msg;
}
const char* ArrayException::what()const{
	return message;
}
void test(){
	Array<char,2> charArray;
	try {
		charArray.push('A');
	}
	catch (ArrayException ex){
		cout << ex.what() << endl;
	}
	try {
		cout << charArray[0] << endl;

	} 
	catch (ArrayException ex) {
		cout << ex.what() << endl;
	}

	try {
		cout << charArray.pop() << endl;
	}
    catch (ArrayException ex){
		cout << ex.what() << endl;
	}
}
int main(){
	test();
	return 0;
}
