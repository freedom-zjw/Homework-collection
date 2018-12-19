# -*- coding:utf-8 -*-
import csv
import os
import random
import time
from math import log
from os.path import join
from collections import Counter


def getdata(filepath, p):
    """
    :param filepath: the path of the input train data csv
    :param p: split %p data in the front and %p data in the end to be vaildation data
    :return labels: the features list of the input data
    :return traindata: data that are used to train
    :return vaildata: data that are used to validate
    """
    Alldata, traindata, vaildata = [], [], []
    reader = csv.reader(open(filepath, encoding="utf-8"))
    for row in reader:
        Alldata.append(row)
    vaildlen = int(len(Alldata) * p)
    randomseed = time.time()
    #randomseed = 1510758961
    print("randomseed: %d" % randomseed)
    number = [i for i in range(len(Alldata))]
    random.seed(randomseed)
    random.shuffle(number)
    for i in range(vaildlen):
        vaildata.append(Alldata[number[i]])
    for i in range(vaildlen, len(Alldata)):
        traindata.append(Alldata[number[i]])
    labels = ["feature" + str(i) for i in range(len(traindata[0])-1)]
    return labels, traindata, vaildata


def MooreVoting(classify):
    """
    多数投票,返回出现次数最多的标签
    :param classify: classify label
    :return: the most common classify label
    """
    classify = Counter(classify).most_common(1)
    return classify[0][0]


def getEntropy(traindata):
    """
    calculate the enrtopy of traindata
    :param traindata: the data that you need to calculate entropy
    :return: the entropy of traindata
    """
    classify = [x[-1] for x in traindata]
    num = len(classify)
    classify = Counter(classify)
    entropy = 0.0
    for key, value in classify.items():
        entropy -= (value/num) * log(value/num, 2)
    return entropy


def getGini(traindata):
    """
    calcuulate the gini number of traindata
    :param traindata: the data that you need to calculate gini number
    :return: the gini number of traindata
    """
    classify = [x[-1] for x in traindata]
    num = len(classify)
    classify = Counter(classify)
    gini = 1.0
    for key, value in classify.items():
        gini -= ((value/num) * (value/num))
    return gini


def splitData(traindata, idx, val):
    """
    :param traindata: data set that need to be splited
    :param idx: the axis of the traindata that need to be splited
    :param val: the value that be used to split the data
    """
    subData = []
    for row in traindata:
        if row[idx] == val:
            subDataRow = row[:idx]
            subDataRow.extend(row[idx+1:])
            subData.append(subDataRow)
    return subData


def getBestFeature(traindata, wayToChooseFeature):
    """
    :param traindata: traindata that waiting to be dividedy
    :param wayToChooseFeature: ID3、C4.5、CART
    :return bestFeature: the Feature that used to be the decision point
    """
    allEntropy = getEntropy(traindata)
    num = len(traindata[0]) - 1
    bestGain = 0.0
    bestGini = 10
    bestFeature = -1
    for i in range(num):
        featureData = [x[i] for x in traindata]
        valSet = set(featureData)
        nowEntropy = 0.0
        splitInfo = 0.0
        gini = 0.0
        for val in valSet:
            subData = splitData(traindata, i, val)
            p = len(subData) / len(traindata)
            if wayToChooseFeature == "ID3" or wayToChooseFeature == "C4.5":
                nowEntropy += p * getEntropy(subData)
                splitInfo -= p * log(p, 2)
            if wayToChooseFeature == "CART":
                gini += p * getGini(subData)
            
        if wayToChooseFeature == "ID3":
            gain = allEntropy - nowEntropy
            if gain > bestGain:
                bestGain = gain
                bestFeature = i
        elif wayToChooseFeature == "C4.5":
            if splitInfo == 0.0:
                continue
            gRatio = (allEntropy - nowEntropy) / splitInfo
            if gRatio > bestGain:
                bestGain = gRatio
                bestFeature = i
        elif wayToChooseFeature == "CART":
            if gini < bestGini:
                bestGini = gini
                bestFeature = i
    return bestFeature


