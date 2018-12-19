# -*- coding:utf-8 -*-
import csv
import os
import random
import numpy as np
from os.path import join


def getnumber(n, Type="Rand"):
    if Type == "Rand":
        number = [i for i in range(n)] 
        random.shuffle(number)
        writer = csv.writer(open("rand.csv", "w", encoding="utf-8", newline=""))
        writer.writerow(number)
    else:
        randReader = csv.reader(open("rand.csv", encoding="utf-8"))
        for row in randReader:
            row = [int(x) for x in row]
            number = row
    return number


def getdata(filepath, p=0.25, randType="Rand"):
    """
    :param filepath: the path of the input train data csv
    :param p: split %p data in the front and %p data in the end to be vaildation data
    :param randType: Type to splite the dataset into trainset and vaildset
    :return traindata: data that are used to train
    :return vaildata: data that are used to validate
    """
    Alldata, traindata, validata = [], [], []
    reader = csv.reader(open(filepath, encoding="utf-8"))
    for row in reader:
        row = [float(x) for x in row]
        Alldata.append([1] + row)
    validlen = int(len(Alldata) * p)
    number = getnumber(len(Alldata), randType)
    for i in range(validlen):
        validata.append(Alldata[number[i]])
    for i in range(validlen, len(Alldata)):
        traindata.append(Alldata[number[i]])
    return traindata, validata


def splitData(dataMat):
    Featrues, Labels = [], []
    for row in dataMat:
        Featrues.append(row[:-1])
        Labels.append(row[-1])
    return np.mat(Featrues), np.mat(Labels).transpose()


def sigmoid(x):
    return 1.0/(1.0+np.exp(-x))


def train(traindata, alpha, method="gradDescent", maxStep=100):
    """
    :param traindata: data matrix that used to train
    :param alpha: learning rate、step length
    :param maxStep: Max Iterations
    :param method:  optimization method
    :return: the result of prediction
    """
    print("training...")
    trainX, trainY = splitData(traindata)
    W = np.ones((trainX.shape[1], 1))
    N = trainX.shape[0]

    for step in range(maxStep):
        print(step)
        if method == "GD": 
            # 梯度下降 grad Descent
            err = trainX.transpose() * (trainY - sigmoid(trainX * W))
            W = W + alpha * err
        elif method == "SGD":
            # 随机梯度下降 stochastic gradient descent 
            for k in range(N):
                i = random.randint(0, N-1)
                err = trainX[i, :].transpose() * (trainY[i, 0] - sigmoid(trainX[i, :] * W))
                W = W + alpha * err
        elif method == "SSGD": 
            # 随机梯度下降 + 减小步长
            for k in range(N):
                alpha = 4.0 / (1.0 + k + step) + 0.0001
                i = random.randint(0, N-1)
                err = trainX[i, :].transpose() * (trainY[i, 0] - sigmoid(trainX[i, :] * W))
                W = W + alpha * err
        else:
            print("Method Type wrong")
            exit()
    return W


def predict(dataMat, W, Type="test"):
    """
    :param dataMat: data matrix that are used to predict
    :Param W: Well trained weight vectors
    :Type:predict type, test or validate
    :return: the result of prediction
    """
    testX, testY = splitData(dataMat)
    n = testX.shape[0]
    result = []
    for i in range(n):
        label = 0
        if sigmoid(testX[i, :] * W)[0, 0] > 0.5:
            label = 1
        result.append(label)

    if Type == "validate":
        cnt = 0
        for i in range(n):
            if result[i] == int(testY[i, 0]):
                cnt += 1
        print("Acc = ", cnt/n)
        return result, cnt/n
    else:
        return result


def getTestdata(filepath):
    reader = csv.reader(open(filepath, encoding="utf-8"))
    testdata = []
    for row in reader:
        row[-1] = 0
        row = [float(x) for x in row]
        testdata.append([1] + row)
    return testdata


def exportResult(filepath, result):
    result = [str(x) for x in result]
    content = '\n'.join(result)
    writer = open(filepath, "w")
    writer.write(content)
    writer.close()


if __name__ == "__main__":
    cp = os.getcwd()
    # traindata, validata = getdata(join(cp, "train.csv"))
    traindata, validata = getdata(join(cp, "train.csv"), 0.25, "notRand")
    method = "SSGD"
    # W = train(traindata, 0.0001, method, 10)
    # np.save("W.npy", W)   
    # result1, Acc1 = predict(traindata, W, "validate")
    # result2, Acc2 = predict(validata, W, "validate")
    W = np.load("W.npy")
    result1, Acc1 = predict(validata, W, "validate")
    testdata = getTestdata(join(cp, "test.csv"))
    result = predict(testdata, W)
    exportResult(join(cp, "output.txt"), result)

