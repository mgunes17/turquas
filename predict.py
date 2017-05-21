from __future__ import print_function
import datetime
from keras.models import load_model
import numpy as np
import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = socket.gethostname()
port = 5555

vector_size = 300
model = load_model('dl_model.h5')
prediction = model.predict(np.expand_dims(np.random.random((1, vector_size)), axis=2))
print (prediction)

#prediction_input = np.loadtxt("/home/ercan/ideaprojects/turquas/question.txt")
#prediction_input = np.expand_dims(prediction_input, axis=2)
#prediction_input = prediction_input.reshape(1,vector_size,1)
#prediction = model.predict(prediction_input)

#start = datetime.datetime.now()
#prediction = model.predict(np.expand_dims(np.random.random((1, 100)), axis=2))
#end = datetime.datetime.now()
#delta2 = end - start
#print (delta2)

s.connect((host, port))
while True:
	print('ready to listen')
	data = s.recv(16384)
	#print('received msg', data)
	prediction_input = np.fromstring(data, dtype=float, sep=',')
	print(prediction_input)
	prediction_input = np.expand_dims(prediction_input, axis=2)
	prediction_input = prediction_input.reshape(1,vector_size,1)
	prediction = model.predict(prediction_input)
	prediction = str(np.array(prediction).tolist())
	s.send(prediction + '\n')
	print('sent msg')
s.close


#f = open('predict.txt', 'w')
#f.write(str(np.array(prediction).tolist()))  # python will convert \n to os.linesep
#f.close()














