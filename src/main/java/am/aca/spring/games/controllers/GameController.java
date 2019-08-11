package am.aca.spring.games.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import am.aca.spring.games.beans.GameBean;
import am.aca.spring.games.db.XODao;

@Controller
public class GameController {
	private Gson json = new Gson();

	@Autowired
	private XODao dao;

	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public String read(@RequestParam("id") int id, @RequestParam("player") String player, ModelMap model) {
		GameBean game = dao.getGameById(id);
		model.addAttribute("id", game.getId());
		if (game.getPlayer1().equals(player)) {
			model.addAttribute("player", game.getPlayer1());
			model.addAttribute("opponent", game.getPlayer2());
			model.addAttribute("turn", true);
		} else {
			model.addAttribute("player", game.getPlayer2());
			model.addAttribute("opponent", game.getPlayer1());
			model.addAttribute("turn", false);
		}
		model.addAttribute("table", new Gson().fromJson(game.getState(), Character[].class));

		return "game";
	}

	@RequestMapping(value = "/game", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String update(@RequestParam("id") int id, @RequestParam("player") String player,
			@RequestBody String cell) {

		@SuppressWarnings("unchecked")
		Map<String, Object> result = json.fromJson(cell, Map.class);
		Integer cellId = Integer.parseInt(result.get("cell_id").toString());

		// Load needed game
		GameBean game = dao.getGameById(id);
		boolean changed = false;
		String nextPlayer = null;

		Character[] table = new Gson().fromJson(game.getState(), Character[].class);

		if (game.getPlayer1().equals(player)) {
			changed = GameBean.changeCell(table, cellId, '1');
			nextPlayer = game.getPlayer2();
		} else if (game.getPlayer2().equals(player)) {
			changed = GameBean.changeCell(table, cellId, '2');
			nextPlayer = game.getPlayer1();
		}
		if (changed) {
			dao.move(id, player, nextPlayer, new Gson().toJson(table, Character[].class), cellId);
			return "" + cellId;
		}
		return "-1";
	}

	@RequestMapping(value = "/check")
	public @ResponseBody String check(@RequestParam("id") int id, @RequestParam("player") String player) {
		GameBean game = dao.getGameById(id);
		if (game.getTurn().equals(player)) {
			return "" + game.getLastMove();
		}

		return "-1";
	}
}
