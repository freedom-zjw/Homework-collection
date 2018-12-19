# -*- coding:utf-8 -*-

import os
import csv
import numpy as np
from os.path import join


class Regress:
    def __init__(self, floderpath):
        self.train_set = self.loadcsv(join(floderpath, "train_set.csv"))
        self.valid_set = self.loadcsv(join(floderpath, "validation_set.csv"))
        self.test_set = self.loadcsv(join(floderpath, "test_set.csv"), 2)
        self.wordset = self.train_set[3]
        self.totword = len(self.train_set[3])
        self.labelset = self.train_set[2]
        self.writepath = "15352408_zhangjiawei_NB_regression.csv"
        [self.trainNUM, self.trainDEN] = self. getTF(self.train_set[0])

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
        Set = list(set([x for line in words for x in line]))
        return [words, label, label_name, Set]

    def getTF(self, Matrix):
        num, den = [], []
        for i in range(len(Matrix)):
            num.append([])
            den.append(0)
            for word in self.wordset:
                cnt = Matrix[i].count(word)
                num[i].append(Matrix[i].count(word))
                den[i] += cnt
        return [num, den]
    
    def getP(self, word, traindoc, k, alpha):
        index = self.wordset.index(word)
        a= self.trainNUM[k][index] + alpha
        b= self.trainDEN[k] + self.totword * alpha
        return a/b

    def predict(self, predictType, alpha):
        #选择要预测的集合是验证集还是测试集
        if predictType == "validate":
            testmatrix = self.valid_set
        else:
            testmatrix = self.test_set
        writer = csv.writer(open(self.writepath, "w", encoding="utf-8", newline=""), dialect='excel')
        writer.writerow(["textid"] + self.labelset)
        #计算概率
        for i in range(len(testmatrix[0])):
            p = [0 for j in range(len(self.labelset))]
            for j in range(len(self.labelset)):
                for k in range(len(self.train_set[0])):
                    temp = 1.0
                    for word in set(testmatrix[0][i]):
                        if word not in self.wordset:
                            continue
                        #计算P(word│e，train[k])
                        temp *= self.getP(word, self.train_set[0][k], k, alpha)
                    #计算temp *=  p(ej|train[k])
                    temp *= self.train_set[1][k][j]
                    p[j] += temp #累加概率
            p = list(np.array(p) / np.sum(np.array(p)))#归一化
            writer.writerow([i+1] + p)
            print("text ", i, "has finished")
        print("Prediction has finished")
        

if __name__ == "__main__":
    currentpath = os.getcwd()
    INS1 = Regress(currentpath)
    #INS1.predict("validate", 1)
    #INS1.predict("test", 0.032)
