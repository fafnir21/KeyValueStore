package design.thrift.client;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import design.thrift.server.BasicAPI;

public class Client {

	public static void main(String[] args) {
		try {
			TTransport transport;
			transport = new TSocket("localhost", 9090);
			transport.open();
			
			TProtocol protocol = new TBinaryProtocol(transport);
			BasicAPI.Client client = new BasicAPI.Client(protocol);
			
			// some test code
			client.put("aaa", "bbb");
			client.put("aaa", "ccc");
			printList(client.get("aaa"));
			
			transport.close();
			
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	private static void printList(List<String> list) {
		for (String s : list) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
}
