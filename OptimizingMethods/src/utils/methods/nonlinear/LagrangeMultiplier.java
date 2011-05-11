package utils.methods.nonlinear;

import utils.exceptions.OptimizingException;
import utils.methods.Method;
import utils.portfolio.Portfolio;

public class LagrangeMultiplier extends Method {

	public LagrangeMultiplier(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}

	}

	public LagrangeMultiplier(Portfolio challenge, double expectedProfit,
			double epsilon) {
		super(challenge, expectedProfit, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}

	}

	@Override
	public void evaluate() throws OptimizingException {
		// TODO Auto-generated method stub

	}

}
