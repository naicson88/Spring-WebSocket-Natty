package com.socket.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
public class ServerCommandLineRunner implements CommandLineRunner{
	
	private final SocketIOServer server;
	
	public ServerCommandLineRunner(SocketIOServer server) {
		super();
		this.server = server;
	}

	@Override
	public void run(String... args) throws Exception {
		server.start();
	}
}
