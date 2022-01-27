package stack;

/**
 * A {@link LinkedStack} is a generic stack that is implemented using a Linked
 * List structure to allow for unbounded size.
 */
public class LinkedStack<T> {

	// TODO: define class variables here
	public LLNode<T> top;
	int size;

	/**
	 * Remove and return the top element on this stack. If stack is empty, return
	 * null (instead of throw exception)
	 */
	public T pop() {
		// TODO
		if (!isEmpty()) {
			T element = top.info;
			top = top.link;
			size -= 1;
			return element;
		}
		return null;
	}

	/**
	 * Return the top element of this stack (do not remove the top element). If
	 * stack is empty, return null (instead of throw exception)
	 */
	public T top() {
		// TODO
		if (!isEmpty()) {
			return top.info;
		}
		return null;
	}

	/**
	 * Return true if the stack is empty and false otherwise.
	 */
	public boolean isEmpty() {
		// TODO
		if (top == null) {
			return true;
		}
		return false;
	}

	/**
	 * Return the number of elements in this stack.
	 */
	public int size() {
		// TODO
		return size;
	}

	/**
	 * Pushes a new element to the top of this stack.
	 */
	public void push(T elem) {
		// TODO
		LLNode<T> newNode = new LLNode<T>(elem);
		newNode.link = top;
		top = newNode;
		size += 1;
	}

}
