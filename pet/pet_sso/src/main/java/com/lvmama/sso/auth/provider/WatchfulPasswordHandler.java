package com.lvmama.sso.auth.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.sso.auth.PasswordHandler;

/**
 * A PasswordHandler base class that implements logic to block IP addresses that
 * engage in too many unsuccessful login attempts. The goal is to limit the
 * damage that a dictionary-based password attack can achieve. We implement this
 * with a token-based strategy; failures are regularly forgotten, and only build
 * up when they occur faster than expiry.
 */
public abstract class WatchfulPasswordHandler implements PasswordHandler {
	/**
	 * sleep time
	 */
	private static final int SEC = 1000;

	// *********************************************************************
	// Constants

	/**
	 * The number of failed login attempts to allow before locking out the
	 * source IP address. (Note that failed login attempts "expire" regularly.)
	 */
	// private static final int FAILURE_THRESHOLD = 100;

	/**
	 * The interval to wait before expiring recorded failure attempts.
	 */
	private static final int FAILURE_TIMEOUT = 60;

	// *********************************************************************
	// Private state

	/** Map of offenders to the number of their offenses. */
	@SuppressWarnings("rawtypes")
	private static Map offenders = new HashMap();

	/** Thread to manage offenders. */
	private static Thread offenderThread = new Thread() {
		public void run() {
			try {
				while (true) {
					Thread.sleep(FAILURE_TIMEOUT * SEC);
					expireFailures();
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	};

	static {
		offenderThread.setDaemon(true);
		offenderThread.start();
	}

	// *********************************************************************
	// Gating logic

	/**
	 * Returns true if the given request comes from an IP address whose
	 * allotment of failed login attemps is within reasonable bounds; false
	 * otherwise. Note: We don't actually validate the user and password; this
	 * functionality must be implemented by subclasses.
	 */
	// public synchronized boolean authenticate(javax.servlet.ServletRequest
	// request,
	// String netid,
	// String password) {
	// return (getFailures(request.getRemoteAddr()) < FAILURE_THRESHOLD);
	// }

	/**
	 * Registers a login failure initiated by the given address.
	 * @param r request
	 */
	@SuppressWarnings("unchecked")
	protected synchronized void registerFailure(final javax.servlet.ServletRequest r) {
		String address = r.getRemoteAddr();
		offenders.put(address, getFailures(address) + 1);
	}

	/**
	 * Returns the number of "active" failures for the given address.
	 * @param address address
	 * @return  the number of "active" failures
	 */
	private static synchronized int getFailures(final String address) {
		Object o = offenders.get(address);
		if (o == null) {
			return 0;
		} else {
			return ((Integer) o).intValue();
		}
	}

	/**
	 * Removes one failure record from each offender; if any offender's
	 * resulting total is zero, remove it from the list.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static synchronized void expireFailures() {
		// scoop up addresses from Map so as to avoid modifying the Map in-place
		Set keys = offenders.keySet();
		Iterator ki = keys.iterator();
		List l = new ArrayList();
		while (ki.hasNext()) {
			l.add(ki.next());
		}
		// now, decrement and prune as appropriate
		for (int i = 0; i < l.size(); i++) {
			String address = (String) l.get(i);
			int failures = getFailures(address) - 1;
			if (failures > 0) {
				offenders.put(address, failures);
			} else {
				offenders.remove(address);
			}
		}
	}

}
