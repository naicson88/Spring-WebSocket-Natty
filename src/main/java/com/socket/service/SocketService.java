package com.socket.service;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.socket.model.Message;
import com.socket.model.MessageType;

@Service
public class SocketService {
	
	public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
		
		for(SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
			if(!client.getSessionId().equals(senderClient.getSessionId())) {
				client.sendEvent(eventName, new Message(MessageType.SERVER, message));
			}
		}
	}
	
}
