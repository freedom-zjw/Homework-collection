# -*- coding: utf-8 -*-
"""
step1：首先是各种工具包的导入和测试样例的导入
"""
from time import time  #导入time模块，用于测算一些步骤的时间消耗
import matplotlib.pyplot as plt #导入画图模块
import numpy as np #导入矩阵计算模块
import scipy as sp #导入科学计算模块
from sklearn.decomposition import MiniBatchDictionaryLearning #导入一种字典学习方法
from sklearn.feature_extraction.image import extract_patches_2d
#导入碎片提取函数，碎片提取函数将图片分割成一个一个小块(pitch)
from sklearn.feature_extraction.image import reconstruct_from_patches_2d
#导入图片复原函数，可通过pitch复原一整张图片
from sklearn.utils.testing import SkipTest
#导入测试工具nose下的异常抛出函数SkipTest
from sklearn.utils.fixes import sp_version
#导入SciPy版本检测函数sp_version用于检测版本高低，版本低于0.12的SciPy没有我们需要的样本测试
if sp_version < (0, 12):
    raise SkipTest("Skipping because SciPy version earlier than 0.12.0 and "
                   "thus does not include the scipy.misc.face() image.")
#下面是测试样例导入，我们使用的是scipy库自带的测试图片
try:
    from scipy import misc
    face = misc.face(gray=True)
except AttributeError:
    # Old versions of scipy have face in the top level package
    face = sp.face(gray=True)
    
"""
step2：通过测试样例计算字典V
Convert from uint8 representation with values between 0 and 255 to
a floating point representation with values between 0 and 1.
"""
face = face / 255.0
#读入的face大小在0~255之间，所以通过除以255将face的大小映射到0~1上去
face = face[::2, ::2] + face[1::2, ::2] + face[::2, 1::2] + face[1::2, 1::2]
face = face / 4.0
#以上两行对图形进行采样，把图片的长和宽各缩小一般。
#记住array矩阵的访问方式 array[起始点：终结点（不包括）：步长]
height, width = face.shape
print('Distorting image...')
distorted = face.copy()
#将face的内容复制给distorted，这里不用等号因为等号在python中其实是地址的引用
distorted[:, width // 2:] += 0.075 * np.random.randn(height, width // 2)
#对照片的右半部分加上噪声，之所以左半部分不加是因为想要产生一个对比的效果
print('Extracting reference patches...')
t0 = time()#开始计时，并保存在t0中
patch_size = (7, 7)#tuple格式的pitch大小
data = extract_patches_2d(distorted[:, :width // 2], patch_size) #对图片的左半部分（未加噪声的部分）提取pitch
data = data.reshape(data.shape[0], -1)
#用reshape函数对data(94500,7,7)进行整形，reshape中如果某一位是-1，则这一维会根据
#（元素个数/已指明的维度）来计算这里经过整形后data变成（94500，49）
data -= np.mean(data, axis=0)
data /= np.std(data, axis=0)
#以上两行作用在于每一行的data减去均值除以方差，这是zscore标准化的方法
print('done in %.2fs.' % (time() - t0))
print('Learning the dictionary...')
t0 = time() #开始计时，并保存在t0中
dico = MiniBatchDictionaryLearning(n_components=100, alpha=1, n_iter=500)
#上面这行初始化MiniBatchDictionaryLearning类，并按照初始参数初始化类的属性
V = dico.fit(data).components_ 
#调用fit方法对传入的样本集data进行字典提取，components_返回该类fit方法的运算结果，也就是我们想要的字典V
dt = time() - t0
print('done in %.2fs.' % dt)
plt.figure(figsize=(4.2, 4))#figsize方法指明图片的大小，4.2英寸宽，4英寸高。其中一英寸的定义是80个像素点
for i, comp in enumerate(V[:100]):#：循环画出100个字典V中的字
    plt.subplot(10, 10, i + 1)
    plt.imshow(comp.reshape(patch_size), cmap=plt.cm.gray_r,
               interpolation='nearest')
    plt.xticks(())
    plt.yticks(())
plt.suptitle('Dictionary learned from face patches\n' +
             'Train time %.1fs on %d patches' % (dt, len(data)),
             fontsize=16)
plt.subplots_adjust(0.08, 0.02, 0.92, 0.85, 0.08, 0.23)#left, right, bottom, top, wspace, hspace

'''
step3：画出标准图像和真正的噪声，方便同之后字典学习学到的噪声相比较
'''
def show_with_diff(image, reference, title):
    """Helper function to display denoising"""
    plt.figure(figsize=(5, 3.3))
    plt.subplot(1, 2, 1)
    plt.title('Image')
    plt.imshow(image, vmin=0, vmax=1, cmap=plt.cm.gray,
               interpolation='nearest')
    plt.xticks(())
    plt.yticks(())
    plt.subplot(1, 2, 2)
    difference = image - reference
    plt.title('Difference (norm: %.2f)' % np.sqrt(np.sum(difference ** 2)))
    plt.imshow(difference, vmin=-0.5, vmax=0.5, cmap=plt.cm.PuOr,
               interpolation='nearest')
    plt.xticks(())
    plt.yticks(())
    plt.suptitle(title, size=16)
    plt.subplots_adjust(0.02, 0.02, 0.98, 0.79, 0.02, 0.2)
show_with_diff(distorted, face, 'Distorted image')

'''
step4：测试不同的字典学习方法和参数对字典学习的影响
'''
print('Extracting noisy patches... ')
t0 = time()
data = extract_patches_2d(distorted[:, width // 2:], patch_size)#提取照片中被污染过的右半部进行字典学习。
data = data.reshape(data.shape[0], -1)
intercept = np.mean(data, axis=0)
data -= intercept
print('done in %.2fs.' % (time() - t0))
transform_algorithms = [  #这里是四中不同字典的表示策略
    ('Orthogonal Matching Pursuit\n1 atom', 'omp',
     {'transform_n_nonzero_coefs': 1}),
    ('Orthogonal Matching Pursuit\n2 atoms', 'omp',
     {'transform_n_nonzero_coefs': 2}),
    ('Least-angle regression\n5 atoms', 'lars',
     {'transform_n_nonzero_coefs': 5}),
    ('Thresholding\n alpha=0.1', 'threshold', {'transform_alpha': .1})]
reconstructions = {}
for title, transform_algorithm, kwargs in transform_algorithms:
    print(title + '...')
    reconstructions[title] = face.copy()
    t0 = time()
    dico.set_params(transform_algorithm=transform_algorithm, **kwargs)#通过set_params对第二阶段的参数进行设置
    code = dico.transform(data)
    #transform根据set_params对设完参数的模型进行字典表示，表示结果放在code中。
    #code总共有100列，每一列对应着V中的一个字典元素，所谓稀疏性就是code中每一行的大部分元素都是0，
    #这样就可以用尽可能少的字典元素表示回去。
    patches = np.dot(code, V)#code矩阵乘V得到复原后的矩阵patches
    patches += intercept
    patches = patches.reshape(len(data), *patch_size)#将patches从（94500，49）变回（94500，7，7）
    if transform_algorithm == 'threshold':#
        patches -= patches.min()
        patches /= patches.max()
    reconstructions[title][:, width // 2:] = reconstruct_from_patches_2d(
        patches, (height, width // 2))#通过reconstruct_from_patches_2d函数将patches重新拼接回图片
    dt = time() - t0
    print('done in %.2fs.' % dt)
    show_with_diff(reconstructions[title], face,
                   title + ' (time: %.1fs)' % dt)
plt.show()
