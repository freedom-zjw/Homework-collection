# -*- coding:utf-8 -*-
import csv
import os
import numpy as np
from os.path import join
from collections import Counter
from math import log2


def loaddata(filepath):
    data = []
    reader = csv.reader(open(filepath, encoding="utf-8"))
    for row in reader:
        data.append(row)
    return np.array(data)


def getEntropy(x):
    n = len(x)
    c = Counter(x)
    entropy = 0
    for x in c:
        p = c[x] / n
        entropy += -(p * log2(p))
    return entropy


def calc(x, label):
    n = len(x)
    c = Counter(x)
    entropy = 0
    for key in c:
        y = []
        for i in range(n):
            if x[i] == key:
                y.append(label[i])
        entropy += c[key]/n * getEntropy(y)
    return entropy


if __name__ == "__main__":
    currentpath = os.getcwd()
    filepath = join(currentpath, "train.csv")
    data = loaddata(filepath)
    HD = getEntropy(data[:, -1])
    HDA = dict()
    for i in range(data.shape[1]-1):
        HDA[i] = HD - calc(data[:, i], data[:, -1])
    #print(HDA)
    d = sorted(HDA.items(), key=lambda x:-x[1])
    print("Use ID3 selection strategy")
    print("Columns numbered from 0 to %d" % (len(data[0])-1))
    print("Choose column %d to be root decision point" % (d[0][0]))
    print("The max gain value is", d[0][1])
    