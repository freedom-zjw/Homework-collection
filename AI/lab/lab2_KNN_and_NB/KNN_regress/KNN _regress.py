# -*- coding:utf-8 -*-

import os
import csv
import numpy as np
from math import sqrt
from math import log
from os.path import join

class Regress:
    def __init__(self, floderpath):
        self.train_set = self.loadcsv(join(floderpath, "train_set.csv"))
        self.valid_set = self.loadcsv(join(floderpath, "validation_set.csv"))
        self.test_set = self.loadcsv(join(floderpath, "test_set.csv"), 2)
        self.MAXK = int(sqrt(len(self.train_set[0])))
        self.K = 11

    def loadcsv(self, filepath, Type=1):
        with open(filepath, 'r') as file:
            reader = csv.reader(file)
            words, label, label_name, cnt = [], [], [], 1
            st = 0 if Type == 1 else 1
            for row in reader:
                if cnt == 1:
                    cnt = 2
                    label_name = [row[i] for i in range(st+1, len(row))]
                    self.label_count = len(label_name)
                    continue
                words.append(row[st].split(' '))
                if Type == 1:
                    label.append([float(row[i]) for i in range(st+1, len(row))])
                else:
                    label.append([0.0 for i in range(st+1, len(row))])
        Set = set([x for line in words for x in line])
        return [words, label, label_name, Set]
    
    def Normalize(self, Matrix): #Z-SCORE 
        n = Matrix.shape[0]
        for i in range(n):
            #求标准差tstd， 均值teman
            tstd, tmean = Matrix[i, :].std(), Matrix[i, :].mean()
            #标准化
            Matrix[i, :] = (Matrix[i, :] - tmean)/(tstd + 1e-10) #加上1e-10防止标准差为0
            #变成单位向量
            len = np.linalg.norm(Matrix[i, :])
            if len > 1e-6:
                Matrix[i, :] = Matrix[i, :]/len
        Matrix = Matrix-np.min(Matrix)
        return Matrix

    def getOnehot(self, standard, train_set, test_set):# 求训练集和测试集的onehot矩阵
        word_set = list(train_set[3] | test_set[3])#单词集合为两者单词集合的并集
        train_matrix, test_matrix = [], []
        train_len, test_len = len(train_set[0]), len(test_set[0])
        #计算onehot
        for i in range(train_len):
            train_matrix.append([])
            for word in word_set:
                train_matrix[i].append(1.0 if word in train_set[0][i] else 0.0)
        for i in range(test_len):
            test_matrix.append([])
            for word in word_set:
                test_matrix[i].append(1.0 if word in test_set[0][i] else 0.0)
        train_matrix = np.array(train_matrix, dtype=float)
        test_matrix = np.array(test_matrix, dtype=float)
        #若选择了标准化，则对onehot矩阵进行标准化
        if standard == "normalization":
            train_matrix = self.Normalize(train_matrix)
            test_matrix = self.Normalize(test_matrix)
        return [train_matrix, test_matrix]

    def getTFIDF(self, standard, train_set, test_set):# 求训练集和测试集的TFIDF矩阵
        word_set = list(train_set[3])
        train_matrix, test_matrix = [], []
        train_len, test_len = len(train_set[0]), len(test_set[0])
        #计算IDF值
        IDF = dict()
        for i in range(train_len):
            s = set(train_set[0][i])
            for x in s:
                if x not in IDF:
                    IDF[x] = 1.0
                else:
                    IDF[x] += 1.0
        for x in IDF:
            IDF[x] = log(train_len/(1.0+IDF[x]))
        #计算训练集的TFIDF
        for i in range(train_len):
            train_matrix.append([])
            wordnum = 0
            for word in word_set:
                if word in train_set[0][i]:
                    cnt = train_set[0][i].count(word)
                    wordnum += cnt
                    train_matrix[i].append(cnt*IDF[word])
                else:
                    train_matrix[i].append(0.0)
            for j in range(len(train_matrix[i])):
                train_matrix[i][j] = train_matrix[i][j] / wordnum
        #计算需预测集的TFIDF
        for i in range(test_len):
            test_matrix.append([])
            wordnum = 0
            for word in word_set:
                if word in test_set[0][i]:
                    cnt = test_set[0][i].count(word)
                    wordnum += cnt
                    test_matrix[i].append(cnt*IDF[word])
                else:
                    test_matrix[i].append(0.0)
            for j in range(len(test_matrix[i])):
                if wordnum > 0:
                    test_matrix[i][j] = test_matrix[i][j] / wordnum
                else:
                    test_matrix[i][j] = 1e-10

        train_matrix = np.array(train_matrix, dtype=float)
        test_matrix = np.array(test_matrix, dtype=float)
        if standard == "normalization":
            train_matrix = self.Normalize(train_matrix)
            test_matrix = self.Normalize(test_matrix)
        return [train_matrix, test_matrix]
                
    def getdis(self, disType, v1, v2):
        if disType == "Manhattan":
            return sum(abs(v1-v2))
        elif disType == "Euclidean":
            return np.linalg.norm(v1-v2)
        elif disType == "Cosine":
            num = float(np.sum(v1*v2))
            denom = np.linalg.norm(v1)*np.linalg.norm(v2)
            cos = num/denom
            return cos
        
    def calcLabel(self, testline, train_matrix, disType):
        dis = dict()
        label = [0.0 for i in range(self.label_count)]
        #计算当前测试文本testline与每一个训练文本的距离
        for i in range(train_matrix.shape[0]):
            dis[i] = self.getdis(disType, testline, train_matrix[i, :])
        #余弦距离
        if disType == "Cosine":
            dis = sorted(dis.items(), key=lambda x: (-x[1], x[0]))
            for i in range(self.K):
                if abs(dis[i][1] - 1) < 1e-6:#余弦距离为1，则测试文本与该训练文本一模一样
                    label = self.train_set[1][dis[i][0]]
                    break
                else:#其他情况按公式计算概率
                    for j in range(self.label_count):
                        label[j] += self.train_set[1][dis[i][0]][j]*dis[i][1]
            #若概率全为0则赋成1/6
            if abs(sum(label) - 1) < 0:
                label = [1.0/6.0 for i in range(6)]
        else:#非余弦距离
            dis = sorted(dis.items(), key=lambda x: (x[1], x[0]))
            for i in range(self.K):
                if dis[i][1] < 1e-6: #距离为0，则测试文本与该训练文本一模一样
                    label = self.train_set[1][dis[i][0]]
                    break
                else:#其他情况按公式计算概率
                    for j in range(self.label_count):
                        label[j] += self.train_set[1][dis[i][0]][j]/dis[i][1]
        Sum = sum(label)
        label = [x/Sum for x in label] #概率归一化
        return label  

    def tryk(self, MatrixType="onehot", standard="original", disType="Manhattan"):
        if MatrixType == "onehot":
            [train_matrix, vaild_matrix] = self.getOnehot(standard, self.train_set, self.valid_set)
        elif MatrixType == "TFIDF":
            [train_matrix, vaild_matrix] = self.getTFIDF(standard, self.train_set, self.valid_set)
        writer = csv.writer(open('try_KNN_regression.csv', 'w', newline=''), dialect='excel')
        for i in range(vaild_matrix.shape[0]):
            label = self.calcLabel(vaild_matrix[i, :], train_matrix, disType)
            writer.writerow(label)
        print('MatrixType: ', MatrixType)
        print('standard: ', standard)
        print('disType: ', disType)
        print("K = ", self.K)

    def predict(self, MatrixType="onehot", standard="original", disType="Manhattan"):
        if MatrixType == "onehot":
            [train_matrix, test_matrix] = self.getOnehot(standard, self.train_set, self.test_set)
        elif MatrixType == "TFIDF":
            [train_matrix, vaild_matrix] = self.getTFIDF(standard, self.train_set, self.test_set)
        writer = csv.writer(open('15352408_zhangjiawei_KNN_regression.csv', 'w', newline=''), dialect='excel')
        writer.writerow(["textid"] + self.train_set[2])
        for i in range(vaild_matrix.shape[0]):
            label = self.calcLabel(vaild_matrix[i, :], train_matrix, disType)
            writer.writerow([i+1] + label)
        print("Prediction has finished")


if __name__ == "__main__":
    currentpath = os.getcwd()
    INS2 = Regress(currentpath)
    #INS2.tryk("onehot", "original", "Manhattan")
    #INS2.tryk("TFIDF", "normalization", "Cosine")
    INS2.predict("TFIDF", "normalization", "Cosine")