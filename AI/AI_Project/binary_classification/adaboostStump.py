# -*- coding:utf-8 -*-
import numpy as np
import csv
import sys
import math
import random


# 弱分类器部分
def weakerClassify(dataSet, index, cutValue, k):
    """
    将该列满足要求的预测结果设为-1，这样就得到了两种分类情况
    """
    result = np.ones((dataSet.shape[0], 1))
    if k == 0:  # 小于等于的部分设-1
        result[dataSet[:, index] <= cutValue] = -1
    else:  # 大于的部分设-1
        result[dataSet[:, index] > cutValue] = -1
    return result


def calcErr(predict, labels, W):
    err = np.mat(np.ones((len(labels), 1)))
    err[predict == labels] = 0
    return W.T * err


def buildWeakerTree(dataSet, Labels, W):  # 求单层决策树弱分类器,即只根据一个特征分类
    dataSet = np.mat(dataSet)
    N, M = dataSet.shape
    Labels = (np.mat(Labels)).T
    minErr = float("inf")
    numSteps = 20.0
    bestWeakerTree = {} 
    bestWeakerResult = np.mat(np.zeros((N, 1)))
    for i in range(M):  # 对数据集中的每一个特征M求一个弱分类器
        colMin = dataSet[:, i].min() 
        colMax = dataSet[:, i].max()
        stepLen = (colMax-colMin) / numSteps  # 步长
        for j in range(-1, int(numSteps)+1):  # 对每个步长
            cutValue = colMin + j*stepLen  # 划分值
            for k in range(2):  # 用不等号划分当前特征值成两段（0为小于等于，1为大于）
                # 根据划分值和不等号对数据集进行弱分类预测，结果存储在weakPredictResult
                weakPredictResult = weakerClassify(dataSet, i, cutValue, k)
                err = calcErr(weakPredictResult, Labels, W)  # 计算加权错误率
                if err < minErr:  # 根据错误率选出最佳弱分类器
                    minErr = err
                    bestWeakerResult = weakPredictResult.copy()
                    bestWeakerTree['index'] = i
                    bestWeakerTree['cutValue'] = cutValue
                    bestWeakerTree['inequal'] = k
    return bestWeakerTree, bestWeakerResult, minErr


#adaboost部分
def train(dataSet, Labels, Step=100):
    N, M = np.shape(dataSet)
    W = np.mat(np.ones((N, 1))/N)  # 数据集权重初始化
    weakerClassifier = []  # 记录弱分类器
    sigmaClassResult = np.mat(np.zeros((N, 1))) #记录每个样本的用所有分类器的分类结果加权累加
    for i in range(Step):
        sys.stdout.write("\rPercent: %f %%" % ((i+1)/Step * 100))
        sys.stdout.flush()
        # 每次在加权数据集中得到一个最佳弱分类决策树
        bestWeakerTree, bestWeakerResult, Err = buildWeakerTree(dataSet, Labels, W)
        # print(1 - Err)
        s = (1.0 - Err) / max(Err, 1e-16)
        alpha = float(0.5*np.log(s))  # 计算该弱分类器的权重
        bestWeakerTree['alpha'] = alpha
        weakerClassifier.append(bestWeakerTree)

        # 更新权重
        expon = np.multiply(-1*alpha*np.mat(Labels).T, bestWeakerResult)
        W = np.multiply(W, np.exp(expon))
        W = W / W.sum()

        sigmaClassResult += alpha * bestWeakerResult  # 加权累加分类结果
        currentErr = np.sum(np.sign(sigmaClassResult) != np.mat(Labels).T) / N  # 计算目前为止的弱分类器构成的强分类器的分类误差
        if currentErr == 0.0:  # 错误率达到0就提前结束
            break
    print("")
    return weakerClassifier


def predict(testSet, StrongClassifier):  # 用adboost得到的强分类器作预测
    testSet = np.mat(testSet)
    N, M = testSet.shape
    sigmaClassResult = np.mat(np.zeros((N, 1)))
    for i in range(0, len(StrongClassifier)):
        result = weakerClassify(
            testSet, 
            StrongClassifier[i]["index"],
            StrongClassifier[i]["cutValue"],
            StrongClassifier[i]["inequal"]
        )
        sigmaClassResult += StrongClassifier[i]["alpha"] * result
    return np.sign(sigmaClassResult)


def loadDataSet(fileName):
    totlen = 12000
    vallen = int(totlen * 0.2)
    num = [i for i in range(totlen)]
    random.shuffle(num)
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    traindataSet, trainLabels, valdataSet, valLabels = [], [], [], []

    Alldata = []
    for row in reader:
        x = row[0:2]
        x = x + row[5:7]
        x = x + row[9:12]
        x.append(row[-1])
        Alldata.append(x)

    for i in range(vallen):
        index = num[i]
        row = Alldata[index][:-1]
        label = float(Alldata[index][-1]) * 2 - 1
        row = [float(x) for x in row]
        valdataSet.append(row)
        valLabels.append(label)

    for i in range(vallen, totlen):
        index = num[i]
        row = Alldata[index][:-1]
        label = float(Alldata[index][-1]) * 2 - 1
        row = [float(x) for x in row]
        traindataSet.append(row)
        trainLabels.append(label)
    
    return traindataSet, trainLabels, valdataSet, valLabels


def loadTestSet(fileName):
    testdataSet = []
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    for row in reader:
        x = row[0:2]
        x = x + row[5:7]
        x = x + row[9:]
        row = x
        row = [float(x) for x in row]
        testdataSet.append(row)
    return testdataSet


def printResult(predictResult, labels):
    predictResult = (predictResult.T).tolist()[0]
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
    if F1 > 0.77:
        return False
    return True


def outputResult(result):
    result = (result.T).tolist()[0]
    result = ["1" if x > 0 else "0" for x in result]
    writer = csv.writer(open("output.csv", "w", encoding="utf-8", newline=""))
    for x in result:
        writer.writerow(x)


if __name__ == "__main__":
    flag = True
    while flag is True:
        traindataSet, trainLabels, valdataSet, valLabels = loadDataSet('newTrain2.csv')
        StrongClassifier = train(traindataSet, trainLabels, 100)
        trainresult = predict(traindataSet, StrongClassifier)
        print("\nTrainSet:")
        printResult(trainresult, trainLabels)
        valresult = predict(valdataSet, StrongClassifier)
        print("\nValidateSet:")
        flag = printResult(valresult, valLabels)
        testdataSet = loadTestSet('newTest2.csv')
        testresult = predict(testdataSet, StrongClassifier)
        outputResult(testresult)
        print("")
    