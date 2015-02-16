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
		DelayedDeLaMuerte job = new DelayedDeLaMuerte();
		job.setMessagingTemplate(messagingTemplate);
		job.setTopic("/topic/message");
		new Thread(job).start();
		return new OutputMessage(message, new Date());
	}
	
	public void sendMessage(String destination, Object message) throws InterruptedException {
		Thread.sleep(550);
		messagingTemplate.convertAndSend(destination, createMessage("inicializando tareas"));
		Thread.sleep(1350);
		messagingTemplate.convertAndSend(destination, createMessage("realizando tareas"));
		Thread.sleep(2850);
		messagingTemplate.convertAndSend(destination, createMessage("tareas realizadas"));
	}
	
	public OutputMessage createMessage(String message) {
		Message tmp = new Message(1, message);
		return new OutputMessage(tmp, new Date());
	}
	
	private class DelayedDeLaMuerte implements Runnable {
		
		private SimpMessageSendingOperations messagingTemplate;
		private String topic;

		public SimpMessageSendingOperations getMessagingTemplate() {
			return messagingTemplate;
		}

		public void setMessagingTemplate(SimpMessageSendingOperations messagingTemplate) {
			this.messagingTemplate = messagingTemplate;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}



		@Override
		public void run() {
			try {
				Thread.sleep(550);
				messagingTemplate.convertAndSend(topic, createMessage("inicializando tareas"));
				Thread.sleep(350);
				messagingTemplate.convertAndSend(topic, createMessage("realizando tareas"));
				Thread.sleep(850);
				messagingTemplate.convertAndSend(topic, createMessage("tareas realizadas"));				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
