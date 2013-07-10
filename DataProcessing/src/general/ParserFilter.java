package general;

/**
 * Filter for given class <T>
 * @param <T>
 */
public abstract class ParserFilter<T> {
	
	/**
	 * Whether we should keep the given object
	 * @param obj The object we consider keeping
	 * @return whether we should keep it
	 */
	public abstract boolean shouldKeep(T obj);
}
