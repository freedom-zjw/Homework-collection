# -*- coding:utf-8 -*-
import csv
import os
import sys
import numpy as np
from os.path import join
import matplotlib.pyplot as plt


def loadTrain(filepath):
    reader = csv.reader(open(filepath, encoding="utf-8"))
    next(reader)
    Alldata = []
    trainInput, trainOutput, validateInput, validateOutput = [], [], [], []
    for row in reader:
        x = row[2:3] + row[4:]
        x = [float(i) for i in x]
        Alldata.append(x)
    Len, valLen = len(Alldata), 475
    for i in range(Len):
        if i + valLen >= Len:
            validateInput.append(Alldata[i][0:-1])
            validateOutput.append(Alldata[i][-1])
        else:
            trainInput.append(Alldata[i][0:-1])
            trainOutput.append(Alldata[i][-1])
    trainInput = np.array(trainInput).T
    trainOutput = np.array(trainOutput)
    validateInput = np.array(validateInput).T
    validateOutput = np.array(validateOutput)
    return trainInput, trainOutput, validateInput, validateOutput


def loadtest(filepath):
    reader = csv.reader(open(filepath))
    next(reader)
    testInput = []
    for row in reader:
        x = row[2:3] + row[4:-1]
        x = [float(i) for i in x]
        testInput.append(x)
    testInput = np.array(testInput).T
    return testInput


def sigmoid(x):
    return 1/(1+np.exp(-x))


def tanh(x):
    return 1 - (2 / (np.exp(x*2) + 1))


def normalize(M):
    if M.ndim > 1:
        Min = np.array([M.min(axis=1).T.tolist()]).T
        Max = np.array([M.max(axis=1).T.tolist()]).T
    else:
        Min = np.array([M.min().T.tolist()]).T
        Max = np.array([M.max().T.tolist()]).T
    M = (M-Min)/(Max-Min + 0.000001)   
    return Min, Max, M


def renormalize(M, Min, Max):
    M = M * (Max-Min + 0.000001) + Min
    return M


def train(InputData, OutputData):
    inputDim = 11
    hiddenDim = 100
    outputDim = 1
    maxStep = 10000
    alpha = 0.0001
    N = InputData.shape[1]
    
    w1 = 0.5*np.random.rand(hiddenDim, inputDim) - 0.1  # 输入层到隐藏层的
    b1 = 0.5*np.random.rand(hiddenDim, 1) - 0.1
    w2 = 0.5*np.random.rand(outputDim, hiddenDim) - 0.1  # 隐藏层到输出层的
    b2 = 0.5*np.random.rand(outputDim, 1) - 0.1

    TrainLoss, w1List, w2List, b1List, b2List = [], [], [], [], []
    w1List.append(w1), b1List.append(b1)
    w2List.append(w2), b2List.append(b2)
    for i in range(maxStep):
        sys.stdout.write("\rPercent: %f %%" % ((i+1)/maxStep * 100))
        sys.stdout.flush()
        # 前向传播 Forward pass
        hiddenInput = np.dot(w1, InputData) + b1  # hiddenDim*N
        # hiddenOuput = sigmoid(hiddenInput)  # hiddenDim*N
        hiddenOuput = tanh(hiddenInput)
        finalOutput = np.dot(w2, hiddenOuput) + b2  # outputDim*N

        # 反向传播 Backward pass
        err2 = (OutputData - finalOutput)  # 输出层的误差 outputDim*N 
        gradient2 = err2  # 输出层的误差梯度  ouputDim*N 
        err1 = np.dot(w2.T, gradient2)  # 隐藏层误差 hiddenDim*outputDim
        # gradient1 = err1 * hiddenOuput * (1 - hiddenOuput)  # 隐藏层误差梯度 hiddenDim*outputDim
        gradient1 = err1 * (1 - hiddenOuput**2) 

        # 更新权重
        w2 = w2 + alpha * np.dot(gradient2, hiddenOuput.T)/N
        b2 = b2 + alpha * np.dot(gradient2, np.ones((N, 1)))/N
        w1 = w1 + alpha * np.dot(gradient1, InputData.T)/N
        b1 = b1 + alpha * np.dot(gradient1, np.ones((N, 1)))/N

        MSE = np.sum(err2**2)/N/2
        TrainLoss.append(MSE)

        w1List.append(w1), b1List.append(b1)
        w2List.append(w2), b2List.append(b2)
        
    return TrainLoss, w1List, w2List, b1List, b2List


