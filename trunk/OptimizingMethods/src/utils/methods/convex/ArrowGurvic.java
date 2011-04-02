package utils.methods.convex;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class ArrowGurvic extends Method {
	private double alpha;
	private double expectedProfit;
	private double[] x, l;

	/*
	 * l - массив лямбда, содержит ограничения на xi<=1 - первые n штук далее 2
	 * ограничения на sum(xi)=1: sum(xi)<=1 & sum(xi)>=1 далее одно ограничение
	 * на Eopt<=sum(xi*Ei)
	 */

	public ArrowGurvic(Portfolio challenge, double alpha, double expectedProfit) {
		super(challenge);
		this.alpha = alpha;
		this.expectedProfit = expectedProfit;
		x = new double[challenge.getSize()];
		l = new double[challenge.getSize() + 3];
		for (int i = 0; i < x.length; i++) {
			x[i] = 0;
			l[i] = 0;
		}

		l[l.length - 1] = l[l.length - 2] = l[l.length - 3] = 0;

	}

	@Override
	public void evaluate() {
		boolean isTheEnd = false;

		while (!isTheEnd) {
			for (int i = 0; i < x.length; i++) {
				x[i] = Math.max(0, x[i] - alpha * difMainOnX(i));
			}
			for (int i = 0; i < l.length; i++) {
				l[i] = Math.max(0, l[i] + alpha * difMainOnL(i));
			}
			isTheEnd = stopCriteria();
		}

	}

	private boolean stopCriteria() {
		boolean criteria = true;
		for (int i = 0; i < x.length; i++) {
			criteria = criteria
					&& (((difMainOnX(i) == 0) && (x[i] > 0)) || ((difMainOnX(i) >= 0) && (x[i] == 0)));
		}

		for (int i = 0; i < l.length; i++) {
			criteria = criteria
					&& (((difMainOnL(i) == 0) && (l[i] > 0)) || ((difMainOnL(i) <= 0) && (l[i] == 0)));
		}

		return criteria;
	}

	private double difMainOnX(int index) {
		double out = 0;
		out += 2 * x[index];
		out += 2 * sumCovarIndex(index);
		out -= sumArray(l);
		out -= profit[index] * l[l.length - 1];
		return out;
	}

	private double difMainOnL(int index) {
		if (index < x.length) {
			// ограничение xi<=1
			return 1 - x[index];
		} else {
			if (index == x.length) {
				return sumArray(x) - 1;
			} else if (index == x.length + 1) {
				return 1 - sumArray(x);
			} else {
				return expectedProfit - sumProfit();
			}
		}
	}

	private double sumArray(double[] arr) {
		double out = 0;
		for (int i = 0; i < arr.length; i++)
			out += arr[i];
		return out;
	}

	private double sumProfit() {
		double out = 0;
		for (int i = 0; i < x.length; i++) {
			out += x[i] * profit[i];
		}
		return out;
	}

	private double sumCovarIndex(int i) {
		double out = 0;
		for (int j = 0; j < x.length; j++) {
			out += covariances[i][j] * x[j];
		}
		return out;
	}

}
