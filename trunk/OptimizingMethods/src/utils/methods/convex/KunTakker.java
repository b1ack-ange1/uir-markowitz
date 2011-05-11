package utils.methods.convex;

import utils.Methods;
import utils.exceptions.OptimizingException;
import utils.methods.Method;
import utils.portfolio.Portfolio;
import Jama.LUDecomposition;
import Jama.Matrix;

public class KunTakker extends Method {

	public KunTakker(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 0.0;
		}
	}

	public KunTakker(Portfolio challenge, double expectedProfit, double epsilon) {
		super(challenge, expectedProfit, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 0.0;
		}

	}

	@Override
	public void evaluate() throws OptimizingException {
		// вырожденный риск всегда равен 1
		int number = tryFindSimpleSolution();
		x = new double[xLength];
		x[number] = 1.0;
		double minimumRisk = getRisk();
		if (profit[number] < expectedProfit)
			throw new OptimizingException("unreacheble expected profit",
					Methods.METOD_KUNTAKKER);

		int status = -1;
		double temp;
		double[] tempArr = new double[xLength];
		for (int i = 0; i < Math.pow(2, xLength + 1); i++) {
			try {
				temp = step(i);
				if (temp >= 0)
					if (minimumRisk >= temp) {
						minimumRisk = temp;
						status = i;
						for (int j = 0; j < xLength; j++)
							tempArr[j] = x[j];
					}

				for (int j = 0; j < x.length; j++) {
					System.out.println("X" + j + " = " + x[j]);
				}

				System.out.println("Final Risk = " + getRisk());

			} catch (OptimizingException e) {

			}
		}

		if (status >= 0) {
			x = tempArr;
		} else {
			x = new double[xLength];
			x[number] = 1.0;
		}

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
			code = makePostfix(xLength + 1 - code.length()) + code;
		}
		double[][] matrixArray = new double[xLength + 1][xLength + 1];
		double[][] matrixFree = new double[xLength + 1][1];
		for (int i = 0; i < code.length(); i++) {
			// условия
			if (code.charAt(i) == '0') {
				// переменная = 0
				matrixArray[i][i] = 1.0;
				matrixFree[i][0] = 0.0;
			} else {
				// скобка = 0
				if (i < xLength - 1) {
					// x
					for (int j = 0; j < xLength - 1; j++) {
						if (i != j) {
							matrixArray[i][j] = 2 * (covariances[i][j] - covariances[i][xLength - 1]);
						} else {
							matrixArray[i][i] = 2 - 4 * covariances[i][xLength - 1];
						}
					}

					matrixArray[i][xLength - 1] = (profit[xLength - 1] - profit[i]);
					matrixArray[i][xLength] = 1.0;

					matrixFree[i][0] =  - 2 * covariances[i][xLength - 1];
				} else if (i == xLength - 1) {
					// l1
					for (int j = 0; j < xLength - 1; j++) {
						matrixArray[i][j] = (profit[xLength - 1] - profit[j]);
					}
					matrixFree[i][0] = profit[xLength-1] - expectedProfit;
				} else {
					// l2
					for (int j = 0; j < xLength - 1; j++) {
						matrixArray[i][j] = 1.0;
					}
					matrixFree[i][0] = 1.0;
				}
			}

		}
		Matrix matrix = new Matrix(matrixArray);
		LUDecomposition dec = new LUDecomposition(matrix);
		Matrix matrixFreeKoeff = new Matrix(matrixFree);
		Matrix solution = null;
		try {
			solution = dec.solve(matrixFreeKoeff);
		} catch (Exception e) {
			throw new OptimizingException("no variant", Methods.METOD_KUNTAKKER);
		}
		double extra = 0.0;
		for (int i = 0; i < xLength - 1; i++) {
			x[i] = solution.get(i, 0);
			extra += x[i];
		}
		x[xLength - 1] = 1 - extra;
		double l1 = solution.get(xLength - 1, 0);
		double l2 = solution.get(xLength, 0);
		if (!checkFormulas(l1, l2))
			throw new OptimizingException("can't find linear solution",
					Methods.METOD_KUNTAKKER);
		return getRisk();

	}

	private int tryFindSimpleSolutionOld() {
		double minimumRisk = Double.MAX_VALUE;
		double currentRisk = 0;
		int out = -1;
		for (int i = 0; i < xLength; i++) {
			x = new double[xLength];
			x[i] = 1.0;
			currentRisk = getRisk();
			if (currentRisk < minimumRisk) {
				minimumRisk = currentRisk;
				out = i;
			}
		}
		return out;
	}

	private int tryFindSimpleSolution() {
		double temp = -1.0;
		int out = -1;
		for (int i = 0; i < xLength; i++) {
			if (profit[i] > temp) {
				temp = profit[i];
				out = i;
			}
		}
		return out;
	}

	private boolean checkFormulas(double l1, double l2) {
		for (int i = 0; i < xLength; i++)
			if (x[i] < 0)
				return false;
		if ((l1 < 0) || (l2 < 0))
			return false;

	/*	for (int i = 0; i < xLength - 1; i++) {
			// Дополняющая нежесткость
			double temp = difMainOnX(i) + l1
					* (profit[xLength - 1] - profit[i]) + l2;
			if (temp < 0)
				return false;
		}
*/

		if (sumProfit() < this.expectedProfit)
			return false;

		return true;
	}

	private double difMainOnX(int index) {
		double out = 0;
		out += 2 * x[index];
		out += 2 * sumCovarIndex(index);
		out -= 2 * x[xLength - 1];
		out -= 2 * sumCovarIndex(xLength - 1);
		out += 2 * (x[xLength - 1] - x[index])
				* covariances[index][xLength - 1];
		return out;
	}

}
