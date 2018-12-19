# -*- coding:utf-8 -*-
import csv
import sys
import numpy as np
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
    elif disType == "Cos":
        num = float(np.sum(v1*v2))
        denom = np.linalg.norm(v1)*np.linalg.norm(v2)
        cos = num/denom
        return cos


def classifyOneRow(trainSet, trainLabels, testrow, k, disType):
    N, M = trainSet.shape
    dis = dict()
    for i in range(N):
        dis[i] = getDis(trainSet[i, :], testrow, disType)
    if disType == "Cos":
        dis = sorted(dis.items(), key=lambda x: (-x[1], x[0]))
    else:
        dis = sorted(dis.items(), key=lambda x: (x[1], x[0]))
    Kneighbors = [trainLabels[dis[z][0]] for z in range(k)]
    c = sorted(Counter(Kneighbors).items(), key=lambda x: (-x[1], x[0])) 
    return c[0][0] 


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
    traindataSet, trainLabels, valdataSet, valLabels = loadDataSet('newTrain3.csv')
    testdataSet = loadTestSet('newTest3.csv')
    Type = "row"
    Ntype = "minmax"
    disType = "Man"
    filename = "output.csv"
    for i in range(1, 2):
        
        #traindataSet, valdataSet, testdataSet = Normalize(traindataSet, valdataSet, testdataSet, Type, Ntype)
        print("k=", i)
        #valresult = predict(traindataSet, trainLabels, valdataSet, i, disType)
        #Accuracy, Precision, Recall, F1 = printResult(valresult, valLabels)
        testResult = predict(traindataSet, trainLabels, testdataSet, i, disType)
        filename = "66_v51NN离散降维2曼哈顿距离.csv"
        outputResult(testResult, filename)
