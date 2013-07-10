package general.group;

public interface KeyExtractor<K,T> {

	public K getKey(T obj);
}
