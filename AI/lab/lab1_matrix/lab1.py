#-*- coding:utf-8 -*-
import os
import math

def pre_handle(filepath):
    file = open(filepath,"r")
    lines = file.readlines()
    file.close()
    #提取每一篇文章的单词，一遍文章的单词作为一个list
    lines = list(map(lambda x:x[x.rindex('\t')+1:-1].split(),lines))
    #Hash统计每个单词是第几次出现的，num统计每个单词出现在多少篇文章中
    Hash, num= dict(), dict()  
    cnt, row = 0, 0
    for line in lines:
        row += 1
        appearnum = set() #appearnum用来判断单词在当前文章中是否已经出现过
        for word in line:
            if word not in Hash:
                Hash[word] = cnt
                cnt = cnt + 1
                num[word] = 1
                appearnum.add(word)
            else:
                if word not in appearnum:
                    num[word] = num[word] + 1
                    appearnum.add(word)
    for word in num:
        num[word] = math.log(row/(1+num[word])) #将num转变为IDF值
    Hash = sorted(Hash.items(), key=lambda x:x[1]) #将单词按照出现顺序排序
    return [lines,Hash,num]

def getContent(lines,Hash,num):
    content1 = ""  #onehot matrix
    content2 = [0,0,0]  #onehot three triple matrix [maxrow,maxcol,valnum]
    content3 = ""  #TF matrix
    content4 = ""  #TF-IDF matrix
    row = -1
    for line in lines: #遍历每一篇文章
        l_oh, l_TF, l_TFIDF = [], [], [] #分别记录one-hot,TF,TFIDF当前行的值
        col = -1; row += 1
        current_line_val = 0
        for word in Hash: #遍历词汇表
            col += 1
            word = word[0]
            if word in line: #如果该文章中包含单词word
                content2[0] = row + 1 if row + 1 > content2[0] else content2[0] #更新三元组顺序表的行
                content2[1] = col + 1 if col + 1> content2[1] else content2[1]  #更新三元组顺序表的列
                content2[2] += 1 #数值个数+1
                content2.append("["+str(row) + ", " + str(col) + ", " + "1]") #增加一个三元组到顺序表中
                #one-hot矩阵新增加一个1，TF，IDTF矩阵都新增加该词在该行出现的次数，其中TFIDF先乘上一个IDF值
                l_oh.append(1)
                word_num = line.count(word)
                l_TF.append(word_num)
                l_TFIDF.append(word_num*num[word])
                #统计该文章中有多少个非0值，用于之后计算TF和IDTF
                current_line_val += word_num
            else: #如果该文章中不包含单词word，则one-hot，TF，TFIDF矩阵都新增加一个0
                l_oh.append(0)
                l_TF.append(0)
                l_TFIDF.append(0)
        #将一篇文章的one-hot，TF, TFIDF,one-hot三元组结果转变成一行字符串加到相应的content中，方便后面写入文件
        #TF和IDF要除以一个当前行的单词总数，结果保留6位小数
        content1 += ' '.join(list(map(lambda x:"0" if x==0 else "1",l_oh))) + '\n'  #one-hot
        content3 += ' '.join(list(map(lambda x:"0" if x==0 else str(round(x/current_line_val,6)),l_TF))) + '\n' #TF
        content4 += ' '.join(list(map(lambda x:"0" if x==0 else str(round(x/current_line_val,6)),l_TFIDF))) + '\n' #TFIDF
    content2 = '\n'.join( list(map(lambda x:x if isinstance(x,str) else "["+str(x)+"]",content2)))  #one-hot 三元顺序表
    
    return [content1,content2,content3,content4] 

def writeData(floderpath,content1,content2,content3,content4):
    onehot = open(floderpath + os.sep + "onehot.txt","w")
    smatrix = open(floderpath + os.sep + "smatrix.txt","w")
    TF = open(floderpath + os.sep + "TF.txt","w")
    TFIDF = open(floderpath + os.sep + "TFIDF.txt","w")
    onehot.write(content1)
    smatrix.write(content2)
    TF.write(content3)
    TFIDF.write(content4)
    onehot.close()
    smatrix.close()
    TF.close()
    TFIDF.close()

if __name__ == "__main__":
    currentpath = os.getcwd() + os.sep + "final data"
    filepath = currentpath + os.sep + "text.txt"
    [lines,Hash,num] = pre_handle(filepath)
    [content1,content2,content3,content4] = getContent(lines,Hash,num)
    writeData(currentpath,content1,content2,content3,content4)
