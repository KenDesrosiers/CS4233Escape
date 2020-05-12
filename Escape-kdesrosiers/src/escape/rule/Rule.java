/************************************************************************
 * This file was created for the CS4233: Object-Oriented Analysis & Design
 * course at Worcester Polytechnic Institute.
 * Kenneth Desrosiers
 ************************************************************************/

package escape.rule;

/**
 * Enter a description
 * @version May 8, 2020
 */

public class Rule {
	RuleID id;
	int intValue;

	public Rule() {
	}

	public RuleID getId() {
		return id;
	}

	public void setId(RuleID id) {
		this.id = id;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int i) {
		this.intValue = i;
	}
}