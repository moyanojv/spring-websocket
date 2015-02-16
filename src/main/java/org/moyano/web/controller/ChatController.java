package org.moyano.web.controller;

import java.util.Date;

import org.moyano.web.dto.Message;
import org.moyano.web.dto.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ChatController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String viewApplication() {
		return "index";
	}

	@RequestMapping(value="/index2", method = RequestMethod.GET)
	public String viewApplication2() {
		return "index2";
	}

	
	@MessageMapping("/message") // work with application prefix where Spring MVC will listen
	@SendTo("/topic/message")
	public OutputMessage sendMessage(Message message) throws InterruptedException {
		logger.debug("message");
		return new OutputMessage(message, new Date());
	}
}
