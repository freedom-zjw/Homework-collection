import weakerCART
import numpy as np
import sys


def train(dataSet, numOfClassifier=1, depth=3):
    N = len(dataSet)
    W = np.mat(np.ones((N, 1))/N)  # 初始化所有样本的权重为1/N
    Labels = np.mat([int(row[-1]) for row in dataSet])
    aggregationResult = np.mat(np.zeros((N, 1)))  # 记录每个样本的用所有分类器的分类结果加权累加
    weekCartClassList = []  # 记录最后得到的弱分类器集合
    print("training...")
    sys.stdout.write("\rPercent: %f %%" % ((0)/numOfClassifier * 100))
    sys.stdout.flush()
    for i in range(numOfClassifier):
        # 每次在加权数据集中得到一个最佳弱分类决策树
        bestWeakCART, bestWeakResult, error = weakerCART.getCART(dataSet, W, depth)  # 得到当前最优的弱分类器
        alpha = float(0.5*np.log((1.0 - error)/max(error, 1e-16)))  # 计算该弱分类器的权重
        bestWeakCART['alpha'] = alpha
        weekCartClassList.append(bestWeakCART)

        # 更新权重
        expon = np.multiply(-1*alpha*np.mat(Labels).T, bestWeakResult)
        W = np.multiply(W, np.exp(expon)) 
        W = W/W.sum()

        # 加权累加分类结果
        aggregationResult += alpha * np.mat(bestWeakResult)
        # 计算目前为止的弱分类器构成的强分类器的分类误差
        avgErr = np.sum(np.sign(aggregationResult) != np.mat(Labels).T) / N
        
        sys.stdout.write("\rPercent: %f %%" % ((i+1)/numOfClassifier * 100))
        sys.stdout.flush()
        if avgErr == 0.0: 
            break
    print("\n")
    return weekCartClassList, np.sign(aggregationResult)


def classfiy(dataSet, tree):
    predictResult = []
    for i in range(len(dataSet)):
        label = weakerCART.classifyOneRow(tree, dataSet[i])
        predictResult.append([int(label)])
    return predictResult


def predict(dataSet, weekCartClassList):
    N = len(dataSet)
    predictResult = np.mat(np.zeros((N, 1))) 
    for i in range(len(weekCartClassList)):
        weakerResult = classfiy(dataSet, weekCartClassList[i]['cart'])
        predictResult += weekCartClassList[i]['alpha'] * np.mat(weakerResult)
    return np.sign(predictResult)