def buildTree(traindata, labels, wayToChooseFeature):
    """
    :param traindata: data to create decision tree
    :param labels: feature name
    :param wayToChooseFeature: the way you use to choose best feature
                            it include ID3、C4.5 and CART
    :return Dtree: the decition tree you built
    """
    # 当剩下的特征的分类类别完全一样时，没必要继续分下去
    classify = [x[-1] for x in traindata]
    if classify.count(classify[0]) == len(classify):
        return classify[0]

    # 当没有特征可分下去，采用多数投票原则返回出现次数最多的标签
    if len(traindata) == 1:
        return MooreVoting(classify)
     
    # 选择最好的feature作为决策点，递归建树
    Dpoint = getBestFeature(traindata, wayToChooseFeature)
    label = labels[Dpoint]
    Tree = {label: {}}
    del(labels[Dpoint])
    values = [x[Dpoint] for x in traindata]
    valSet = set(values)
    for val in valSet:
        labelsCopy = labels[:]
        Tree[label][val] = buildTree(
                splitData(traindata, Dpoint, val),
                labelsCopy,
                wayToChooseFeature
        )
    return Tree


def getTestdata(filepath):
    testdata = []
    reader = csv.reader(open(filepath, encoding="utf-8"))
    for row in reader:
        testdata.append(row[:-1])
    return testdata


def classifyVec(Tree, labels, testVec, traindata, testlabels):
    """
    :param Tree: The Descision tree
    :param labels: Labels of the testdata
    :param testdata: The data vector that need to be classified
    :param traindata: The data used to train
    :return: The classify result 
    """
    root = list(Tree.keys())[0]
    choice = Tree[root]
    featureIdx = labels.index(root)
    feaidx2 = testlabels.index(root)
    del(testlabels[feaidx2])
    flag = 0
    #根据test向量feature的取值选择要走的树杈
    for key in choice.keys():
        if testVec[featureIdx] == key:
            flag = 1
            if type(choice[key]).__name__ == "dict":
                labelsCopy = testlabels[:]
                subtraindata = splitData(traindata, feaidx2, key)
                result = classifyVec(choice[key], labels, testVec, subtraindata, labelsCopy)
            else:
                result = choice[key]
            break
    #如果无法继续分下去，采用多数投票原则决定类别
    if flag == 0:
        nowlabels = [x[-1] for x in traindata]
        result = MooreVoting(nowlabels)
    return result


def classify(Tree, labels, testMatrix, traindata, testlabels, filepath):
    """
    :param Tree: The Descision tree
    :param labels: Labels of the testdata
    :param testdata: The data Matrix that need to be classified
    :param traindata: The data used to train
    :param filepath: Output the result to the filepath
    :return results: The result of classifing the testMatrix
    """
    results = []
    content = ""
    writefile = open(filepath, "w")
    for i in range(len(testMatrix)):
        labelsCopy = testlabels[:]
        result = classifyVec(Tree, labels, testMatrix[i], traindata, labelsCopy)
        results.append(result)
        #print("test%d: %s" % (i, result))
        content += str(result) + '\n'
    writefile.write(content)
    writefile.close()
    return results


def calcAcc(vaildata, vailresult):
    n = len(vaildata)
    cnt = 0
    for i in range(n):
        if vaildata[i][-1] == vailresult[i]:
            cnt += 1
    print("Acc: ", cnt/n)


if __name__ == "__main__":
    currentpath = os.getcwd()
    labels, traindata, vaildata = getdata(join(currentpath, "train.csv"), 0.25)
    wayToChooseFeature, labelsCopy = "C4.5", labels[:]
    Dtree = buildTree(traindata, labelsCopy, wayToChooseFeature)
    print("Algorithm: " + wayToChooseFeature)
    #print(Dtree)
    testdata = getTestdata(join(currentpath, "test.csv"))
    labelsCopy = labels[:]
    valresult = classify(Dtree, labels, vaildata, traindata, labelsCopy, join(currentpath, "valOutput.txt"))
    calcAcc(vaildata, valresult)
    labelsCopy = labels[:]
    classify(Dtree, labels, testdata, traindata, labelsCopy, join(currentpath, "Output.txt"))