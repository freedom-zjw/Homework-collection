# -*- coding:utf-8 -*-

import os
import csv
import numpy as np
from os.path import join


class Classify:
    def __init__(self, floderpath):
        self.train_set = self.loadcsv(join(floderpath, "train_set.csv"))
        self.valid_set = self.loadcsv(join(floderpath, "validation_set.csv"))
        self.test_set = self.loadcsv(join(floderpath, "test_set.csv"), 2)
        self.labelset = sorted(list(set(self.train_set[1])))
        self.wordset = list(self.train_set[2])
        self.labelnum = []
        self.calcPemotion()
        self.writepath = "15352408_zhangjiawei_NB_classification.csv"

    def loadcsv(self, filepath, Type=1):
        with open(filepath, 'r') as file:
            reader = csv.reader(file)
            next(reader)
            words, label = [], []
            st = 0 if Type == 1 else 1
            for row in reader:
                words.append(row[st].split(' '))
                label.append(row[st + 1])         
        Set = set([x for line in words for x in line])
        return [words, label, Set]

    def calcPemotion(self):
        self.Pemotion = []
        NtrainDoc = len(self.train_set[0])
        for emotion in self.labelset:
            num = self.train_set[1].count(emotion)
            self.labelnum.append(num)
            self.Pemotion.append(num / NtrainDoc)
    
    def countword(self, ModelType):
        self.wordcnt = [dict() for i in range(len(self.labelset))]
        self.labeltotword = [0 for i in range(len(self.labelset))]
        for i in range(len(self.train_set[0])):
            label_index = self.labelset.index(self.train_set[1][i])
            if ModelType == "Multinomial":
                for word in self.train_set[0][i]:   
                    if word not in self.wordcnt[label_index]:
                        self.wordcnt[label_index][word] = 1
                    else:
                        self.wordcnt[label_index][word] += 1
                    self.labeltotword[label_index] += 1
            elif ModelType == "Bernoulli":
                for word in self.wordset:   
                    if word in self.train_set[0][i]:
                        if word not in self.wordcnt[label_index]:
                            self.wordcnt[label_index][word] = 1
                        else:
                            self.wordcnt[label_index][word] += 1

    def getwordnum(self, word, index):
        if word not in self.wordcnt[index]:
            return 0
        else:
            return self.wordcnt[index][word]

    def predict(self, ModelType, predictType, alpha):
        self.countword(ModelType)
        totword, cnt = len(self.wordset), 0
        if predictType == "validate":
            testset = self.valid_set
        else:
            testset = self.test_set
            writer = csv.writer(open(self.writepath, "w", encoding="utf-8", newline=""), dialect='excel')
            writer.writerow(["textid", "label"])
        for i in range(len(testset[0])):
            p = [1.0 for j in range(len(self.labelset))]
            for j in range(len(self.labelset)):
                for word in testset[0][i]:
                    if word in self.wordset: 
                        num = self.getwordnum(word, j)
                        if ModelType == "Multinomial":          
                            p[j] *= ((num + alpha) / (self.labeltotword[j] + totword * alpha))
                        elif ModelType == "Bernoulli":
                            p[j] *= (num + alpha) / (self.labelnum[j] + 2*alpha)
                p[j] *= self.Pemotion[j]
            p = list(np.array(p) / np.sum(np.array(p)))
            label = self.labelset[p.index(max(p))]
            if predictType == "validate" and label == testset[1][i]:
                cnt += 1
            elif predictType != "validate":
                writer.writerow([i+1, label])
        print("Prediction has finished")
        if predictType == "validate":
            print("Acc", cnt / len(testset[0])) 
            return cnt / len(testset[0])

if __name__ == "__main__":
    currentpath = os.getcwd()
    INS1 = Classify(currentpath)
    a = np.arange(0.01, 1.01, 0.01)
    maxp = 0
    maxa = 0.55
    """
    for i in range(len(a)):
        print("\n", a[i])
        p = INS1.predict("Multinomial", "validate", a[i])
        p = INS1.predict("Bernoulli", "validate", a[i])
        if p > maxp:
            maxp = p
            maxa = a[i]
    """
    #p = INS1.predict("Multinomial", "validate", 0.55)
    #print("alpha= ", maxa, "Validate Accauracy= ", maxp)
    #p = INS1.predict("Multinomial", "validate", maxa)
    #p = INS1.predict("Bernoulli", "validate", maxa)
    INS1.predict("Multinomial", "test", maxa)

    #INS1.validate("Bernoulli")