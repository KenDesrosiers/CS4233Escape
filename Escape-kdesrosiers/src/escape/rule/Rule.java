/************************************************************************
 * This file was created for the CS4233: Object-Oriented Analysis & Design
 * course at Worcester Polytechnic Institute.
 * Kenneth Desrosiers
 ************************************************************************/

package escape.rule;

/**
 * A class for organizing game rules
 * @version May 8, 2020
 */

public class Rule {
	RuleID id;
	int intValue;

	/**
	 * An empty constructor
	 */
	public Rule() {
	}

	/**
	 * returns the id of the rule
	 * @return the id
	 */
	public RuleID getId() {
		return id;
	}

	/**
	 * sets the id of the rule
	 * @param id the id
	 */
	public void setId(RuleID id) {
		this.id = id;
	}

	/**
	 * returns the intValue of the rule if applicable
	 * @return the intVale
	 */
	public int getIntValue() {
		return intValue;
	}

	/**
	 * sets the intValue of the rule if applicable
	 * @param i the intValue
	 */
	public void setIntValue(int i) {
		this.intValue = i;
	}
}