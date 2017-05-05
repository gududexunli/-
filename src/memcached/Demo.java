package memcached;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * @author licheng at 2017年4月24日 下午2:37:12
 */
public class Demo {
	private String str;
	
	public static void main(String[] args) throws Exception {
		try {
			String host = "10.90.26.130";
			List<InetSocketAddress> address = new ArrayList<>();
			//端口列表，需要的可以继续增加端口
			address.add(new InetSocketAddress(host, 11219));
			address.add(new InetSocketAddress(host, 11220));
			
			MemcachedClient client = MemcachedUtil.getDefaultClient(address, new int[] { 1, 1 });
			List<String> keys = MemcachedUtil.search(client, "group_rule#93");
			for (String key : keys) {
				System.out.println(key);
				String value = client.get(key);
				//System.out.println(client.delete(key));
				System.out.println(value);
			}
		} finally {
			System.exit(0);
		}

	}

}
