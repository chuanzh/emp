package cn.chuanz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.chuanzh.util.ConfigRead;
import com.github.chuanzh.util.FuncStatic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class RedisClient {
	private static ShardedJedisPool pool;
	
	static{ 
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(ConfigRead.readIntValue("redis_maxTotal"));
		config.setMaxIdle(ConfigRead.readIntValue("redis_maxIdel"));
		config.setTimeBetweenEvictionRunsMillis(ConfigRead.readIntValue("redis_timeBetweenEvictionRunsMillis"));
		config.setMinEvictableIdleTimeMillis(ConfigRead.readIntValue("redis_minEvictableIdleTimeMillis"));
		config.setTestOnBorrow(ConfigRead.readBooleanValue("redis_testOnBorrow"));
		config.setMaxWaitMillis(ConfigRead.readLongValue("redis_maxWaitMillis"));
		String ipAndPort = ConfigRead.readValue("redis_ipAndPort").trim();
		
		List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		String[] addressArr = ipAndPort.split(";");
		for (String str : addressArr) {
			list.add(new JedisShardInfo(str.split(":")[0], Integer.parseInt(str
					.split(":")[1])));
		}
		pool = new ShardedJedisPool(config, list);
	}
	
	private RedisClient (){
		
	}

	/**
	 * 关闭 Redis
	 */
	public static void destory() {
		pool.destroy();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static long del(byte [] key) {
		ShardedJedis shardedJedis = null;
		long ret = 0l;
		try {
			shardedJedis = pool.getResource();
			ret = shardedJedis.del(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static long del(String key) {
		ShardedJedis shardedJedis = null;
		long ret = 0l;
		try {
			shardedJedis = pool.getResource();
			ret = shardedJedis.del(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return ret;
	}
	/**
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static long hdel(String key,String... fields) {
		
		ShardedJedis shardedJedis = null;
		long ret = 0l;
		try {
			shardedJedis = pool.getResource();
			ret = shardedJedis.hdel(key, fields);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return ret;
	}
	
	/**
	 * redis的List集合 ，向key这个list添加元素
	 * 
	 * @param key
	 *            List别名
	 * @param string
	 *            元素
	 * @return
	 */
	public static long rpush(String key, String string) {
		ShardedJedis shardedJedis = null;
		long ret = 0l;
		try {
			shardedJedis = pool.getResource();
			ret = shardedJedis.rpush(key, string);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return ret;
	}

	/**
	 * 获取key这个List，从第几个元素到第几个元素 LRANGE key start
	 * stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。
	 * 也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 *            List别名
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束下标
	 * @return
	 */
	public static List<String> lrange(String key, long start, long end) {
		ShardedJedis shardedJedis = null;
		List<String> ret  = null;
		try {
			shardedJedis = pool.getResource();
			ret = shardedJedis.lrange(key, start, end);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param key key
	 * @param match 匹配字符
	 * @param maxCount 最大个数
	 * @return
	 */
	public static List<Map.Entry<String, String>> hscan(String key,  String match, int maxCount) {
		ShardedJedis shardedJedis = null;  
		List<Map.Entry<String, String>> list = null;
		try {
			shardedJedis = pool.getResource(); 
			int cursor = 0; 
		    ScanParams scanParams = new ScanParams(); 
		    scanParams.count(maxCount); 
		    if(!FuncStatic.checkIsEmpty(match)){
		    	scanParams.match(match);
		    }
		    
		    Jedis jedis = shardedJedis.getShard(key);
		    ScanResult<Map.Entry<String, String>> scanResult;
		    list = new ArrayList<Map.Entry<String, String>>();
		    do {
		        scanResult = jedis.hscan(key, String.valueOf(cursor), scanParams);
		        list.addAll(scanResult.getResult());
		        cursor = Integer.parseInt(scanResult.getStringCursor());
		    } while (cursor > 0 && maxCount > cursor); 
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return list;
	}
	/**
	 * 返回有序集 key 中，指定区间内的成员。
	 * 其中成员的位置按 score 值递增(从小到大)来排序。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrange(String key, long start, long end) {
		ShardedJedis shardedJedis = null; 
		Set<String> ret;
		try {
			shardedJedis = pool.getResource();
			ret = shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return ret;
	}

	/**
	 * 将哈希表key中的域field的值设为value。
	 * 
	 * @param key
	 *            哈希表别名
	 * @param field键
	 * @param value
	 *            值
	 */
	public static void hset(String key, String field, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.hset(key, field, value);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 向key赋值
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.set(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}
	
	/**
	 * 向key赋值
	 * @param key
	 * @param value
	 */
	public static void set(byte [] key, byte [] value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.set(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}
	
	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		ShardedJedis shardedJedis = null;
		byte[] value = null;
		try {
			shardedJedis = pool.getResource();
			value = shardedJedis.get(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		ShardedJedis shardedJedis = null;
		String value = null;
		try {
			shardedJedis = pool.getResource();
			value = shardedJedis.get(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 * 
	 * @param key
	 * @param map
	 */
	public static void hmset(String key, Map<String, String> map) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.hmset(key, map);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 */
	public static void setex(String key, int seconds, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.setex(key, seconds, value);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}
	
	/**
	 * 为给定key设置生命周期
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 */
	public static void expire(byte[] key, int seconds) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 为给定key设置生命周期
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 */
	public static void expire(String key, int seconds) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		ShardedJedis shardedJedis = null;
		boolean bool = false;
		try {
			shardedJedis = pool.getResource();
			bool = shardedJedis.exists(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return bool;
	}

	/**
	 * 返回key值的类型 none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
	 * 
	 * @param key
	 * @return
	 */
	public static String type(String key) {
		ShardedJedis shardedJedis = null;
		String type =null;
		try {
			shardedJedis = pool.getResource();
			type = shardedJedis.type(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return type;
	}
	
	public static String type(byte[] key) {
		ShardedJedis shardedJedis = null;
		String type =null;
		try {
			shardedJedis = pool.getResource();
			type = shardedJedis.type(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return type;
	}

	/**
	 * 从哈希表key中获取field的value
	 * 
	 * @param key
	 * @param field
	 */
	public static String hget(String key, String field) {
		ShardedJedis shardedJedis = null;
		String value =null;
		try {
			shardedJedis = pool.getResource();
			value = shardedJedis.hget(key, field);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return value;
	}
	/**
	 * 返回哈希表 key 中所有域的值。
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key) {
		ShardedJedis shardedJedis = null; 
		List<String> value = null;
		try {
			shardedJedis = pool.getResource();
			value  = shardedJedis.hvals(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * 返回哈希表key中，所有的域和值
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> hgetAll(String key) {
		ShardedJedis shardedJedis = null;
		Map<String, String> map =null;
		try {
			shardedJedis = pool.getResource();
			map = shardedJedis.hgetAll(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return map;
	}

	/**
	 * 返回集合key中，所有的成员
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		ShardedJedis shardedJedis = null;
		Set<String> set =null;
		try {
			shardedJedis = pool.getResource();
			set = shardedJedis.smembers(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return set;
	}

	/**
	 * 移除集合中的member元素
	 * 
	 * @param key
	 *            List别名
	 * @param field
	 *            键
	 */
	public static void delSetObj(String key, String field) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.srem(key, field);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * 判断member元素是否是集合key的成员。是（true），否则（false）
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean isNotField(String key, String field) {
		ShardedJedis shardedJedis = null;
		boolean bool = false;
		try {
			shardedJedis = pool.getResource();
			bool = shardedJedis.sismember(key, field);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return bool;
	}
	/**
	 * 返回列表 key 的长度。
	 * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
	 * 如果 key 不是列表类型，返回一个错误。
	 * @param key 
	 * @return
	 */
	public static long llen(String key) {
		ShardedJedis shardedJedis = null;
		long len  = 0;
		try {
			shardedJedis = pool.getResource();
			len = shardedJedis.llen(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return len;
	}
	/**
	 * 返回哈希表 key 中域的数量。
	 * @param key 
	 * @return 当 key 不存在时，返回 0 。
	 */
	public static long hlen(String key) {
		ShardedJedis shardedJedis = null;
		long len  = 0;
		try {
			shardedJedis = pool.getResource();
			len = shardedJedis.hlen(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return len;
	}
	/**
	 * 返回集合 key 的基数(集合中元素的数量)。
	 * @param key 
	 * @return 当 key 不存在时，返回 0 。
	 */
	public static long scard (String key) {
		ShardedJedis shardedJedis = null;
		long len  = 0;
		try {
			shardedJedis = pool.getResource();
			len = shardedJedis.scard(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return len;
	}
	/**
	 * 返回有序集 key 的基数。
	 * @param key 
	 * @return key 不存在时，返回 0 
	 */
	public static long zcard (String key) {
		ShardedJedis shardedJedis = null;
		long len  = 0;
		try {
			shardedJedis = pool.getResource();
			len = shardedJedis.zcard(key);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
		return len;
	}

	/**
	 * 如果key已经存在并且是一个字符串，将value追加到key原来的值之后
	 * 
	 * @param key
	 * @param value
	 */
	public static void append(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = pool.getResource();
			shardedJedis.append(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(shardedJedis);
			shardedJedis = null;
			throw new JedisException(e);
		}finally{
			if (shardedJedis != null) { 
				pool.returnResource(shardedJedis);
			}
		}
	}
}
