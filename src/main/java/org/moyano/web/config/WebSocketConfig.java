package org.moyano.web.config;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = "org.moyano.web.controller")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
	// "/topic" is being registered as a endpoint where is it possible to be subscripted
    config.enableSimpleBroker("/topic");
    
    // "/app" is the application prefix where Spring MVC will listen for browser 
    // requests encoded within the STOMP message frame
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
	  
	//  "/mysocket" is the websocket endpoint
    registry.addEndpoint("/mysocket").withSockJS();
  }
}

// from http://jmesnil.net/stomp-websocket/doc/

//********** client
//service.SOCKET_URL = "/spring-ng-chat/XXXXX";  -->  XXXX "server's websocket endpoint"  (mysocket)
// *** client configuration 1
// var client = Stomp.client(url);  --> defaut browser websocket implementation 1
// *** client configuration 2
// var ws = new SockJS(url); var client = Stomp.over(ws) --> stomp over sockjs


//****** subscribe to recive message
// client.subscribe("/out/*******", callback); - /out/*****

//***** send messages

// client.send("/in/******", {priority: 9}, "Hello, STOMP");  -> /in/******


