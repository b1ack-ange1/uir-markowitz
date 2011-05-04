package exceptions;

@SuppressWarnings("serial")
public class MatricesNotMatch extends Exception{

	public String toString(){
		return "Matrices' dimensions does not match!";
	}
}