package design.thrift.server.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import design.thrift.server.ServiceHandler;

public class ServerTest {

	ServiceHandler serviceHandler;
	
	@Before
	public void setUp() throws Exception {
		serviceHandler = new ServiceHandler();
	}

	@Test
	public void testPutAndGet() throws TException {
		serviceHandler.put("k1", "v1");
		serviceHandler.put("k1", "v2");
		serviceHandler.put("k1", "v3");
		List<String> res = serviceHandler.get("k1");
		assertArrayEquals(new String[] {"v1", "v2", "v3"}, res.toArray(new String[res.size()]));
	}
	
	@Test
	public void testDelKey() throws TException {
		serviceHandler.put("k2", "v1");
		serviceHandler.put("k2", "v2");
		serviceHandler.delKey("k2");
		List<String> res = serviceHandler.get("k2");
		assertArrayEquals(new String[] {}, res.toArray(new String[res.size()]));
	}
	
	@Test
	public void testDelValue() throws TException {
		serviceHandler.put("k3", "v1");
		serviceHandler.put("k3", "v2");
		serviceHandler.delValue("k3", "v1");
		List<String> res = serviceHandler.get("k3");
		assertArrayEquals(new String[] {"v2"}, res.toArray(new String[res.size()]));
	}

}
