

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

/**
 * MyFriendAlertsTester.java
 * 
 * Unit tests for MyFriendAlerts.java. The test cases include scenarios for
 * out-of-time-range stocks, one friend, multiple friends, and varying 
 * net BUY/SELL trades.
 * 
 * @author Jacob Knorr
 *
 */

public class MyFriendAlertsTester {
	
	/*
	 * Apart from the below tests, some other scenarios include testing for:
	 * 	- empty alertList when net number = 0 for all stocks
	 * 	- invalid input
	 * 	- high volume input
	 */
	
	@Test
	public void testStocksOverOneWeekAgo() {
		
		ArrayList<String> friends = new ArrayList<>();
		friends.add("Fred");
		
		ArrayList<String> trades = new ArrayList<>();
		trades.add("2016-10-06,SELL,GOOGL");
		trades.add("2016-10-05,SELL,GOOGL");
		
		//this will not be in the list because its activity was over one week ago
		trades.add("2016-09-02,SELL,AAPL");
		
		MyFriendAlerts fa = new MyFriendAlerts(friends, trades);
		ArrayList<String> alertList = fa.getFriendAlerts("");
		
		String result = "";
		for (String str : alertList) {
			result = result + str;
		}
		
		assertEquals("2,SELL,GOOGL", result);
	}

	@Test
	public void testAlertListWithOneFriend() {
		
		ArrayList<String> friends = new ArrayList<>();
		friends.add("Fred");
		
		ArrayList<String> trades = new ArrayList<>();
		trades.add("2016-10-05,BUY,GOOGL");
		trades.add("2016-10-06,BUY,GOOGL");
		trades.add("2016-10-04,BUY,GOOGL");
		trades.add("2016-10-05,SELL,GOOGL"); // GOOGL will have net number of 2 BUYs
		
		trades.add("2016-10-04,BUY,AAPL");
		trades.add("2016-10-06,SELL,AAPL"); // net will be 0, so AAPL wont be in list
		
		trades.add("2016-10-04,SELL,YHOO");
		trades.add("2016-10-05,BUY,YHOO"); 
		trades.add("2016-10-06,SELL,YHOO"); 
		trades.add("2016-10-06,SELL,YHOO");
		trades.add("2016-10-05,SELL,YHOO");
		trades.add("2016-10-05,SELL,YHOO"); // net number of 4 SELLs
		
		MyFriendAlerts fa = new MyFriendAlerts(friends, trades);
		
		// argument left blank because friends are simulated here for testing
		ArrayList<String> alertList = fa.getFriendAlerts("");
										
		assertEquals("4,SELL,YHOO", alertList.get(0));
		assertEquals("2,BUY,GOOGL", alertList.get(1));
	}
	
	@Test
	public void testAlertListWithMultipleFriends() {
		
		ArrayList<String> friends = new ArrayList<>();
		friends.add("Fred");
		
		// Now, when another friend is added, the net number will be doubled
		// for each stock
		friends.add("Jane");
		
		ArrayList<String> trades = new ArrayList<>();
		trades.add("2016-10-05,SELL,GOOGL");
		trades.add("2016-10-06,SELL,GOOGL");
		
		trades.add("2016-10-05,BUY,AAPL");
		trades.add("2016-10-06,BUY,AAPL");
		trades.add("2016-10-04,BUY,AAPL");
		
		MyFriendAlerts fa = new MyFriendAlerts(friends, trades);
		ArrayList<String> alertList = fa.getFriendAlerts("");
		
		assertEquals("6,BUY,AAPL", alertList.get(0));
		assertEquals("4,SELL,GOOGL", alertList.get(1));
	}

}




