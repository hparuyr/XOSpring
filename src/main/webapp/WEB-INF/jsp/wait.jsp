<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wait we are looking for someone</title>
</head>
<body>
	<p>GameId: ${id}</p>
	<p>Player: ${player}</p>
	<p>Waiting...</p>
	<img alt="Loading.." src="https://media0.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif">
	<script type="text/javascript">
		// reloading page each 3 seconds to see if oponent found
		function check() {
			var ajax = new XMLHttpRequest();
			ajax.onreadystatechange = processResult;
	
			ajax.open("GET", "/wait?gameId=" + ${id}, true);
			ajax.send(null);
		}
		function processResult() {
			if (this.readyState == 4 && this.status == 200) {
				if(this.responseText == ""){
					console.log("Waiting...");
					setTimeout(check, 3000);
				}
				else{
					var url = "/" + this.responseText + "?id=${id}&player=${player}";
					console.log(url);
					window.location = url;
				}
			}		
		}
		check();
	</script>
</body>
</html>