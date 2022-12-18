package com.socket.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.socket.model.Message;
import com.socket.service.SocketService;

@Component
public class SocketModule {
	
	private final SocketIOServer server;
	private final SocketService socketService;
	
	Logger logger = LoggerFactory.getLogger(SocketModule.class);
	
	public SocketModule(SocketIOServer server, SocketService socketService) {
		this.server = server;
		this.socketService = socketService;

		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnect());
		server.addEventListener("send_message", Message.class, onChatReceived());
	}

	private DataListener<Message> onChatReceived() {
		return (senderClient, data, ackSender) -> {
			logger.info(data.toString());
			//senderClient.getNamespace().getBroadcastOperations().sendEvent("get_message", data.getMessage());
			socketService.sendMessage(data.getRoom(), "get_message", senderClient, data.getMessage());
		};
	}

	private DisconnectListener onDisconnect() {
		return client -> {
			logger.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
		};
	}

	private ConnectListener onConnected() {
		return (client) -> {		
			String room = client.getHandshakeData().getSingleUrlParam("room");
			client.joinRoom(room);
			logger.info("Socket ID [{}] Connected to socket", client.getSessionId().toString());
		};
	}
}
