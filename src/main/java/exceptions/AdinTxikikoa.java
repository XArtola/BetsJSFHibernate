package exceptions;

public class AdinTxikikoa extends Exception {

	private static final long serialVersionUID = 1L;

	public AdinTxikikoa() {
		super();
	}

	/**
	 * This exception is triggered if the question already exists
	 * 
	 * @param s String of the exception
	 */
	public AdinTxikikoa(String s) {
		super(s);
	}

}
