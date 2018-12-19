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
        self.w = np.ones(len(self.trainset[0][0]))
        self.error = self.getError(self.w)
        self.cnt = 300

    def loadcsv(self, filepath):
        reader = csv.reader(open(filepath, encoding="utf-8"))
        data = []
        label = []
        for row in reader:
            tmp = [float(row[i]) for i in range(len(row)-1)]
            data.append([1] + tmp)
            if row[-1].strip() != '?':
                label.append(int(row[-1]))
        return [np.array(data), label]

    def getError(self, w):
        error = 0
        ans = np.dot(self.trainset[0], w)
        for i in range(len(ans)):
            if sign(ans[i]) != self.trainset[1][i]:
                error += 1
        return error

    def train(self):
        for i in range(self.cnt):
            sys.stdout.write("\rPercent: %d/%d" % (i+1, self.cnt))
            sys.stdout.flush()
            self.init_indicator()#每次迭代需要初始化指标
            for j in range(len(self.trainset[0])):
                y = sign(np.dot(self.w, self.trainset[0][j]))#x*w
                label = self.trainset[1][j]
                if y != label:#发现预测错误
                    tmp = self.trainset[0][j] * label
                    nw = self.w + tmp #求解新w
                    error = self.getError(nw)
                    if error < self.error:#若新w的错误率比较小则更新口袋中的w
                        self.error = error
                        self.w = nw
        return self.w

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
        self.Accuracy = (self.TP + self.TN) / (self.TP + self.FP + self.TN + self.FN)
        if self.TP + self.FP == 0:
            self.Precision = 0
        else:
            self.Precision = self.TP / (self.TP + self.FP)
        if self.TP + self.FN == 0:
            self.Recall = 0
        else:
            self.Recall = self.TP / (self.TP + self.FN)
        if self.Precision + self.Recall == 0:
            self.F1 = 0
        else:
            self.F1 = (2 * self.Precision * self.Recall) \
                      / (self.Precision + self.Recall)

    def val(self, w):
        self.init_indicator()
        self.w = w
        self.valset = self.loadcsv(join(self.folderpath, "val.csv"))
        for i in range(len(self.valset[0])):
            x = self.valset[0][i]
            label = int(self.valset[1][i])
            y = sign(np.dot(self.w, x))
            self.update(y, label)
        print("\nAccuracy = ", self.Accuracy)
        print("Precision = ", self.Precision)
        print("Recall = ", self.Recall)
        print("F1 = ", self.F1)
        print("")
    
    def predict(self, w, writefile):
        self.w = w
        self.testset = self.loadcsv(join(self.folderpath, "test.csv"))
        writer = csv.writer(open(writefile, "w", encoding="utf-8", newline=""))
        for i in range(len(self.testset[0])):
            x = self.testset[0][i]
            y = sign(np.dot(self.w, self.testset[0][i]))
            writer.writerow([y])


if __name__ == "__main__":
    currentpath = os.getcwd()
    ins = PLA(currentpath)
    w = ins.train()
    ins.val(w)
    ins.predict(w, join(currentpath, "pocket_out.csv"))
    #m = []
    #m.append([1,2,3])
    #m.append([4,6,5])
    #print(m[0:,1])
    #w = np.array([2,1,3])