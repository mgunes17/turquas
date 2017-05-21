from __future__ import print_function

from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation
from keras.layers import LSTM, Bidirectional
from keras.layers import Conv1D, MaxPooling1D
from keras.optimizers import SGD
import numpy as np

vector_size = 300

# Convolution
kernel_size = 5
filters = 64
pool_size = 1

# LSTM
lstm_output_size = 64

# Training
batch_size = 30
epochs = 20

x_train = np.loadtxt("/home/ercan/ideaprojects/turquas/input.txt")
y_train = np.loadtxt("/home/ercan/ideaprojects/turquas/output.txt")
x_train = np.expand_dims(x_train, axis=2)

x_test = np.loadtxt("/home/ercan/ideaprojects/turquas/valid_x.txt")
y_test = np.loadtxt("/home/ercan/ideaprojects/turquas/valid_y.txt")
x_test = np.expand_dims(x_test, axis=2)

print('Build model...')

model = Sequential()
model.add(Conv1D(filters, 
                 kernel_size, 
                 activation='relu', 
                 input_shape= (vector_size, 1)))
model.add(MaxPooling1D(pool_size=pool_size))
#model.add(Dropout(0.20))

#model.add(Conv1D(filters, kernel_size, activation='relu'))
#model.add(Conv1D(filters, kernel_size, activation='relu'))
#model.add(MaxPooling1D(pool_size=pool_size))
#model.add(Dropout(0.25))

model.add(Bidirectional(LSTM(lstm_output_size)))
model.add(Dense(vector_size))
model.add(Activation('linear'))
model.compile(loss='mean_squared_error',
              optimizer='sgd',
              metrics=['mae'])

print('Train...')
model.fit(x_train, y_train,
          batch_size=batch_size,
          epochs=epochs,
          validation_data=(x_test, y_test))
score, acc = model.evaluate(x_test, y_test, batch_size=batch_size)
print('Test score:', score)
print('Test accuracy:', acc)

model.save('dl_model.h5')


#prediction = model.predict(np.expand_dims(np.random.random((1, 100)), axis=2))

#f = open('predict.txt', 'w')
#f.write(str(np.array(prediction).tolist()))  # python will convert \n to os.linesep
#f.close()













