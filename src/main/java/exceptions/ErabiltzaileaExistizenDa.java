package exceptions;

public class ErabiltzaileaExistizenDa extends Exception {
	private static final long serialVersionUID = 1L;

	 public ErabiltzaileaExistizenDa()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public ErabiltzaileaExistizenDa(String s)
	  {
	    super(s);
	  }
	
}
