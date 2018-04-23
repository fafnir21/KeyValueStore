package design.thrift.server;

import java.util.List;

/*
 * Store a copy of values with timestamp
 */
public class ValuesHistory {
	
	// Timestamp of a value list record. Converted to millisecond
	private final long timestamp;
	// List of values
	private final List<String> values;
	
	public ValuesHistory(long timestamp, List<String> values) {
		this.timestamp = timestamp;
		this.values = values;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public List<String> getValues() {
		return this.values;
	}
}
