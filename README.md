#小红书博客和评论爬虫

这个Python项目由两个脚本组成，旨在根据指定的关键字和注释url从小红书(中国流行的社交媒体平台)中抓取注释和评论数据。

##脚本1:博客

第一个脚本使用指定的关键字获取注释信息。它包括各种功能，如登录、搜索、选择笔记类别、获取笔记详细信息、滚动以及以Excel和文本格式保存数据。

# # #的特性

-登录小红书(首次运行需要扫描二维码)
—使用关键字搜索笔记
-选择笔记类别(例如，全部，图像和文字，视频)
—获取标题、作者、点赞、备注链接等详细信息
-向下滚动页面以加载更多笔记
-将抓取的数据保存到具有自动调整列大小的Excel文件中
—“保存笔记”链接到文本文件

##脚本2:评论

第二个脚本根据指定的注释url抓取注释数据。该脚本包括从文本文件中读取url、转换时间戳、获取注释、处理多级注释以及将数据保存为Excel格式的函数。

# # #的特性

—从文本文件中读取笔记url
-获取和处理主要和次要评论
-将时间戳转换为可读格式
-将抓取的数据保存到Excel文件中，每200项自动保存一次


#小红书评论清理

这个Python脚本旨在清理从小红书(中国流行的社交媒体平台)上抓取的评论数据。该脚本读取包含抓取数据的Excel文件，选择comments列，并执行各种清理操作，例如删除表情符号、提及和特定短语。它还处理空注释，并将清理后的数据保存回新的Excel文件。

# #特性

—加载包含抓取数据的Excel文件
—选择需要清理的注释列
-删除表情符号，提及和特定短语
-处理空注释
—将清理后的数据保存到新的Excel文件中

## Data Preparation

The data preparation phase involves scraping note and comment data from Xiaohongshu based on specified keywords and note URLs. This is accomplished using two separate scripts: a Note Scraper and a Comment Scraper. These scripts fetch and save data in Excel and text formats.

## Data Cleaning

The data cleaning phase is handled by a Comment Cleaner script. This script reads the scraped data from an Excel file, selects the comments column, and performs various cleaning operations such as removing emoticons, mentions, and specific phrases. It also handles empty comments and saves the cleaned data back to a new Excel file.









