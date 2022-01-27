package stack;

// A standard linked list node class used to implement the LinkedStack version of a stack.
public class LLNode<T> {

	public T info;
	public LLNode<T> link;

	/**
	 * @throws NullPointerException if {@code info} is {@code null}
	 */
	public LLNode(T info) {
		if (info == null) {
			throw new NullPointerException();
		}
		this.info = info;
	}
}
