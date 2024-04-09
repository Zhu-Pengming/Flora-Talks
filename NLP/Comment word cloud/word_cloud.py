# -*- coding: utf-8 -*-
"""
Created on Thu Mar 28 11:08:49 2024

@author: dell
"""

import matplotlib.pyplot as plt
from wordcloud import WordCloud
import pandas as pd

# 读取Excel文件
df = pd.read_excel('cleaned_powder.xlsx')

# 假设你的文本数据在名为'text_column'的列中
text_data = df['评论内容']

# 将所有文本数据连接成一个长字符串
text = ' '.join(text_data)

# 创建词云对象
wordcloud = WordCloud(font_path='simsun.ttf').generate(text)


# 使用matplotlib显示词云
plt.imshow(wordcloud, interpolation='bilinear')
plt.axis("off")
plt.show()

wordcloud.to_file('wordcloud_powder.png')                                 


