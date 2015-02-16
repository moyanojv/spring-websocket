<!DOCTYPE HTML>
<html lang="en">
  <head>
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700" rel="stylesheet" type="text/css" />
    <link href="assets/style.css" rel="stylesheet" type="text/css" />
  </head>

    <script src="libs/sockjs/sockjs.min.js" type="text/javascript"></script>
    <script src="libs/stomp-websocket/lib/stomp.min.js" type="text/javascript"></script>

	<script type="text/javascript">
		var socket = new SockJS('/spring-ng-chat/chat');
		var client = Stomp.over(socket);
		 
		var onConnect = function() {
		  client.subscribe("/topic/message", function(message) {
		      console.log(message);
		  });
		};
		client.connect('guest', 'guest', onConnect);

	
	</script>
    

  </body>
</html>
