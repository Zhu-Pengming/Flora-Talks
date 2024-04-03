
# Data Preparation
  ### # Includes crawler code + data (uncleaned)
The data preparation phase involves scraping note and comment data from Xiaohongshu based on specified keywords and note URLs. This is accomplished using two separate scripts: a Note Scraper and a Comment Scraper. These scripts fetch and save data in Excel and text formats.

# Data Cleaning

The data cleaning phase is handled by a Comment Cleaner script. This script reads the scraped data from an Excel file, selects the comments column, and performs various cleaning operations such as removing emoticons, mentions, and specific phrases. It also handles empty comments and saves the cleaned data back to a new Excel file.









