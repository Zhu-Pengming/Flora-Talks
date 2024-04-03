# -*- coding: utf-8 -*-
"""
Created on Thu Mar 28 10:07:20 2024

@author: dell
"""

import pandas as pd

file_name = "powder.xlsx"

# 加载整个Excel文件
df = pd.read_excel(file_name)

# 现在选择这一列的数据
comments = df['评论内容']

# 开始清理评论
# 删除包含[R]的表情
comments = comments.str.replace(r'\[.*?R\]', '', regex=True)
# 删除@某人
comments = comments.str.replace(r'@\w+', '', regex=True)
# 删除"谢谢"
comments = comments.str.replace('谢谢', '', regex=False)
# 删除"okk"
comments = comments.str.replace('okk', '', regex=False)
# 删除包含"哈哈"的语气词
comments = comments.str.replace(r'哈哈+', '', regex=True)
# 删除[doge]表情
comments = comments.str.replace(r'\[doge\]', '', regex=True)

comments = comments.str.replace('好的', '', regex=True)

comments = comments.str.replace('客气', '', regex=True)

comments = comments.str.replace('感谢', '', regex=True)

comments = comments.str.replace('你好', '', regex=True)

comments = comments.str.replace('您好', '', regex=True)

comments = comments.str.replace('博主', '', regex=True)







# 删除所有"@用户名"的提及，包括那些没有用户名的"@"
df['评论内容'] = df['评论内容'].str.replace(r'@\w* ?', '', regex=True)

# 清理后的评论已经保存在comments变量中，如果需要更新原DataFrame
df.iloc[:, 1] = comments

# 删除空白评论及其对应的笔记链接和评论点赞数
# 假设'笔记链接'和'评论点赞数'是相关列的标题
df = df.dropna(subset=['评论内容'])  # 删除评论内容为空的行
df = df[df['评论内容'].str.strip() != '']  # 删除评论内容仅包含空格的行

# 如果需要将清理后的数据保存到新的Excel文件
df.to_excel('cleaned_powder.xlsx', index=False)
