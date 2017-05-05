package memcached;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.buffer.SimpleBufferAllocator;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;

/**
 * @author licheng at 2017年4月24日 下午4:30:18
 */
@SuppressWarnings("deprecation")
public class MemcachedUtil {

	public static List<String> search(MemcachedClient client, List<InetSocketAddress> address,String str) throws Exception {
		if (address == null || address.size() == 0) {
			return null;
		}
		
		List<String> result = new ArrayList<>();
		for (InetSocketAddress inetSocketAddress : address) {
			KeyIterator keyIterator = client.getKeyIterator(inetSocketAddress);
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				if(key.toUpperCase().indexOf(str.toUpperCase())!=-1){
					result.add(key);
				}
			}
		}
		return result;
	}

	public static List<String> search(MemcachedClient client,String str) throws Exception {
		return search(client, new ArrayList<>(client.getAvailableServers()),str);
	}
	
	public static MemcachedClient getDefaultClient(List<InetSocketAddress> address,int[] weight) throws Exception{
		XMemcachedClientBuilder clientBuild = new XMemcachedClientBuilder(address, new int[] { 1, 1 });
		clientBuild.setConnectionPoolSize(1);
		clientBuild.setCommandFactory(new BinaryCommandFactory());
		clientBuild.setTranscoder(new SerializingTranscoder());
		clientBuild.setBufferAllocator(new SimpleBufferAllocator());
		clientBuild.setSessionLocator(new KetamaMemcachedSessionLocator());
		clientBuild.setCommandFactory(new TextCommandFactory());
		MemcachedClient client =  clientBuild.build();
		return client;
	}

}
