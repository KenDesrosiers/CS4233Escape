/************************************************************************
* This file was created for the CS4233: Object-Oriented Analysis & Design
* course at Worcester Polytechnic Institute.
* Kenneth Desrosiers
************************************************************************/

package escape;

import java.util.ArrayList;

/**
* Enter a description
* @version May 10, 2020
*/

public class TestObserver implements GameObserver{
	
	private ArrayList<String> queue;
	
	public TestObserver() {
		queue = new ArrayList<String>();
	}

	@Override
	public void notify(String message) {
		// TODO Auto-generated method stub
		queue.add(message);
	}

	@Override
	public void notify(String message, Throwable cause) {
		// TODO Auto-generated method stub
		queue.add(message + " : " + cause.getMessage());
	}
	
	public boolean sameMessage(String msg) {
		return (queue.get(queue.size() - 1).equals(msg)) ? true : false;
	}
}