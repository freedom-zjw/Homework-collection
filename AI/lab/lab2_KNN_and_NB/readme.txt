每个代码都默认了同时要读train_set、validation_set、test_set，所以在代码文件夹里
已经提供好，要改数据请直接在相应文件中按对应格式改，否则会出错

KNN:
  预测验证集数据的接口：
	tryk（MatrixType，standard，disType）
		MatrixType 可选项：onehot
		standard 可选项：original normalization
		disType 可选项： Manhattan  Euclidean 以及 Cosine
  预测测试集数据接口：
	predict（MatrixType，standard，disType）
	用法同上

NB
  分类：predict(ModelType，predictType, alpha)
		ModelType 可选项：Multinomial   Bernoulli
                predictType 可选项：validate：预测验证集
                                    test：    预测测试集
                alpha: 0~1 的任意浮点数
  
  回归：predict(predictType, alpha)
        参数意义同分类