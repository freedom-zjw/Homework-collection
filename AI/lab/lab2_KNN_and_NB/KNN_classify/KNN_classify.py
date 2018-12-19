# -*- coding:utf-8 -*-

import os
import csv
import numpy as np
from math import sqrt
from math import log
from collections import Counter
from os.path import join


class Classify:
    def __init__(self, floderpath):
        self.train_set = self.loadcsv(join(floderpath, "train_set.csv"))
        self.valid_set = self.loadcsv(join(floderpath, "validation_set.csv"))
        self.test_set = self.loadcsv(join(floderpath, "test_set.csv"), 2)
        self.MAXK = int(sqrt(len(self.train_set[0])))
        self.K = 11
        self.Accuracy = 0.0

    def loadcsv(self, filepath, Type=1):
        with open(filepath, 'r') as file:
            reader = csv.reader(file)
            next(reader)
            words, label = [], []
            st = 0 if Type == 1 else 1
            for row in reader:
                words.append(row[st].split(' '))
                label.append(row[st + 1])         
        Set = set([x for line in words for x in line])
        return [words, label, Set]

    def Normalize(self, Matrix):
        n = Matrix.shape[0]
        for i in range(n):
            tstd, tmean = Matrix[i, :].std(), Matrix[i, :].mean()
            Matrix[i, :] = (Matrix[i, :] - tmean)/(tstd + 1e-10)
            len = np.linalg.norm(Matrix[i, :])
            if len > 1e-6:
                Matrix[i, :] = Matrix[i, :]/len
        Matrix = Matrix-np.min(Matrix)
        return Matrix
    
    def getOnehot(self, standard, train_set, test_set):
        word_set = list(train_set[2] | test_set[2])
        train_matrix, test_matrix = [], []
        train_len, test_len = len(train_set[0]), len(test_set[0])
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
        if standard == "normalization":
            train_matrix = self.Normalize(train_matrix)
            test_matrix = self.Normalize(test_matrix)
        return [train_matrix, test_matrix]

    def getTFIDF(self, standard, train_set, test_set):
        word_set = list(train_set[2])
        train_matrix, test_matrix = [], []
        train_len, test_len = len(train_set[0]), len(test_set[0])
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
        #计算当前测试文本testline与每一个训练文本的距离
        for i in range(train_matrix.shape[0]):
            dis[i] = self.getdis(disType, testline, train_matrix[i, :])
        if disType == "Cosine":#余弦距离按从大到小排序
            dis = sorted(dis.items(), key=lambda x: (-x[1], x[0]))               
        else:#其余距离按从小到大排序
            dis = sorted(dis.items(), key=lambda x: (x[1], x[0]))
        #选择最近的K个文本中出现次数最多的感情
        Kneighbors = [self.train_set[1][dis[z][0]] for z in range(self.K)]
        c = sorted(Counter(Kneighbors).items(), key=lambda x: (-x[1], x[0])) 
        return c[0][0]  
    
    def tryk(self, MatrixType="onehot", standard="original", disType="Manhattan"):
        if MatrixType == "onehot":
            [train_matrix, vaild_matrix] = self.getOnehot(standard, self.train_set, self.valid_set)
        elif MatrixType == "TFIDF":
            [train_matrix, vaild_matrix] = self.getTFIDF(standard, self.train_set, self.valid_set)
        print('MatrixType: ', MatrixType)
        print('standard: ', standard)
        print('disType: ', disType)
        for i in range(11, 20):
            Acc, self.K= 0.0, i
            for i in range(vaild_matrix.shape[0]):
                label = self.calcLabel(vaild_matrix[i, :], train_matrix, disType)
                if label == self.valid_set[1][i]:
                    Acc += 1.0
            print("K = ", self.K, "Accuracy = ", Acc/vaild_matrix.shape[0])
    
    def predict(self, MatrixType="onehot", standard="original", disType="Manhattan"):
        if MatrixType == "onehot":
            [train_matrix, test_matrix] = self.getOnehot(standard, self.train_set, self.test_set)
        elif MatrixType == "TFIDF":
            [train_matrix, test_matrix] = self.getTFIDF(standard, self.train_set, self.test_set)
        writer = csv.writer(open('15352408_zhangjiawei_KNN_classification.csv', 'w', newline=''), dialect='excel')
        writer.writerow(["textid", "label"])
        for i in range(test_matrix.shape[0]):
            label = self.calcLabel(test_matrix[i, :], train_matrix, disType)
            line = [i+1]
            line.append(label)
            writer.writerow(line)
        print("Prediction has finished")


if __name__ == "__main__":
    currentpath = os.getcwd()
    INS1 = Classify(currentpath)
    #INS1.tryk("TFIDF", "normalization", "Cosine")
    #INS1.tryk("onehot", "original", "Manhattan")
    INS1.predict("TFIDF", "normalization", "Cosine")
