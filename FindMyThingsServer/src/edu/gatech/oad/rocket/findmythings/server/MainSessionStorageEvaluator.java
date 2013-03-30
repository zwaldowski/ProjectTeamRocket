package edu.gatech.oad.rocket.findmythings.server;

import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.subject.Subject;

public class MainSessionStorageEvaluator implements SessionStorageEvaluator {

	@Override
	public boolean isSessionStorageEnabled(Subject arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}