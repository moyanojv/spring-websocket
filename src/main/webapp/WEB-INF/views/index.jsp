<!DOCTYPE HTML>
<html lang="en">
  <head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
  	<link rel="stylesheet" href="assets/basic.css">
  </head>

	
	
	
	<div class="jumbotron">
		<div class="container">
		<h1>Test this new feature!</h1>
		<form class="form-horizontal">
			<div class="form-group">
				<label for="input" class="col-sm-2 control-label">Message</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="input" placeholder="Type a message">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-primary" id="send_button">Send</button>
				</div>
			</div>
		</form>
		</div>

	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<ul id="messages">

			</ul>
		</div>

	</div>

	
	
	
<!-- Latest compiled and minified JavaScript -->
	<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="libs/sockjs/sockjs.min.js" type="text/javascript"></script>
    <script src="libs/stomp-websocket/lib/stomp.min.js" type="text/javascript"></script>
    <script src="libs/moment/moment.js" type="text/javascript"></script>


	

	<script type="text/javascript">
		var socket = new SockJS('/spring-websocket/mysocket');
		var client = Stomp.over(socket);
		 
		var onConnect = function() {
		  client.subscribe("/topic/message", function(data) {
			  var message = JSON.parse(data.body)
			  var out = {};
		      out.message = message.message;
		      out.time = new Date(message.time);
			  var li = $('<li/>').addClass('list-group-item').text(moment(out.time).format('MM/DD/YYYY, HH:mm:ss [   ]') + out.message);
			  $("#messages").prepend(li);
		      console.log(message);
		  });
		};
		
		client.connect({}, onConnect);
		
		var send = function(id, message) {
			client.send("/app/message", 
					{
						priority: 9
		      		}, JSON.stringify({
		      			id: id,
		      			message: message
		      		}
		      	)
			);
		};
		
		$("#send_button").click(function() {
			var text = $("#input").val();
			send(Math.floor(Math.random() * 1000000), text);
		});

	
	</script>
  </body>
</html>
