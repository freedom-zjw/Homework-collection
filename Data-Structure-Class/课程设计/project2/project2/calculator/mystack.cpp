#include"mystack.h"
template<typename T>
Mystack<T>::Mystack(){
    top_pointer=NULL;
    num=0;
}
template<typename T>
bool Mystack<T>::empty() {
    return num==0;
} 
template<typename T>
T Mystack<T>::top() {
    if (num>0)return top_pointer->data;
    else throw 0;
} 
template<typename T>
void Mystack<T>::push(T value) {    
	node *no=new node(value,top_pointer);
	top_pointer=no;
	num++;
}
template<typename T>
T Mystack<T>::pop() {
	if (num>0){
		T value=top_pointer->data;
		node *tmp=top_pointer;
		top_pointer=top_pointer->next;
		delete tmp;
		num--;
		return value;
	}
    else  throw 0;
}
template<typename T>
int Mystack<T>::size() {
    return num;
}
template<typename T>
Mystack<T>::~Mystack(){
    while (top_pointer!=NULL){
    	node *tmp=top_pointer;
    	top_pointer=top_pointer->next;
    	delete tmp;
	}
}