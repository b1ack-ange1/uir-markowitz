package utils.methods.square;

import utils.exceptions.OptimizingException;
import utils.methods.Method;
import utils.portfolio.Portfolio;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class LevenbergMarquardt extends Method {
	private Matrix baseMatrix;
	private Matrix transitionMatrix;

	public LevenbergMarquardt(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}

	}

	public LevenbergMarquardt(Portfolio challenge, double expectedProfit,
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
		makeDiagonal();
	}

	private void makeDiagonal() {
		Matrix matrix = new Matrix(covariances);
		EigenvalueDecomposition decomp = new EigenvalueDecomposition(matrix);
		baseMatrix = decomp.getD();
		transitionMatrix = decomp.getV();

	}

}
