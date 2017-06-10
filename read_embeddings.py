#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import print_function

import os
import sys
import numpy as np
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from keras.utils import to_categorical
from keras.layers import Dense, Input
from keras.layers import Embedding, LSTM
from keras.models import Model
from sklearn.metrics.pairwise import cosine_similarity


BASE_DIR = '/home/ercan'
TURQUAS_DIR = BASE_DIR + '/BitirmeProjesi'
WORD2VEC_VALUES = 'embeddings.txt'
SENTENCES = 'sentences.txt'
CANDIDATES = 'candidates.txt'
QUESTION = 'question.txt'
TEST_DATA = 'test_data.txt'
MAX_SEQUENCE_LENGTH = 20
EMBEDDING_DIM = 200
ANSWER_COUNT = 5


# load sentences from text file
print('Loading sentences.')
sentences = []
labels_index = {}
f = open(os.path.join(TURQUAS_DIR, SENTENCES))
for line in f:
    sentences.append(line.lower()) # read sentences from txt
f.close()
print('Found %s sentences.' % len(sentences))



#load word vectors and index them to a dictionary
print('Indexing word vectors.')
embeddings_index = {}
f = open(os.path.join(TURQUAS_DIR, WORD2VEC_VALUES))
for line in f:
    values = line.split() # split word and vector
    word = values[0]
    vector = np.asarray(values[1:], dtype='float32')
    embeddings_index[word] = vector # assign vector value to word in dict
f.close()
print('Found %s word vectors.' % len(embeddings_index))


#tokenize sentences and get word_index
print('Tokenizing the sentences')
tokenizer = Tokenizer(num_words=len(embeddings_index)) # create Tokenizer
tokenizer.fit_on_texts(sentences) # fit sentences into Tokenizer
sequences = tokenizer.texts_to_sequences(sentences) # convert texts to sequences
word_index = tokenizer.word_index # create word-index dictionary
data = pad_sequences(sequences, maxlen=MAX_SEQUENCE_LENGTH) # create sentences with pads

for word, vector in embeddings_index.items():
	if word not in tokenizer.word_index:
		tokenizer.word_index[word] = len(tokenizer.word_index) + 1

print('Found %s unique tokens.' % len(tokenizer.word_index))


#create embedding matrix for embedding layer
num_words = len(tokenizer.word_index) + 1 # set total number of words 
embedding_matrix = np.zeros((num_words, EMBEDDING_DIM)) # create embedding matrix for word vectors
for word, i in tokenizer.word_index.items(): # for every word in word_index dict
    if i >= num_words:
        continue
    embedding_vector = embeddings_index.get(word) # get vector value of word
    if embedding_vector is not None:
        # words not found in embedding index will be all zeros
        embedding_matrix[i] = embedding_vector # assign vector of that word to its index from word_index dict


# load pre-trained word embeddings into an Embedding layer
# trainable = False means the embeddings are fixed
embedding_layer = Embedding(num_words,
                            EMBEDDING_DIM,
                            weights=[embedding_matrix],
                            input_length=MAX_SEQUENCE_LENGTH,
                            trainable=False)
sequence_input = Input(shape=(MAX_SEQUENCE_LENGTH,), dtype='int32')
embedded_sequences = embedding_layer(sequence_input)
lstm = LSTM(512, return_sequences=False)(embedded_sequences)
dense = Dense(200, activation='linear')(lstm)

model = Model(sequence_input, dense)
model.compile(loss='cosine_proximity',
              optimizer='rmsprop')
#sentence_bank = model.predict(data)


def readQuestion():
	question = []
	f = open(os.path.join(TURQUAS_DIR, QUESTION))
	for line in f:
	    question.append(line.lower())
	f.close()
	return question

# load candidates from text file
def readCandidates():
	print('Loading candidates.')
	candidates = []
	labels_index = {}
	f = open(os.path.join(TURQUAS_DIR, CANDIDATES))
	for line in f:
	    candidates.append(line.lower())
	f.close()
	print('Found %s candidates.' % len(candidates))
	return candidates


while True:
	choice = raw_input("continue(yes/no)?: ")
	choice = str(choice)
	while choice != 'yes':
		print("xd")
		choice = raw_input("continue(y/n)?: ")

	candidates = readCandidates()
	sequences = tokenizer.texts_to_sequences(candidates) 
	candidate_data = pad_sequences(sequences, maxlen=MAX_SEQUENCE_LENGTH)
	candidate_bank = model.predict(candidate_data)	

	question = readQuestion()
	question_sequence = tokenizer.texts_to_sequences(question)
	question_data = pad_sequences(question_sequence, maxlen=MAX_SEQUENCE_LENGTH)
	question_pred = model.predict(question_data)

	cosine_sim = cosine_similarity(question_pred, candidate_bank)
	sorted_list = np.argsort(-cosine_sim)
	sorted_list = sorted_list[0][:ANSWER_COUNT]

	file = open("answer.txt", "w")
	for index in sorted_list:
		file.write("%s%s\n" %(candidates[index], str(cosine_sim[0][index])))
		print("Answer: %s \nScore: %.4f " %(candidates[index], cosine_sim[0][index]))
	file.close()

	# test_sentences = []
	# sentence = raw_input("Please enter something: ")
	# test_sentences.append(sentence.lower())
	# sequence = tokenizer.texts_to_sequences(test_sentences) # convert texts to sequences
	# print(sequence)
	# sample = pad_sequences(sequence, maxlen=MAX_SEQUENCE_LENGTH)
	# sample_pred = model.predict(sample)

	# cosine_sim = cosine_similarity(sample_pred, sentence_bank)
	# sorted_list = np.argsort(-cosine_sim)
	# sorted_list = sorted_list[0][:ANSWER_COUNT]
	# print(cosine_sim)
	# print(sorted_list)
	# for index in sorted_list:
	# 	print("Answer: %s \nScore: %.4f " %(sentences[index], cosine_sim[0][index]))









# testtttttttttttttt
# print('Loading test sentences.')
# test_data_x = []
# test_data_y = []
# f = open(os.path.join(TURQUAS_DIR, TEST_DATA))
# for line in f:
# 	qa = line.split("|")
# 	question = qa[0]
# 	answer = qa[1]

# 	test_data_x.append(question.lower())
# 	test_data_y.append(answer.lower())
# f.close()
# print('Found %s test sentences.' % len(test_data_x))


# score = 0.0
# for question, answer in zip(test_data_x, test_data_y):
# 	test_sentence = []
# 	test_sentence.append(question)
# 	sequence = tokenizer.texts_to_sequences(test_sentence)
# 	sample = pad_sequences(sequence, maxlen=MAX_SEQUENCE_LENGTH)
# 	sample_pred = model.predict(sample)

# 	cosine_sim = cosine_similarity(sample_pred, sentence_bank)
# 	sorted_list = np.argsort(-cosine_sim)
# 	sorted_list = sorted_list[0][:ANSWER_COUNT]

# 	count = 1
# 	for index in sorted_list:
# 		if sentences[index] == answer:
# 			score = score + 1.0 / count
# 		count = count + 1


