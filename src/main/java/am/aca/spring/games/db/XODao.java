package am.aca.spring.games.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import am.aca.spring.games.beans.GameBean;

public class XODao {
	private JdbcTemplate template;
	private static final String CREATE_GAME_SQL = "INSERT INTO GAME(PLAYER1) VALUES(?)";
	private static final String CREATE_JOIN_SQL = "UPDATE GAME SET PLAYER2=?, TURN=PLAYER1, STATE=? WHERE ID=?";
	private static final String MAKE_MOVE_SQL = "UPDATE GAME SET STATE=?, LAST_MOVE=?, TURN=? WHERE ID=? AND TURN=?";
	private static final String GET_AVAILABLE_GAME = "SELECT * FROM GAME WHERE PLAYER2 IS NULL LIMIT 1";
	private static final String GET_GAME_BY_ID = "SELECT * FROM GAME WHERE id=?";

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public Integer createGame(final String player1) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(CREATE_GAME_SQL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, player1);
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public Integer joinGame(final Integer gameId, final String player2, final String state) {
		return template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(CREATE_JOIN_SQL);
				ps.setString(1, player2);
				ps.setString(2, state);
				ps.setInt(3, gameId);
				return ps;
			}
		});
	}

	public GameBean getAvailableGames() {
		try {
			return template.queryForObject(GET_AVAILABLE_GAME, new BeanPropertyRowMapper<GameBean>(GameBean.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public GameBean getGameById(final Integer id) {
		try {
			return template.queryForObject(GET_GAME_BY_ID, new Object[] { id },
					new BeanPropertyRowMapper<GameBean>(GameBean.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int move(final Integer id, final String player, final String nextPlayer, final String state,
			final Integer lastMove) {
		return template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(MAKE_MOVE_SQL);
				ps.setString(1, state);
				ps.setInt(2, lastMove);
				ps.setString(3, nextPlayer);
				ps.setInt(4, id);
				ps.setString(5, player);
				return ps;
			}
		});
	}
}
