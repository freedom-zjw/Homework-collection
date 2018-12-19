# -*- coding:utf-8 -*-
import numpy as np
import csv
import sys
import random


def loadDataSet(fileName):
    totlen = 12000
    vallen = int(totlen * 0.3)
    num = [i for i in range(totlen)]
    random.shuffle(num)
    reader = csv.reader(open(fileName, "r", encoding='utf-8'))
    traindataSet, trainLabels, valdataSet, valLabels = [], [], [], []

    Alldata = []
    for row in reader:
        """
        x = row[0:2]
        x = x + row[5:7]
        x = x + row[9:12]
        x.append(row[-1])
        """
        Alldata.append(row)

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
        """
        x = row[0:2]
        x = x + row[5:7]
        x = x + row[9:]
        row = x
        """
        row = [float(x) for x in row]
        testdataSet.append(row)
    return testdataSet


def printResult(predictResult, labels):
    predictResult = (predictResult).tolist()
    TP, FN, TN, FP = 0, 0, 0, 0
    for i in range(len(labels)):
        TP += 1 if labels[i] == 1 and predictResult[i] == 1 else 0
        FN += 1 if labels[i] == 1 and predictResult[i] == -1 else 0
        TN += 1 if labels[i] == -1 and predictResult[i] == -1 else 0
        FP += 1 if labels[i] == -1 and predictResult[i] == 1 else 0
    print(TP,FN,TN,FP)
    Accuracy = (TP + TN) / (TP + FP + TN + FN)
    if TP != 0:
        Precision = TP / (TP + FP)
    else:
        Precision = 0
    Recall = TP / (TP + FN)
    if Precision + Recall == 0:
        F1 = 0
    else:
        F1 = (2 * Precision * Recall) / (Precision + Recall)
    print("Accuracy = ", Accuracy)
    print("Precision = ", Precision)
    print("Recall = ", Recall)
    print("F1 = ", F1)
    """
    if F1 > 0.77:
        return False
    return True
    """

def logistic_regression_classifier(train_x, train_y):    
    from sklearn.linear_model import LogisticRegression    
    model = LogisticRegression(penalty='l2')    
    model.fit(train_x, train_y)    
    return model    

def knn_classifier(train_x, train_y):    
    from sklearn.neighbors import KNeighborsClassifier    
    model = KNeighborsClassifier()    
    model.fit(train_x, train_y)    
    return model 

def naive_bayes_classifier(train_x, train_y):    
    from sklearn.naive_bayes import MultinomialNB    
    model = MultinomialNB(alpha=0.01)    
    model.fit(train_x, train_y)    
    return model  

    
def logistic_regression_classifier(train_x, train_y):    
    from sklearn.linear_model import LogisticRegression    
    model = LogisticRegression(penalty='l2')    
    model.fit(train_x, train_y)    
    return model  

def random_forest_classifier(train_x, train_y):    
    from sklearn.ensemble import RandomForestClassifier    
    model = RandomForestClassifier(n_estimators=8)    
    model.fit(train_x, train_y)    
    return model  

def decision_tree_classifier(train_x, train_y):    
    from sklearn import tree    
    model = tree.DecisionTreeClassifier()    
    model.fit(train_x, train_y)    
    return model 

def gradient_boosting_classifier(train_x, train_y):    
    from sklearn.ensemble import GradientBoostingClassifier    
    model = GradientBoostingClassifier(n_estimators=200)    
    model.fit(train_x, train_y)    
    return model    

def svm_classifier(train_x, train_y):    
    from sklearn.svm import SVC    
    model = SVC(kernel='rbf', probability=True)    
    model.fit(train_x, train_y)    
    return model  

def svm_cross_validation(train_x, train_y):    
    from sklearn.grid_search import GridSearchCV    
    from sklearn.svm import SVC    
    model = SVC(kernel='rbf', probability=True)    
    param_grid = {'C': [1e-3, 1e-2, 1e-1, 1, 10, 100, 1000], 'gamma': [0.001, 0.0001]}    
    grid_search = GridSearchCV(model, param_grid, n_jobs = 1, verbose=1)    
    grid_search.fit(train_x, train_y)    
    best_parameters = grid_search.best_estimator_.get_params()    
    for para, val in list(best_parameters.items()):    
        print(para, val)    
    model = SVC(kernel='rbf', C=best_parameters['C'], gamma=best_parameters['gamma'], probability=True)    
    model.fit(train_x, train_y)    
    return model 

def outputResult(result):
    result = (result.T).tolist()[0]
    result = ["1" if x > 0 else "0" for x in result]
    writer = csv.writer(open("output.csv", "w", encoding="utf-8", newline=""))
    for x in result:
        writer.writerow(x)


if __name__ == "__main__":
    traindataSet, trainLabels, valdataSet, valLabels = loadDataSet('newTrain2.csv')
    testdataSet = loadTestSet('newTest2.csv')

    #SVC
    classifier = naive_bayes_classifier(np.array(traindataSet), np.array(trainLabels))

    trainResult = knn_classifier(np.array(traindataSet))
    printResult(trainResult, trainLabels)
    valResult = classifier.predict(np.array(valdataSet))
    printResult(valResult, valLabels)
    
    