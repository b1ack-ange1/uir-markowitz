package utils.methods.convex;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class ArrowGurvic extends Method {
	public double alpha;
	public double[] l;

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
				operations++;
				x_new[i] = Math.max(0, x[i] - alpha * difMainOnX(i));
			}
			for (int i = 0; i < l.length; i++) {
				operations++;
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

	/*		for (int i = 0; i < xLength; i++)
				System.out.print("x" + i + "=" + x[i] + ";");
			for (int i = 0; i < l.length; i++)
				System.out.print("l" + i + "=" + l[i] + ";");
			System.out.println("Current risk =" + getRisk());
*/
			if (risk <= this.risk) {
				this.risk = risk;

				x = x_new;
				l = l_new;
				break;
			}

			isTheEnd = stopCriteria();
			//isTheEnd = isTheEnd || (risk <= this.risk);
			risk = this.risk;

		}
		endTime();
		finalProfit = sumProfit();
	}

	private double sumWeights() {
		double out = 0;
		for (int i = 0; i < xLength - 1; i++)
			out += x[i];
		operations+=xLength;
		return out;
	}

	private boolean stopCriteria() {
		boolean criteria = true;
		for (int i = 0; i < xLength - 1; i++) {
			operations+=6;
			criteria = criteria

					&& (((difMainOnX(i) <= epsilon) && (x[i] > 0)) || ((difMainOnX(i) >= 0) && (x[i] <= 0)));
			if (!criteria)
				return false;
		}

		for (int i = 0; i < l.length; i++) {
			operations+=8;
			criteria = criteria

					&& (((difMainOnL(i) <= epsilon) && (l[i] > 0)) || ((difMainOnL(i) <= 0) && (l[i] <= 0)));
			if (!criteria)
				return false;
		}

		return true;
	}

	private double difMainOnX(int index) {
		double out = 0;
		operations+=18;
		out += 2 * x[index]*covariances[index][index];
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
		operations++;
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
			operations+=2;
			if (j != i)
				out += covariances[i][j] * x[j];
		}
		return out;
	}

}
