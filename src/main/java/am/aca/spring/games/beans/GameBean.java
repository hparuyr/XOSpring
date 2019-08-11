package am.aca.spring.games.beans;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class GameBean {
	private Integer id;
	private String player1;
	private String player2;
	private String state;
	private String turn;
	private String winner;
	private Integer lastMove;
	
	public static final Character[] DEFAULT_STATE = { '_', '_', '_', '_', '_', '_', '_', '_', '_' };
	
	public GameBean() {
	}

	public Map<String, Object> toMap(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", id);
		result.put("player1", player1);
		result.put("player2", player2);
		result.put("turn", turn);
		
		result.put("table", new Gson().fromJson(state, Character[].class));
		
		return result;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public static boolean changeCell(Character[] table, Integer cellId, char value) {
		if (table[cellId] == '_') {
			table[cellId] = value;
			return true;
		}
		return false;
	}

	public boolean checkFinish() {
		// Here should be added win check logic
		// if someone win put player name into winner field
		return false;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getLastMove() {
		return lastMove;
	}

	public void setLastMove(Integer lastMove) {
		this.lastMove = lastMove;
	}
}
