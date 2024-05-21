# Natural language processing 

## Data Preparation

  ### # Includes crawler code + data (uncleaned)
  
The data preparation phase involves scraping note and comment data from Xiaohongshu based on specified keywords and note URLs. This is accomplished using two separate scripts: a Note Scraper and a Comment Scraper. These scripts fetch and save data in Excel and text formats.

## Data Cleaning

  The data cleaning phase is handled by a Comment Cleaner script. This script reads the scraped data from an Excel file, selects the comments column, and performs various cleaning operations such as removing emoticons, mentions, and specific phrases. It also handles empty comments and saves the cleaned data back to a new Excel file.
  
  ### # Comment word cloud 
  
  #### ## Powder 
  ![](https://github.com/Zhu-Pengming/Flora-Talks/blob/main/NLP/Comment%20word%20cloud/wordcloud_powder.png)
  
  #### ## Rust
  ![](https://github.com/Zhu-Pengming/Flora-Talks/blob/main/NLP/Comment%20word%20cloud/wordcloud_rust.png)

  This project includes a GPT-2 model fine-tuned for plant disease recognition, implemented using the PyTorch library and the Hugging Face's transformers library.
  
  ## Model Architecture
  The model utilizes the pre-trained GPT-2, a large transformer-based model known for its effectiveness in natural language processing tasks. The fine-tuning adapts GPT-2 to generate responses based on plant disease-related queries.
  
  ## Dataset
  The dataset is loaded from an Excel file stored on Google Drive, containing pairs of questions and answers about plant diseases. The data is processed to fit the model's input requirements.
  
  ## Training
  The model is trained to minimize the cross-entropy loss using the Adam optimizer. The training process involves feeding batches of tokenized text data into the model, performing backpropagation, and updating the model's weights. Training progress is logged every ten batches, displaying the current epoch, batch index, and loss.
  
  ## Testing
  The model does not specify a separate testing phase within the provided script. However, evaluation typically involves checking the model's performance on unseen data to validate its generalization capabilities.
  
  ## Usage
  To use the fine-tuned model:
  1. Mount Google Drive to access the dataset and model files.
  2. Load the tokenizer and fine-tuned model from the specified directories.
  3. Set the model to evaluation mode for inference.

  ## Requirements
      Python
      PyTorch
      transformers library (from Hugging Face)
      pandas
      Google Colab for execution environment

  


# PlantDiseaseDetection_Image
This project contains a deep convolutional neural network (DeepCNN) model for image classification. The model is implemented using the PyTorch library.

  ## Model Architecture
  
  The model consists of six convolutional layers, three fully connected layers, and three max pooling layers. The convolutional layers are used to extract features from the input images, the max pooling layers are used to reduce the spatial dimensions of the output from the convolutional layers, and the fully connected layers are used to perform classification based on the extracted features.
  
  ## Dataset
  ![](https://huggingface.co/datasets/NouRed/plant-disease-recognition)
  The model is trained and tested on a dataset loaded using the `load_dataset` function from the `datasets` library. The dataset is split into training, validation, and testing sets.
  
  ## Training
  
  The model is trained using the Adam optimizer and the cross-entropy loss function. The training process includes forward propagation, loss computation, backpropagation, and optimizer step update.
  
  ## Testing
  
  The model's performance is evaluated on the testing set after the training process. The accuracy of the model on the testing set is printed out.
  
  ## Usage
  
  To train and test the model, run the `Training model.py` script.
  
  ## Requirements
  
  - Python
  - PyTorch
  - torchvision
  - datasets
  - PIL






