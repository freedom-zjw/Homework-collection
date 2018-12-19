# -*- coding:utf-8 -*-
import csv
import sys
import numpy as np
import math
import random
from collections import Counter


def loadDataSet(fileName, p=0):
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    traindataSet, valdataSet = [], []
    trainLabels, valLabels = [], []

    Alldata = []
    for row in reader:
        row = [float(x) for x in row]
        row[-1] = int(row[-1])
        Alldata.append(row)
        
    totlen = len(Alldata)
    vallen = int(totlen * p)
    num = [i for i in range(totlen)]
    random.shuffle(num)
    for i in range(vallen):
        index = num[i]
        row = Alldata[index][:-1]
        valdataSet.append(row)
        valLabels.append(Alldata[index][-1])

    for i in range(vallen, totlen):
        index = num[i]
        row = Alldata[index][:-1]
        traindataSet.append(row)
        trainLabels.append(Alldata[index][-1])
    
    return traindataSet, trainLabels, valdataSet, valLabels


def loadTestSet(fileName):
    testdataSet = []
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    for row in reader:
        row = [float(x) for x in row]
        testdataSet.append(row)
    return testdataSet


def Normalize(traindataSet, valdataSet, testdataSet, Type="row", Ntype="minmax"):
    trainlen = len(traindataSet)
    vallen = len(valdataSet)
    testlen = len(testdataSet)
    Alldata = []
    for row in traindataSet:
        Alldata.append(row)
    for row in valdataSet:
        Alldata.append(row)
    for row in testdataSet:
        Alldata.append(row)

    Alldata = np.array(Alldata, dtype=float)
    if Type == "col":
        Alldata = Alldata.T
    N, M = Alldata.shape

    for i in range(N):
        if Ntype == "minmax":
            rowMax = np.max(Alldata[i, :])
            rowMin = np.min(Alldata[i, :])
            Alldata[i, :] = (Alldata[i, :] - rowMin) / max(rowMax - rowMin, 1e-16)
        else:
            tstd, tmean = Alldata[i, :].std(), Alldata[i, :].mean()
            Alldata[i, :] = (Alldata[i, :] - tmean)/max(tstd, 1e-16)

    if Type == "col":
        Alldata = Alldata.T
    Alldata = Alldata.tolist()

    traindataSet, valdataSet, testdataSet = [], [], []
    for i in range(trainlen):
        traindataSet.append(Alldata[i])
    for i in range(trainlen, trainlen + vallen):
        valdataSet.append(Alldata[i])
    for i in range(trainlen + vallen, trainlen + vallen + testlen):
        testdataSet.append(Alldata[i])
    return traindataSet, valdataSet, testdataSet


def getDis(v1, v2, disType="Euclidean"):
    if disType == "Euclidean":
        return np.linalg.norm(v1 - v2)
    elif disType == "Man":
        return sum(abs(v1-v2))


def gaussian(dist, a=1, b=0, c=600):
    return a * math.e ** (-(dist - b) ** 2 / (2 * c ** 2))


def classifyOneRow(trainSet, trainLabels, testrow, k, disType):
    N, M = trainSet.shape
    dis = dict()
    weight = dict()
    for i in range(N):
        dis[i] = getDis(trainSet[i, :], testrow, disType)
    dis = sorted(dis.items(), key=lambda x: (x[1], x[0]))
    sumW = 0.0
    P = [0.0, 0.0]
    c = dis[k-1][1] / 2
    for i in range(k):
        x = dis[i][0]
        weight[x] = gaussian(dis[i][1], 1, 0, c)
        sumW += weight[x]
    for i in range(k):
        x = dis[i][0]
        y = int(trainLabels[x])
        P[y] += weight[x] / sumW
    if P[0] > P[1]:
        return 0
    else:
        return 1 


def predict(trainSet, trainLabels, testSet, k=1, disType="Euclidean"):
    trainSet = np.array(trainSet)
    testSet = np.array(testSet)
    N, M = testSet.shape
    result = []
    for i in range(N):
        sys.stdout.write("\rPercent: %f %%" % ((i+1)/N * 100))
        sys.stdout.flush()
        label = classifyOneRow(trainSet, trainLabels, testSet[i, :], k, disType)
        result.append(label)
    print("")
    return result


def printResult(predictResult, labels):
    TP, FN, TN, FP = 0, 0, 0, 0
    for i in range(len(labels)):
        TP += 1 if labels[i] == 1 and predictResult[i] == 1 else 0
        FN += 1 if labels[i] == 1 and predictResult[i] == -1 else 0
        TN += 1 if labels[i] == -1 and predictResult[i] == -1 else 0
        FP += 1 if labels[i] == -1 and predictResult[i] == 1 else 0
    Accuracy = (TP + TN) / (TP + FP + TN + FN)
    Precision = TP / (TP + FP)
    Recall = TP / (TP + FN)
    F1 = (2 * Precision * Recall) / (Precision + Recall)
    print("Accuracy = ", Accuracy)
    print("Precision = ", Precision)
    print("Recall = ", Recall)
    print("F1 = ", F1)
    return Accuracy, Precision, Recall, F1


def outputResult(result, filename="output.csv"):
    writer = csv.writer(open(filename, "w", encoding="utf-8", newline=""))
    for x in result:
        writer.writerow([x])


if __name__ == "__main__":
    traindataSet, trainLabels, valdataSet, valLabels = loadDataSet('newTrain2.csv')
    testdataSet = loadTestSet('newTest2.csv')
    Type = "row"
    Ntype = "minmax" 
    disType = "Man"
    filename = "output.csv"
    # writer = csv.writer(open("K.csv", "w", encoding="utf-8", newline=""))
    # writer.writerow(["k", "Accuracy", "Precision", "Recall", "F1"])
    for i in range(3, 4):
        
        #traindataSet, valdataSet, testdataSet = Normalize(traindataSet, valdataSet, testdataSet, Type, Ntype)
        print("k=", i)
        # valresult = predict(traindataSet, trainLabels, valdataSet, i, disType)
        # Accuracy, Precision, Recall, F1 = printResult(valresult, valLabels)
        # writer.writerow([i, Accuracy, Precision, Recall, F1])
        testResult = predict(traindataSet, trainLabels, testdataSet, i, disType)
        filename = "output.csv"
        outputResult(testResult, filename)
