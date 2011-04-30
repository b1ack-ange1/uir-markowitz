package utils.methods.convex;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class ArrowGurvic extends Method {
	public double alpha;
	public double[] l;
	public int xLength;

	/*
	 * l - массив лямбда, содержит ограничения на xi<=1 - первые n-1 штук далее
	 * 1 ограничение Eopt<=sum(xi*Ei) + (1-sum(xi))En 1 ограничение sum(xi)<=1
	 */

	public ArrowGurvic(Portfolio challenge, double alpha, double epsilon) {
		super(challenge, epsilon);
		this.alpha = alpha;
		x = new double[challenge.getSize()];
		l = new double[challenge.getSize() + 1];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
			l[i] = 0.1;
		}
		l[xLength] = 0.1;

	}

	public ArrowGurvic(Portfolio challenge, double alpha,
			double expectedProfit, double epsilon) {
		super(challenge, expectedProfit, epsilon);
		this.alpha = alpha;
		x = new double[challenge.getSize()];
		l = new double[challenge.getSize() + 1];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
			l[i] = 0.1;
		}
		l[xLength] = 0.1;
	}

	@Override
	public void evaluate() {
		startTime();
		boolean isTheEnd = false;
		double[] x_new = new double[xLength];
		for (int i = 0; i < xLength; i++) {
			x_new[i] = x[i];
		}
		double[] l_new = new double[xLength + 1];
		for (int i = 0; i < xLength + 1; i++) {
			l_new[i] = l[i];
		}

		double risk = Double.MAX_VALUE;
		while (!isTheEnd) {
			for (int i = 0; i < xLength - 1; i++) {
				x_new[i] = Math.max(0, x[i] - alpha * difMainOnX(i));
			}
			for (int i = 0; i < l.length; i++) {
				l_new[i] = Math.max(0, l[i] + alpha * difMainOnL(i));
			}

			x = x_new;
			l = l_new;
			x[xLength - 1] = 1.0 - sumWeights();

			x_new = new double[xLength];
			for (int i = 0; i < xLength; i++) {
				x_new[i] = x[i];
			}

			l_new = new double[xLength + 1];
			for (int i = 0; i < xLength + 1; i++) {
				l_new[i] = l[i];
			}

			isTheEnd = stopCriteria();

			for (int i = 0; i < xLength; i++)
				System.out.print("x" + i + "=" + x[i] + ";");
			for (int i = 0; i < l.length; i++)
				System.out.print("l" + i + "=" + l[i] + ";");
			System.out.println("Current risk =" + getRisk());
			isTheEnd = isTheEnd || (risk <= this.risk);
			if (risk <= this.risk) {
				this.risk = risk;

				x = x_new;
				l = l_new;
				break;
			}
			risk = this.risk;

		}
		endTime();
		finalProfit = sumProfit();
	}

	private double sumWeights() {
		double out = 0;
		for (int i = 0; i < xLength - 1; i++)
			out += x[i];
		return out;
	}

	private boolean stopCriteria() {
		boolean criteria = true;
		for (int i = 0; i < xLength - 1; i++) {
			criteria = criteria
					&& (((difMainOnX(i) <= epsilon) && (x[i] > 0)) || ((difMainOnX(i) >= 0) && (x[i] <= 0)));
			if (!criteria)
				return false;
		}

		for (int i = 0; i < l.length; i++) {
			criteria = criteria
					&& (((difMainOnL(i) <= epsilon) && (l[i] > 0)) || ((difMainOnL(i) <= 0) && (l[i] <= 0)));
			if (!criteria)
				return false;
		}

		return true;
	}

	private double difMainOnX(int index) {
		double out = 0;
		out += 2 * x[index];
		out += 2 * sumCovarIndex(index);
		out += x[xLength - 1]
				* (2 * covariances[xLength - 1][index] - covariances[xLength - 1][xLength - 1]);
		out -= (2 * sumCovarIndex(xLength - 1) + covariances[xLength - 1][xLength - 1]
				* x[xLength - 1]);
		out -= l[index];
		out += (profit[xLength - 1] - profit[index]) * l[l.length - 1];
		out += l[xLength];
		return out;
	}

	private double difMainOnL(int index) {
		if (index < xLength - 1) {
			// ограничение xi<=1
			return x[index] - 1;
		} else {
			if (index == xLength - 1)
				// необходимо обязательно обновлять вес Xn
				return expectedProfit - sumProfit();
			else
				return sumWeights() - 1;
		}
	}

	protected double sumCovarIndex(int i) {
		double out = 0;
		for (int j = 0; j < xLength - 1; j++) {
			if (j != i)
				out += covariances[i][j] * x[j];
		}
		return out;
	}

}
