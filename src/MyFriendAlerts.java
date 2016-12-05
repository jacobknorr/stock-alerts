
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * Stocks Your Friends are Trading
 * 
 * MyFriendAlerts uses its getFriendAlerts method to return an alert list
 * for the recent stock activity of a user's friends. The getFriendsForUser
 * and getTradeTransactionsForUser methods are provided and are used by getFriendAlerts.
 * 
 * Time complexity: O(n^2)
 * Space complexity: O(n)
 * 
 * @author Jacob Knorr
 *
 */

public class MyFriendAlerts {
	
	private List<String> friends;
	private List<String> transactions;
	
	/*
	 *  Constructor takes two ArrayList arguments, primarily for unit testing so
	 *  getTradeTransactionsForUser and getFriendsForUser can return non-empty lists
	 */
	public MyFriendAlerts(ArrayList<String> friends, ArrayList<String> transactions) {
		this.friends = friends;
		this.transactions = transactions;
	}
	
	public List<String> getTradeTransactionsForUser(String user) {
		return transactions;
	}
	public List<String> getFriendsForUser(String user) {
		return friends;
	}
	
	
	/*
	 * getFriendAlerts -- returns a list of recent stock activity of a user's friends.
	 * List is sorted in descending order by net number of stock trades.
	 */
	public ArrayList<String> getFriendAlerts(String user) {
		
		ArrayList<String> alerts = new ArrayList<>();
		Map<String,Integer> map = new HashMap<>();  // Map<ticker><net_number>
		
		for (String friend : getFriendsForUser(user)) {
			for (String trade : getTradeTransactionsForUser(friend)) {
				Transaction t = new Transaction(trade);
				if (LocalDate.now().minusWeeks(1).isBefore(t.date)) 
					map.put(t.ticker, map.getOrDefault(t.ticker,0) + t.net_change);
			}
		}
		
		String str = "";
		for (Map.Entry<String, Integer> entry : map.entrySet()) {

			if (entry.getValue() < 0) 
				alerts.add(entry.getValue()*-1 + ",SELL," + entry.getKey());
			else if (entry.getValue() > 0)
				alerts.add(entry.getValue() + ",BUY," + entry.getKey());

		}
		Collections.sort(alerts, Collections.reverseOrder());

		return alerts;
	}
}

/* 
 * Each Transaction instance parses a trade of the form "<date><BUY|SELL><ticker>"
 * into separate components
*/
class Transaction {
	
	LocalDate date;
	String status, ticker;
	int net_change;
	
	public Transaction(String trade) {
		String[] data = trade.split(",");
		
		date = LocalDate.parse(data[0]);
		status = data[1];
		ticker = data[2];
		
		if (status.equals("BUY"))
			net_change = 1;
		else net_change = -1; 
	}
}









