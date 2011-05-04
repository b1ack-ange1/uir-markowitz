package exceptions;

@SuppressWarnings("serial")
public class MatrixNotSquare extends Exception{
	public String toString(){
		return "Matrix is not square!";
	}
}
