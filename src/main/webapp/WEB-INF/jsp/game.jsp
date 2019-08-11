<%@page import="am.aca.spring.games.beans.GameBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Let's Play</title>
</head>
<body>
		<script type="text/javascript">
			var yourTurn = ${turn} || false;
			
			// if it's not your tourn check move
			setTimeout(check, 2000);
			function check() {
				if(!yourTurn){
					var ajax = new XMLHttpRequest();
					ajax.onreadystatechange = processCheck;
			
					ajax.open("GET", "/check?id=${id}&player=${player}", true);
					ajax.send(null);
				}
				else{
				}
			}
			function processCheck() {
				if (this.readyState == 4 && this.status == 200) {
					var val = this.responseText;
					if(val == "" || val == "-1"){
						setTimeout(check, 2000);
					}
					else{
						document.getElementById(val).innerHTML="o";
						yourTurn = true;
					}
				}		
			}
			function send(td) {
				if(yourTurn){
					yourTurn = false;
					var id = td.getAttribute("id");
					var ajax = new XMLHttpRequest();
					ajax.onreadystatechange = processResult;
			
					ajax.open("POST", "/game?id=${id}&player=${player}", true);
					ajax.setRequestHeader("Content-type", "application/json");
					ajax.send(JSON.stringify({"cell_id":id}));
				}
				else{
					alert("It's not your tourn");
				}
			}
			function processResult() {
				if (this.readyState == 4 && this.status == 200) {
					var val = this.responseText;
					if(val != "-1"){
						document.getElementById(val).innerHTML="x";
						setTimeout(check, 2000);
					}
					else{
						yourTurn = true;
					}
				}		
			}
		</script>
		<p>GameId: ${id}</p>
		<table border="1">
			<tr><td colspan="3">Player: ${player}</td></tr>
			<tr>
				<td id="0" style="width: 30px; height: 30px" onclick="send(this)">${table[0]}</td>
				<td id="1" style="width: 30px; height: 30px" onclick="send(this)">${table[1]}</td>
				<td id="2" style="width: 30px; height: 30px" onclick="send(this)">${table[2]}</td>
			</tr>
 			<tr>
				<td id="3" style="width: 30px; height: 30px" onclick="send(this)">${table[3]}</td>
				<td id="4" style="width: 30px; height: 30px" onclick="send(this)">${table[4]}</td>
				<td id="5" style="width: 30px; height: 30px" onclick="send(this)">${table[5]}</td>
			</tr>
			<tr>
				<td id="6" style="width: 30px; height: 30px" onclick="send(this)">${table[6]}</td>
				<td id="7" style="width: 30px; height: 30px" onclick="send(this)">${table[7]}</td>
				<td id="8" style="width: 30px; height: 30px" onclick="send(this)">${table[8]}</td> 
			</tr>
			<tr><td colspan="3">Player: ${opponent}</td></tr>
		</table>
</body>
</html>