def validate(InputData, OutputData, w1List, w2List, b1List, b2List):
    maxStep = len(w1List)
    N = InputData.shape[1]
    validateLoss = []
    for i in range(maxStep):
        w1, b1, w2, b2 = w1List[i], b1List[i], w2List[i], b2List[i]
        hiddenInput = np.dot(w1, InputData) + b1  # hiddenDim*N
        # hiddenOuput = sigmoid(hiddenInput)  # hiddenDim*N
        hiddenOuput = tanh(hiddenInput)
        finalOutput = np.dot(w2, hiddenOuput) + b2  # outputDim*N
        err = (OutputData - finalOutput) 
        MSE = np.sum(err**2)/N/2
        validateLoss.append(MSE)
    return validateLoss


def predict(InputData, w1, w2, b1, b2):
    hiddenInput = np.dot(w1, InputData) + b1  # hiddenDim*N
    # hiddenOuput = sigmoid(hiddenInput)  # hiddenDim*N
    hiddenOuput = tanh(hiddenInput)
    finalOutput = np.dot(w2, hiddenOuput) + b2  # outputDim*N
    return finalOutput


def DrawLossFunction(TrainLoss, ValidateLoss):
    plt.subplot(121)
    plt.plot(TrainLoss, label='Traning loss')
    plt.plot(ValidateLoss, label='Validation loss')
    plt.legend()


def DrawOutput(realOuput, predictOutput):
    plt.subplot(122)
    plt.plot(realOuput, label='Data')
    plt.plot(predictOutput, label='prediction')
    plt.legend()
    plt.show()


if __name__ == "__main__":
    currentpath = os.getcwd()
    trainInput, trainOutput, validateInput, validateOutput = loadTrain(join(currentpath, "train.csv"))
    trainInMin, trainInMax, trainInput = normalize(trainInput)
    trainOutMin, trainOutMax, trainOutput = normalize(trainOutput)
    validateInMin, validateInMax, validateInput = normalize(validateInput)
    validateOutMin, validateOutMax, validateOutput = normalize(validateOutput)
    TrainLoss, w1List, w2List, b1List, b2List = train(trainInput, trainOutput)
    ValidateLoss = validate(validateInput, validateOutput, w1List, w2List, b1List, b2List)
    predictOutput = predict(validateInput, w1List[-1], w2List[-1], b1List[-1], b2List[-1])
    DrawLossFunction(TrainLoss, ValidateLoss)
    validateOutput = renormalize(validateOutput, validateOutMin, validateOutMax)
    predictOutput = renormalize(predictOutput[0], validateOutMin, validateOutMax)
    minn = np.min(predictOutput)
    if minn < 0:
        predictOutput = predictOutput - minn
    DrawOutput(validateOutput, predictOutput)
    testInput = loadtest(join(currentpath, "test.csv"))
    testInMin, testInMax, testInput = normalize(testInput) 
    testOutput = predict(testInput, w1List[-1], w2List[-1], b1List[-1], b2List[-1])
    testOutput = renormalize(testOutput[0], validateOutMin, validateOutMax)
    minn = np.min(testOutput)
    if minn < 0:
        testOutput = testOutput - minn
    testOutput = testOutput + 0.5
    testOutput = testOutput.tolist()
    testOutput = [str(int(i)) for i in testOutput]
    testOutput = "\n".join(testOutput)
    filename = "output.txt"
    writer = open(join(currentpath, filename), "w")
    writer.write(testOutput)
    writer.close()
    


    
    


