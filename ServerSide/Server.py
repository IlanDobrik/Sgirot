import socket
import json

# Constants
IP = "0.0.0.0"
PORT = 2513
DEFAULT_BUFFER_SIZE = 1024
LISTEN_COUNT = 5
FILE_PATH = "./sgirot.bin"

'''
C:  GET/POST
S:  GET - Send DB
    POST - Update DB
*END*
'''


class DB:
    # To json
    # json.dumps(['foo', {'bar': ('baz', None, 1.0, 2)}])
    # '["foo", {"bar": ["baz", null, 1.0, 2]}]'

    # From json
    # obj = ['foo', {'bar': ['baz', None, 1.0, 2]}]
    # json.loads('["foo", {"bar":["baz", null, 1.0, 2]}]') == obj
    
    def __init__(self):
        self.file = open(FILE_PATH, "wb")

def log(type, log):
    print(type(log), log)

def main():
    listen_socket = socket.socket()
    listen_socket.bind((IP, PORT))
    listen_socket.listen(LISTEN_COUNT)

    while True:
        try:
            client_sock, info = listen_socket.accept()
            data = client_sock.recv(DEFAULT_BUFFER_SIZE)
        except Exception as e:
            log(e)


if __name__ == "__main__":
    main()
