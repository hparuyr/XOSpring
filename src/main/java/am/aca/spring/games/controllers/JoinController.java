package am.aca.spring.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import am.aca.spring.games.beans.GameBean;
import am.aca.spring.games.db.XODao;

@Controller
public class JoinController {

	@Autowired
	private XODao dao;

	@RequestMapping(value = { "/", "/index" })
	public String goToIndex() {
		return "index";
	}

	@RequestMapping(value = "/wait", method = RequestMethod.GET)
	public @ResponseBody String wait(@RequestParam(value = "gameId", required = true) Integer gameId) {
		GameBean game = dao.getGameById(gameId);
		if (game == null)
			return "index";

		if (game.getPlayer2() == null) {
			return "";
		}

		return "game";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView join(@RequestParam("player") String player, ModelMap model) {
		GameBean game = dao.getAvailableGames();

		// Create game if there is no game created
		if (game == null) {
			Integer gameId = dao.createGame(player);
			game = new GameBean();
			game.setId(gameId);
		}
		// There is already created game join
		else {
			dao.joinGame(game.getId(), player, new Gson().toJson(GameBean.DEFAULT_STATE, Character[].class));
		}

		model.addAttribute("id", game.getId());
		model.addAttribute("player", player);
		return new ModelAndView("wait", model);
	}

}
