package org.tiefaces.showcase.websheet;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class RuleBean implements Serializable{

	private static final long serialVersionUID = 1L;

	
	public boolean checkRule1(double value) {
		if (value > 0) {
			return true;
		}
		return false;
	}
	
	public boolean checkRule2(double value) {
		if (value < 500000) {
			return true;
		}
		return false;
	}
}
