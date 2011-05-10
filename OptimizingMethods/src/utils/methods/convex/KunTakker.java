package utils.methods.convex;

import utils.methods.Method;
import utils.portfolio.Portfolio;

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
	public void evaluate() {
		int number = tryFindSimpleSolution();
		x = new double[xLength];
		x[number] = 1.0;
		double minimumRisk = getRisk();

		int status = -1;
		double temp;
		for (int i = 0; i < Math.pow(2, xLength + 1); i++) {
			temp = step(i);
			if (temp >= 0)
				if (minimumRisk >= temp) {
					minimumRisk = temp;
					status = i;
				}
		}

	}

	private double step(int number) {
		// 0 - переменная равна 0
		// 1 - скобка равна 0
		String code = Integer.toBinaryString(number);

	}

	private int tryFindSimpleSolution() {
		double minimumRisk = 1D;
		for (int i = 0; i < xLength; i++) {
			x = new double[xLength];
			x[i] = 1.0;
			if (getRisk() < minimumRisk)
				return i;
		}
		return -1;
	}

	private boolean checkFormulas(double[] xLocal) {
		for (int i = 0; i < xLength; i++)
			if (xLocal[i] < 0)
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
