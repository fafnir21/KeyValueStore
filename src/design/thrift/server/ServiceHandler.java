package design.thrift.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

public class ServiceHandler implements BasicAPI.Iface {

	private final Map<String, KVHistoryRecord> keyValueMap;
	
	public ServiceHandler() {
		this.keyValueMap = new HashMap<>();
	}
	
	// case1: new key, create new history record and put it into the map with the key
	// case2: key already exists, just get its history record object and call put() function
	@Override
	public void put(String key, String value) throws TException {
		if (!keyValueMap.containsKey(key)) {
			keyValueMap.put(key, new KVHistoryRecord(key));
		}
		keyValueMap.get(key).put(value);
	}
	
	// case1: key is not in the map, return an empty list
	// case2: otherwise, return the list of values
	@Override
	public List<String> get(String key) throws TException {
		if (!keyValueMap.containsKey(key)) {
			return new LinkedList<>();
		}
		return keyValueMap.get(key).get();
	}
	
	// case1: key is not in the map, return an empty list
	// case2: otherwise, return the list of values by calling getByTime()
	@Override
	public List<String> getByTime(String key, long time) throws TException {
		if (!keyValueMap.containsKey(key)) {
			return new LinkedList<>();
		}
		return keyValueMap.get(key).getByTime(time);
	}
	
	// case1: key is not in the map, give a warning
	// case2: otherwise, delete the specific map entry
	@Override
	public void delKey(String key) throws TException {
		if (!keyValueMap.containsKey(key)) {
			System.out.println("key: " + key + " does not exist");
			return;
			// or throw an exception here
		}
		keyValueMap.remove(key);
	}
	
	// case1: key is not in the map, give a warning
	// case2: otherwise, delete the specific map entry
	@Override
	public void delValue(String key, String value) throws TException {
		if (!keyValueMap.containsKey(key)) {
			System.out.println("key: " + key + " does not exist");
			return;
			// or throw an exception here
		}
		keyValueMap.get(key).delete(value);
	}
	
	// case1: key is not in the map, return an empty list
	// case2: otherwise, return the list of difference
	@Override
	public List<String> diff(String key, long time1, long time2) throws TException {
		if (!keyValueMap.containsKey(key)) {
			return new LinkedList<>();
		}
		return keyValueMap.get(key).diff(time1, time2);
	}
	
}
