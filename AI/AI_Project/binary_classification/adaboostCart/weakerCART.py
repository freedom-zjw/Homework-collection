import math
import numpy as np
from collections import Counter


def getGini(dataSet):  # 计算一个数据集的gini系数
    labels = [x[-1] for x in dataSet]
    num = len(labels)
    classify = Counter(labels)
    gini = 1.0
    for key, value in classify.items():
        gini -= ((value/num) * (value/num))
    return gini


def splitDataSet(dataSet, axis, threshold, inequal):  # 根据特征、特征值和方向划分数据集
    subDataSet = []
    for row in dataSet:
        if inequal == 'lt' and row[axis] <= threshold:
            subDataSet.append(row)
        elif inequal == 'gt' and row[axis] > threshold:
            subDataSet.append(row)
    return subDataSet


# 特征值按连续值处理，设置一个阈值将值划成两部分
def chooseBestFeature(dataSet):
    numOfFeat = len(dataSet[0]) - 1     
    bestGiniGain = 1.0
    bestFeature = -1
    bestThreshold = ""
    numSteps = 20.0
    for i in range(numOfFeat):        
        colMin = np.mat(dataSet)[:, i].min() 
        colMax = np.mat(dataSet)[:, i].max()
        stepLen = (colMax-colMin) / numSteps  # 步长

        for j in range(-1, int(numSteps)+1):  # 遍历该列的所有取值
            threshold = colMin + j*stepLen
            GiniGain = 0.0

            # 根据阈值划分数据集并且求增益
            for k in range(2):
                inequal = 'lt' if k == 0 else 'gt'
                subDataSet = splitDataSet(dataSet, i, threshold, inequal)
                P = len(subDataSet)/float(len(dataSet))
                GiniGain += P * getGini(subDataSet)

            # 求出gini指数最小的那一列作为划分特征
            if (GiniGain < bestGiniGain):
                bestGiniGain = GiniGain 
                bestFeature = i
                bestThreshold = threshold

    return bestFeature, bestThreshold  # 返回划分特征 以及 阈值


def MooreVoting(classList):  # 多数投票表决
    classList = Counter(classList).most_common(1)
    return classList[0][0]


# 生成一个深度为depth的弱分类CART决策树
def buildTree(dataSet, depth=3):  
    labelList = [int(row[-1]) for row in dataSet]

    if depth == 0 or len(dataSet) == 1:  # 如果到达指定深度或没有可划分的特征，直接多数投票表决
        return MooreVoting(labelList)

    # 当剩下的特征的分类类别完全一样时，没必要继续分下去
    if labelList.count(labelList[0]) == len(labelList): 
        return labelList[0] 

    bestFeature, bestThreshold = chooseBestFeature(dataSet)
    bestNode = str(bestFeature)+":"+str(bestThreshold)  # 用最优特征+阀值作为节点，方便后期预测
    if bestFeature == -1:
        return MooreVoting(labelList)
    myTree = {bestNode: {}}

    # 根据最优特征和阈值 划分数据集，递归建树
    myTree[bestNode]['<='+str(round(float(bestThreshold), 3))] = buildTree(
            splitDataSet(dataSet, bestFeature, bestThreshold, 'lt'), 
            depth-1
    )
    myTree[bestNode]['>'+str(round(float(bestThreshold), 3))] = buildTree(
        splitDataSet(dataSet, bestFeature, bestThreshold, 'gt'), 
        depth-1
    )
    return myTree  


# 对单个样本进行分类
def classifyOneRow(tree, testRow):
    if type(tree) is not dict:
        return tree
    root = list(tree.keys())[0]
    feature, threshold = root.split(":") # 取出根节点，得到最优特征和阀值
    feature = int(feature)
    threshold = float(threshold)
    if testRow[feature] > threshold:  # 递归预测
        return classifyOneRow(tree[root]['>'+str(round(float(threshold), 3))], testRow)
    else:
        return classifyOneRow(tree[root]['<='+str(round(float(threshold), 3))], testRow)


#对数据集做分类，
def Classify(dataSet, tree):
    isError = np.ones((np.shape(dataSet)[0], 1))  # 1为分错，0为分对
    predictResult = []  # 记录预测的结果
    labelsList = [row[-1] for row in dataSet]
    for i in range(len(dataSet)):
        label = classifyOneRow(tree, dataSet[i])
        isError[i] = 1 if label != labelsList[i] else 0
        predictResult.append([int(label)])
    return isError, predictResult


# 记录弱分类器，主要调整样本的个数来达到调整样本权重的目的，训练弱分类器由createTree函数生成
def getCART(dataSet, W, depth=3):
    minW = W.min()  # 记录最小权重
    newDataSet = []
    for i in range(len(dataSet)):  # 最小权重样本数为1，权重大的样本对应重复math.ceil(float(np.np.array(W.T)[0][i]/minW))次
        newDataSet.extend([dataSet[i]]*int(math.ceil(float(np.array(W.T)[0][i] / minW))))
    bestWeakCART = {}
    weekCartTree = buildTree(newDataSet, depth)
    isError, bestWeakResult = Classify(dataSet, weekCartTree)
    error = W.T * isError  # 记录分错的权重之和
    bestWeakCART['cart'] = weekCartTree
    return bestWeakCART, bestWeakResult, error