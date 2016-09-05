package org.mj.study.zookeeper.pdf;

public interface DataMonitorListener {

	/**
	 * The existence status of the node has changed .
	 * @param data
	 */
	void exists(byte[] data);
	
	/**
	 * The Zookeeper session is no longer valid.
	 * @param rc
	 */
	void closing(int rc);
}
