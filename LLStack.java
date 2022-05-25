package stack;

/**
* This data structure is a slight modification to a standard stack that is implemented with
* a linked list to allow for infinite size.
 */
public class LLStack<T> {

	public LLNode<T> top;
	int size;

	/**
	 * Removes the top element of the stack and returns it, returning null
	 * if there are no elements in the stack.
	 */
	public T pop() {
		if (!isEmpty()) {
			T element = top.info;
			top = top.link;
			size -= 1;
			return element;
		}
		return null;
	}

	/**
	 * Returns the top element of the stack without removing it, returning null
	 * if there are no elements in the stack.
	 */
	public T top() {
		if (!isEmpty()) {
			return top.info;
		}
		return null;
	}

	/**
	 * Returns true if the stack has no elements, and false otherwise.
	 */
	public boolean isEmpty() {
		if (top == null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of elements in the stack.
	 */
	public int size() {
		return size;
	}

	/**
	* Adds a new element to the top of the stack and updates its size.
	 */
	public void push(T elem) {
		LLNode<T> newNode = new LLNode<T>(elem);
		newNode.link = top;
		top = newNode;
		size += 1;
	}

}
