package design.thrift.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class KVHistoryRecord {
	
	private final String key;
	// a list of values history records (including timestamp)
	private final List<ValuesHistory> valuesHistoryList;
	// list of values up to date
	private List<String> currentValues;
	
	public KVHistoryRecord(String key) {
		this.key = key;
		this.valuesHistoryList = new ArrayList<>();
		this.currentValues = new LinkedList<>();
	}
	
	// Update current list and create a copy to add into the history list
	// Time complexity: O(1)
	public void put(String value) {
		currentValues.add(value);
		valuesHistoryList.add(new ValuesHistory(new Date().getTime(), currentValues));
	}
	
	// Get the current list
	// Time complexity: O(1)
	public List<String> get() {
		return currentValues;
	}
	
	// Update current list and create a copy to add into the history list
	// Only remove the first occurrence of specific value
	// Time complexity: O(1)
	public void delete(String value) {
		currentValues.remove(value);
		valuesHistoryList.add(new ValuesHistory(new Date().getTime(), currentValues));
	}
	
	// Do binary search on the valuesHistoryList
	// Time complexity: O(log n), n is the size of history list
	// Space complexity: O(1)
	public List<String> getByTime(long time) {
		return binarySearch(time, 0, valuesHistoryList.size() - 1);
	}
	
	// Call getByTime(long time) to get two history list of values,
	// Then get the difference by removeAll() function
	// Time complexity: O(m * n), m indicates size of value list1, n -> list2
	// Space complexity: O(m + n)
	public List<String> diff(long time1, long time2) {
		List<String> vlist1 = getByTime(time1);
		List<String> vlist2 = getByTime(time2);
		vlist1.removeAll(vlist2);
		vlist2.removeAll(vlist1);
		List<String> res = new LinkedList<>(vlist1);
		res.addAll(vlist2);
		return res;
	}
	
	// Find the biggest value which is smaller than the input time
	// Time complexity: O(log n), n is the size of history list
	// Space complexity: O(1)
	private List<String> binarySearch(long time, int left, int right) {
		while (left < right - 1) {
			int mid = left + (right - left) / 2;
			long ts = valuesHistoryList.get(mid).getTimestamp();
			if (ts < time) {
				left = mid;
			} else if (ts > time) {
				right = mid;
			} else {
				return valuesHistoryList.get(mid).getValues();
			}
		}
		ValuesHistory vhLeft = valuesHistoryList.get(left);
		ValuesHistory vhRight = valuesHistoryList.get(right);
		return vhRight.getTimestamp() < time ? vhRight.getValues() : vhLeft.getValues();
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void printCurrentList() {
		for (String s : currentValues) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
}
