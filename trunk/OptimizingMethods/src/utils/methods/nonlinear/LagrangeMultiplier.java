package utils.methods.nonlinear;

import utils.Methods;
import utils.exceptions.OptimizingException;
import utils.methods.Method;
import utils.portfolio.Portfolio;
import Jama.LUDecomposition;
import Jama.Matrix;

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
		// вырожденный риск всегда равен 1
		startTime();
		double minimumRisk = Double.MAX_VALUE;
		int status = -1;
		double temp;
		double[] tempArr = new double[xLength];
		for (int i = 0; i < Math.pow(2, xLength + 1); i++) {
			operations++;
			try {
				temp = step(i);
				if (temp >= 0)
					if (minimumRisk >= temp) {
						minimumRisk = temp;
						status = i;
						for (int j = 0; j < xLength; j++)
							tempArr[j] = x[j];
					}

			} catch (OptimizingException e) {

			}
		}

		if (status >= 0) {
			x = tempArr;
		} else {
			throw new OptimizingException("alarm",
					Methods.METOD_LAGRANGEMULTIPLIER);
		}
		endTime();
	}

	private String makePostfix(int count) {
		String out = "";
		for (int i = 0; i < count; i++)
			out += "0";
		return out;
	}

	private double step(int number) throws OptimizingException {
		// 0 - переменная равна 0
		// 1 - скобка равна 0
		String code = Integer.toBinaryString(number);
		if (code.length() < xLength + 1) {
			operations++;
			code = makePostfix(xLength + 1 - code.length()) + code;
		}
		double[][] matrixArray = new double[2 * xLength + 2][2 * xLength + 2];
		double[][] matrixFree = new double[2 * xLength + 2][1];
		for (int i = 0; i < code.length(); i++) {
			operations++;
			// условия
			if (code.charAt(i) == '0') {
				// переменная = 0
				matrixArray[i + xLength][i + xLength] = 1.0;
				matrixFree[i + xLength][0] = 0.0;
			} else {
				// скобка = 0
				if (i < xLength) {
					// x

					matrixArray[i + xLength][i] = 1.0;
					matrixFree[i + xLength][0] = 0.0;
				} else if (i == xLength) {
					// l1
					for (int j = 0; j < xLength; j++) {
						matrixArray[i + xLength][j] = profit[j];
					}
					matrixFree[i + xLength][0] = expectedProfit;
				}
			}

		}

		for (int i = 0; i < xLength; i++) {
			for (int j = 0; j < xLength; j++) {
				operations++;
				matrixArray[i][j] = covariances[i][j];
			}
			matrixArray[i][i + xLength] = -1.0;
			matrixArray[i][2 * xLength] = -profit[i];
			matrixArray[i][2 * xLength + 1] = 1.0;
			matrixFree[i][0] = 0.0;
		}

		for (int i = 0; i < xLength; i++) {
			operations++;
			matrixArray[2 * xLength + 1][i] = 1.0;
		}
		matrixFree[2 * xLength + 1][0] = 1.0;

		Matrix matrix = new Matrix(matrixArray);
		LUDecomposition dec = new LUDecomposition(matrix);
		Matrix matrixFreeKoeff = new Matrix(matrixFree);
		Matrix solution = null;
		try {
			operations += Math.pow(xLength, 2);
			solution = dec.solve(matrixFreeKoeff);
		} catch (Exception e) {
			throw new OptimizingException("no variant",
					Methods.METOD_LAGRANGEMULTIPLIER);
		}
		double extra = 0.0;
		for (int i = 0; i < xLength; i++) {
			operations++;
			x[i] = solution.get(i, 0);
			extra += x[i];
		}
		if (!checkFormulas())
			throw new OptimizingException("can't find linear solution",
					Methods.METOD_LAGRANGEMULTIPLIER);
		return getRisk();

	}

	private boolean checkFormulas() {
		for (int i = 0; i < xLength; i++)
			if (x[i] < 0)
				return false;

		if (!isSumCorrect())
			return false;

		if (sumProfit() < expectedProfit)
			return false;
		return true;
	}

}