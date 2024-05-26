# -*- coding: utf-8 -*-
"""
Created on Thu Mar 28 10:07:20 2024

@author: dell
"""

import pandas as pd

file_name = "powder.xlsx"

# Load the entire Excel file
df = pd.read_excel(file_name)

# Now select the data from this column
comments = df['评论内容']

# Begin cleaning the comments
# Remove expressions containing [R]
comments = comments.str.replace(r'\[.*?R\]', '', regex=True)
# Remove @username mentions
comments = comments.str.replace(r'@\w+', '', regex=True)
# Remove "thank you"
comments = comments.str.replace('谢谢', '', regex=False)
# Remove "okk"
comments = comments.str.replace('okk', '', regex=False)
# Remove laughter expressions like "haha"
comments = comments.str.replace(r'哈哈+', '', regex=True)
# Remove [doge] expression
comments = comments.str.replace(r'\[doge\]', '', regex=True)

# Additional cleaning operations
comments = comments.str.replace('好的', '', regex=True)
comments = comments.str.replace('客气', '', regex=True)
comments = comments.str.replace('感谢', '', regex=True)
comments = comments.str.replace('你好', '', regex=True)
comments = comments.str.replace('您好', '', regex=True)
comments = comments.str.replace('博主', '', regex=True)
comments = comments.str.replace(r'[a-zA-Z0-9 ]', '', regex=True)
comments = comments.str.replace(r'[^\w\s]', '', regex=True)

# Convert all comments to string
comments = comments.astype(str)

# Remove all "@username" mentions, including those without a username "@"
df['评论内容'] = df['评论内容'].str.replace(r'@\w* ?', '', regex=True)

# Cleaned comments are already stored in the comments variable, if you need to update the original DataFrame
df.iloc[:, 1] = comments

# Remove blank comments and their corresponding note links and comment likes
# Assuming '笔记链接' and '评论点赞数' are the titles of the relevant columns
df = df.dropna(subset=['评论内容'])  # Delete rows where the comment content is empty
df = df[df['评论内容'].str.strip() != '']  # Delete rows where the comment content contains only spaces

# If you need to save the cleaned data to a new Excel file
df.to_excel('cleaned_powder.xlsx', index=False)  # Comment in English
