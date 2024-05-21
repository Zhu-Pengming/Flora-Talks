# PlantAdvisorGPT2

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


  ### Since there are very few such data sets, I originally intended to get the data (crawlers) from the comments on small red books. However, although I cleaned the data and generated a word cloud map, the model failed to deliver the desired results. Since the initial data set was not working well, I switched to the data set from Plantwise Plus Knowledge Bank. This dataset contains solutions to various problems about plants. 
  
  ## Dataset(dataset.xlsx)
  The dataset is loaded from an Excel file stored on Google Drive, containing pairs of questions and answers about plant diseases. The data is processed to fit the model's input requirements.
  
  
  ## Overview of the Model
  
  ### Input Layer
  The model input is a plant care problem. The text is processed through GPT-2's word divider to generate the corresponding input vector.
  
  ### Hidden Layer
  The GPT-2 model has multiple hidden layers, each containing many neurons that process input data through a fully connected layer.
  
  ### Output Layer
  The output layer generates the final text output of the model, which is the answer to the input question.
  
  ### Loss Function
  The cross entropy loss function is used to measure the difference between the generated text and the real answer. Losses are optimized through backpropagation during training.
  
  ### Weight and Bias
  Each connection of the GPT-2 model has a corresponding weight and bias, and these parameters are constantly adjusted during training to minimize losses.
  
  ### Activate Function
  GPT-2 models use a variety of activation functions, such as ReLU, to help the model learn complex patterns when processing and generating natural language text.
  
  ### Optimization Function
  Using the Adam optimizer, the model parameters are constantly adjusted during training to improve the accuracy of the generated answers.
  
  ### Training Process
  The training function implements model training through the following steps:
  - Iterate over epochs and batches.
  - Pass inputs through the model and calculate losses.
  - Perform backpropagation and optimization.
  - Print losses every 10 batches to monitor training progress.
  Once the training is complete, the fine-tuned model and word divider are saved to Google Drive.
  
  ### Generate Text
  A function is defined to generate text based on input prompts. Use nucleus sampling and top-k sampling to ensure variety and quality of generated text.

  
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
[Plant Disease Recognition Dataset on HuggingFace](https://huggingface.co/datasets/NouRed/plant-disease-recognition)

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
    






