package utils.exceptions;

public class OptimizingException extends Exception {
	private String message;
	private String type;

	public OptimizingException(String msg, String type) {
		super();
		this.message = msg;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}
}
