#include <iostream>
#include <map>
using namespace std;
class Position {
 public:
         int x;
         int y;
};
class Robot {
public:
         Robot(); /* default constructor, initialize at (0,0) */
         Robot(Position pos);/* initialize at pos */
         void Move(char Dir); 
		 /* Dir could be 'N', 'E', 'S', 'W', 
		 for other characters, the robot don’t move */
         Position GetPosition();/* return current position */
private:
         Position currentPos;
};
Robot::Robot(){
    currentPos.x=currentPos.y=0;//机器人位置初始化为(0,0) 
}
Robot::Robot(Position pos){
    currentPos=pos;//机器人位置初始化为pos 
}
void Robot::Move(char Dir){
    if (Dir=='N')currentPos.y++;//N是向上走即纵坐标+1 
    else if (Dir=='E')currentPos.x++;//E是向右走即横坐标+1 
    else if (Dir=='W')currentPos.x--;//W是向左走即横坐标-1 
    else if (Dir=='S')currentPos.y--;//S是向下走即纵坐标-1 
}
Position Robot::GetPosition(){
    return currentPos;//返回当前坐标 
}            
int main() {
    Position c;
    c.x = 0;
    c.y = 1;
    Robot a;
    cout << a.GetPosition().x << ' ' << a.GetPosition().y << endl;
    Robot b( c );
    cout << b.GetPosition().x << ' ' << b.GetPosition().y << endl;
    b.Move('E');
    cout << b.GetPosition().x << ' ' << b.GetPosition().y << endl;
    b.Move('N');
    cout << b.GetPosition().x << ' ' << b.GetPosition().y << endl;
    b.Move('W');
    cout << b.GetPosition().x << ' ' << b.GetPosition().y << endl;
    b.Move('S');
    cout << b.GetPosition().x << ' ' << b.GetPosition().y << endl;

    b.Move('s');
    cout << b.GetPosition().x << ' ' << b.GetPosition().y << endl;
    return 0;
}    