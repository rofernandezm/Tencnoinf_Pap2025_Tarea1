package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logic.entity.User;

public class UserHandler {
	private Map<String, User> users;
	private Map<String, String> emailMapper;
	private static UserHandler instance = null;

	private UserHandler() {
		users = new HashMap<String, User>();
		emailMapper = new HashMap<String, String>();
	}

	public static UserHandler getIntance() {
		if (instance == null)
			instance = new UserHandler();
		return instance;
	}

	public void addUser(User user) {
		String nickname = user.getNickname();
		String email = user.getEmail();
		this.users.put(nickname, user);
		this.emailMapper.put(email, nickname);
	}

	public User gerUserByNickname(String nickname) {
		return ((User) users.get(nickname));
	}

	public Boolean existNickname(String nickname) {
		return users.containsKey(nickname);
	}

	public Boolean existEmail(String email) {
		return emailMapper.containsKey(email);
	}

	public String[] listUsers() {
		if (users.isEmpty())
			return null;
		else {
			Set<String> keySet = users.keySet();
			Object[] keys = keySet.toArray();
			String[] nicknames = new String[users.size()];
			for (int ind = 0; ind < keys.length; ind++) {
				nicknames[ind] = keys[ind].toString();
			}
			return nicknames;
		}

	}
}
