# Java gRPC Learning

## what is gRPC and Protobuf
gRPC is a open source HTTP 2 based intercommunication network protocol designed to communicate between server and client. It allows two different applications to communicate with one another even if they are written in different language. The client calls the remote procedure/ method available on server - and it appears like the remote method is available in the client itself. 
The gRPC uses the Protocol Buffer for serializing the data, similar to JSON or AVRO. Protobuf defines the structure of the message to be exchanged in the .proto file. The same proto file can be utilized by the client and server and helps to generate the code in required language.

gRPC mainly provides communication - 
	1. Unary RPC - single request and get response back from server
	2. Client side streaming - where a client can send a stream of messages to server
	3. Server side streaming - where a server can return a stream of messages from server to client
Multiplexing - where both client and server can serve the stream

## How to Run the project
- Run the main methods to know about the basics working with protobuf generated files
- 

## References
This project is created from a course taken by Udemy - 
https://www.udemy.com/course/grpc-the-complete-guide-for-java-developers/