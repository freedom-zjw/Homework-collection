import adaboost
import random
import time
import csv


def loadDataSet(fileName):
    totlen = 12000
    vallen = int(totlen * 0.2)
    num = []
    valhash = set()
    randomseed = time.time()
    print(randomseed)
    random.seed(randomseed)
    cnt = 0
    while (cnt < vallen):
        x = random.randint(0, totlen-1)
        if x not in valhash:
            num.append(x)
            valhash.add(x)
            cnt += 1
    for i in range(totlen):
        if i not in valhash:
            num.append(i)
    
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    traindataSet, valdataSet = [], []

    Alldata = []
    for row in reader:
        row = [float(x) for x in row]
        row[-1] = int(row[-1]) * 2 - 1
        Alldata.append(row)

    for i in range(vallen):
        index = num[i]
        row = Alldata[index]
        valdataSet.append(row)

    for i in range(vallen, totlen):
        index = num[i]
        row = Alldata[index]
        traindataSet.append(row)
    
    return traindataSet, valdataSet


def loadTestSet(fileName):
    testdataSet = []
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    for row in reader:
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
    return F1


def outputResult(result, numOfClassifier, depth, trainF1, valF1):
    filepath = str(numOfClassifier) + "classifier_" + \
               str(depth) + "depth_" + \
               "train" + str(trainF1) + \
               "_val" + str(valF1) + ".csv"             
    result = (result.T).tolist()[0]
    result = ["1" if x > 0 else "0" for x in result]
    writer = csv.writer(open(filepath, "w", encoding="utf-8", newline=""))
    for x in result:
        writer.writerow(x)


if __name__ == "__main__":
    writer = csv.writer(open("F12.csv", "w", encoding="utf-8", newline=""))
    traindataSet, valdataSet = loadDataSet('newTrain2.csv')
    testdataSet = loadTestSet('newTest2.csv')
    """
    traindataSet = [[1.,2.1, 1.0],
                         [2.,1.1, 1.0],
                         [1.3,1., -1.0],
                         [1.,1., -1.0],
                         [2.,1., 1.0]
    ]
    """
    trainLabels = [row[-1] for row in traindataSet]
    valLabels = [row[-1] for row in valdataSet]
    valdataSet = [row[:-1] for row in valdataSet]
    for depth in range(10, 11):
        for numOfClassifier in range(16, 21):
            print("\nnumofClassifier: " + str(numOfClassifier) + "  depth: " + str(depth))
            weekCartClassList, trainResult = adaboost.train(traindataSet, numOfClassifier, depth)
            trainF1 = printResult(trainResult, trainLabels)

            valResult = adaboost.predict(valdataSet, weekCartClassList)
            valF1 = printResult(valResult, valLabels)
    
            # testResult = adaboost.predict(testdataSet, weekCartClassList)
            # outputResult(testResult, numOfClassifier, depth, trainF1, 0)
            writer.writerow([trainF1, valF1])
    
