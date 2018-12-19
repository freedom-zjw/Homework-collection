#-*- coding:utf-8 -*-
import os
from operator import itemgetter

def getData(filepath):
    file = open(filepath,"r")
    lines = file.readlines()
    file.close()
    lines = list(map(lambda x:x[x.index('[')+1:x.rindex(']')],lines))
    matrix = []
    for i in range(len(lines)):
        if i<3:
            matrix.append([int(lines[i])])
        else:
            l = lines[i].split(', ')
            l = list(map(lambda x:int(x),l))
            matrix.append(l)
    return matrix

def plus(A,B):
    C = [0,0] #初始化行数和列数
    d = dict()
    hash = []
    for i in range(3,len(A)):
        l = A[i]
        key = (l[0],l[1]) #以(x,y)为关键字判断该坐标是否出现过
        if key not in d: #没有出现过则新增一个
            d[key] = l[2]
            hash.append((l[0],l[1]))
        else: #出现过则该坐标的值 加上新的值
            d[key] += l[2]

    #B的处理同A
    for i in range(3,len(B)):
        l = B[i]
        key = (l[0],l[1])
        if key not in d:
            d[key] = l[2]
            hash.append((l[0], l[1]))
        else:
            d[key] += l[2]
            if d[key] == 0: #注意这里是删除值为0的元素
            	del d[key]
            	hash.remove((l[0], l[1]))
    hash = sorted(hash, key=itemgetter(0,1))
    for item in hash:
    	C[0] = max(C[0],item[0]+1)
    	C[1] = max(C[1],item[1]+1)
    C[0] = "[" + str(C[0]) + "]";
    C[1] = "[" + str(C[1]) + "]";
    C.append("[" + str(len(d)) + "]")
    for key in hash:
        C.append("[" + str(key[0]) + ", " + str(key[1]) + ", " +str(d[key]) + "]")
    C = '\n'.join(C)
    return C


if __name__ == "__main__":
    currentpath = os.getcwd() + os.sep + "final data" + os.sep
    A = getData(currentpath + "A.txt")
    B = getData(currentpath + "B.txt")
    C = plus(A,B)
    writefile = open(currentpath + "C.txt","w")
    writefile.write(C)
    writefile.close()

