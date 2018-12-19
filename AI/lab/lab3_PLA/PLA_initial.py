# -*- coding:utf-8 -*-
import csv
import os
import sys
import numpy as np
from os.path import join
"""
四个指标
TP：本来为+1，预测为+1
FN：本来为+1，预测为-1
TN：本来为-1，预测为-1
FP：本来为-1，预测为+1

Accuracy(准确率) = (TP + TN) / (TP + FP + TN + FN)
Precision(精确率) = TP / (TP + FP)
Recall(召回率) = TP / (TP + FN)
F1(F值) =  (2 * precision * Recall) / (precision + Recall)
"""


def sign(x):
    if x > 0:
        return 1
    else:
        return -1


class PLA:
    def __init__(self, folderpath):
        self.folderpath = folderpath
        self.trainset = self.loadcsv(join(folderpath, "train.csv"))
        self.w = np.ones(len(self.trainset[0][:-1]))
        self.cnt = 278

    def loadcsv(self, filepath):

        reader = csv.reader(open(filepath, encoding="utf-8"))
        data = []
        for row in reader:
            row = [float(x) for x in row if x.strip() != '?']
            data.append([1] + row)
        return data

    def train(self, tot=278):
        cnt = 0
        self.cnt = tot
        self.w = np.ones(len(self.trainset[0][:-1])) #初始化权重向量
        while cnt < self.cnt: #迭代指定次数
            cnt += 1
            flag = 1 #判断当前是否全部划分正确
            sys.stdout.write("\rPercent: %d/%d" % (cnt, self.cnt)) #迭代次数进度条
            sys.stdout.flush()
            for i in range(len(self.trainset)):
                x = self.trainset[i][:-1]   #取出x向量
                label = int(self.trainset[i][-1])#取出真实label
                y = sign(np.dot(self.w, x))  #x和权重向量w做内积
                if y == label:
                    continue
                flag = 0
                self.w = self.w + np.array(x) * label #预测错误，调整w
            if flag == 1: #如果已经全部划分正确就不继续划分了
                break

    def init_indicator(self):
        self.TP = self.FN = self.TN = self.FP = 0

    def update(self, y, label):
        if label == 1 and y == 1:
            self.TP += 1
        elif label == 1 and y == -1:
            self.FN += 1
        elif label == -1 and y == -1:
            self.TN += 1
        else:
            self.FP += 1

    def val(self):
        self.init_indicator()
        self.valset = self.loadcsv(join(self.folderpath, "val.csv"))
        for i in range(len(self.valset)):
            x = self.valset[i][:-1]  #取出x向量
            label = int(self.valset[i][-1]) #取出真实label
            y = sign(np.dot(self.w, x)) #预测的label
            self.update(y, label)  #根据预测label和真实label，更新4种指标的值
        #求准确率、精确率、召回率、F值
        Accuracy = (self.TP + self.TN) / (self.TP + self.FP + self.TN + self.FN)
        Precision = self.TP / (self.TP + self.FP)
        Recall = self.TP / (self.TP + self.FN)
        F1 = (2 * Precision * Recall) / (Precision + Recall)
        print("\ncnt = ", self.cnt)
        print("Accuracy = ", Accuracy)
        print("Precision = ", Precision)
        print("Recall = ", Recall)
        print("F1 = ", F1)
        print("")
        return [Accuracy, Precision, Recall, F1]
    
    def predict(self, writefile):
        self.testset = self.loadcsv(join(self.folderpath, "test.csv"))
        writer = csv.writer(open(writefile, "w", encoding="utf-8", newline=""))
        for i in range(len(self.testset)):
            x = self.testset[i]
            y = sign(np.dot(self.w, x))
            writer.writerow([y])


if __name__ == "__main__":
    currentpath = os.getcwd()
    ins = PLA(currentpath)
    
    #writefile = join(currentpath, "init_data.csv")
    #writer = csv.writer(open(writefile, "w", encoding="utf-8", newline=""))
    #writer.writerow(["id", "Accuracy", "Precision", "Recall", "F1"])
    """
    maxAcc = 0
    maxdata = []
    for i in range(500):
        ins.train(i+1)
        data = ins.val()
        #writer.writerow([i+1] + data)
        if data[0] > maxAcc:
            maxAcc = data[0]
            maxdata = [i+1] + data
    print("\n\n")
    print("cnt = ", maxdata[0])
    print("Accuracy = ", maxdata[1])
    print("Precision = ", maxdata[2])
    print("Recall = ", maxdata[3])
    print("F1 = ", maxdata[4])
    """
    ins.train(278)
    ins.val()
    ins.predict(join(currentpath, "init_out.csv"))
