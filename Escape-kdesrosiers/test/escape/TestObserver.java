/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape;

import java.util.ArrayList;

/**
* A mock class for testing observers
* @version May 10, 2020
*/

public class TestObserver implements GameObserver{
	
	private ArrayList<String> queue;
	
	public TestObserver() {
		queue = new ArrayList<String>();
	}

	/**
	 *@see escape.GameObserver#notify(String)
	 */
	@Override
	public void notify(String message) {
		queue.add(message);
	}

	/**
	 *@see escape.GameObserver#notify(String, Throwable)
	 */
	@Override
	public void notify(String message, Throwable cause) {
		queue.add(message + " : " + cause.getMessage());
	}
	
	/**
	 * sees if the last message in the message queue is the same as the input
	 * @param msg the message to test
	 * @return true if so, false if not
	 */
	public boolean sameMessage(String msg) {
		return (queue.get(queue.size() - 1).equals(msg)) ? true : false;
	}
}