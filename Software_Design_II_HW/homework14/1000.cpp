#include<iostream>
using namespace std;
#define Template template<typename T, int capacity>
Template class Stack{//capacity用来指明栈的容量大小 
	public:
    	Stack(); // Constructs an empty stack.
        bool empty();// Returns true if the stack is empty.
    	T peek(); // Returns the element at the top of the stack without removing it from the stack.
    	void push(T value); // Stores an element into the top of the stack.
    	T pop();// Removes the element at the top of the stack and returns it.
    	int size(); // Returns the number of elements in the stack.
    	~Stack();
	private:
    	T* elements; // Points to an array that stores elements in the stack.
    	int num;  // The number of the elements in the stack.
};
Template Stack<T,capacity>::Stack(){
    elements=new T[capacity];
    num=0;
}
Template bool Stack<T,capacity>::empty() {
    return num==0;
} 
Template T Stack<T,capacity>::peek() {
    if (num>0)return elements[num-1];
} 
Template void Stack<T,capacity>::push(T value) {    
	if (num<capacity)elements[num++]=value;
}
Template T Stack<T,capacity>::pop() {
	T value = peek();
    num--;
    return value;
}
 
Template int Stack<T, capacity>::size() {
    return num;
} 
Template Stack<T,capacity>::~Stack(){
    delete[] elements;
}
int main(){
    Stack<string,8> intStack;
    string x; 
	for (int i = 0; i < 8; i++) {
		cin>>x; 
		intStack.push(x);
	} 
	cout<<intStack.size()<<endl;
	while (!intStack.empty()) {
		cout << intStack.peek() << " ";
		intStack.pop();
	}
	cout << endl;
}