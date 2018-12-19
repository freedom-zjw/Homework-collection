#include<iostream>
using namespace std;
int main(){
    int times;
    cin >> times;
    while(times--){
        int pointCount;     //城市的个数
        int delPointNum = 0;    //已经能配对好的城市个数
        cin >> pointCount;
        int edgeArray[pointCount][2];   //保存相连的两个城市
        int pointLigature[pointCount];  //连接每个城市的度
        for(int i = 0; i < pointCount; i++)pointLigature[i] = 0;
        for(int i = 0; i < pointCount - 1; i++){
            int startPoint, endPoint;
            cin >> startPoint >> endPoint;
            edgeArray[i][0] = startPoint;
            edgeArray[i][1] = endPoint;
            pointLigature[startPoint - 1]++;
            pointLigature[endPoint - 1]++;
        }
        if(pointCount % 2 == 1) {cout << "No" << endl;continue;}    //如果城市的个数是奇数个直接判断不能配对
        int maxDelTime = pointCount;
        while(maxDelTime--){     //如果存在着度为1的顶点（城市），每次循环都会配对（删掉）一对
            int delPoint = 0;   //找出度为1的顶点
            for(; delPoint< pointCount; delPoint++){
                if(pointLigature[delPoint] == 1){
                    delPointNum += 2;
                    break;
                }
            }
            for(int i = 0; i < pointCount - 1; i++){     //找出与度为1的顶点相连的顶点   
                if(edgeArray[i][0] == delPoint + 1) {delPoint = edgeArray[i][1]; break;}
                if(edgeArray[i][1] == delPoint + 1) {delPoint = edgeArray[i][0]; break;}
            }
            for(int i = 0; i < pointCount - 1; i++){     //以与度为1的顶点相连的顶点为中心，删掉与它相连的路径           
                if(edgeArray[i][0] == delPoint || edgeArray[i][1] == delPoint){   
                    int point;
                    point = edgeArray[i][0] - 1;
                    pointLigature[point]--;
                    point = edgeArray[i][1] - 1;
                    pointLigature[point]--;
                    edgeArray[i][0] = 0;
                    edgeArray[i][1] = 0;
                }
            }
        }
        if(delPointNum >= pointCount) cout << "Yes" << endl;
        else cout << "No" << endl;
    }
    return 0;
}