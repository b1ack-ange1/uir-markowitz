package utils.methods.convex;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class ArrowGurvic extends Method {
	private double alpha;
	private double[] l;

	/*
	 * l - массив лямбда, содержит ограничения на xi<=1 - первые n штук далее 2
	 * ограничения на sum(xi)=1: sum(xi)<=1 & sum(xi)>=1 далее одно ограничение
	 * на Eopt<=sum(xi*Ei)
	 */

	public ArrowGurvic(Portfolio challenge, double alpha, double epsilon) {
		super(challenge, epsilon);
		this.alpha = alpha;
		x = new double[challenge.getSize()];
		l = new double[challenge.getSize() + 3];
		for (int i = 0; i < x.length; i++) {
			x[i] = 0.5;
			l[i] = 0;
		}

		l[l.length - 1] = l[l.length - 2] = l[l.length - 3] = 0;

	}

	public ArrowGurvic(Portfolio challenge, double alpha,
			double expectedProfit, double epsilon) {
		super(challenge, expectedProfit, epsilon);
		this.alpha = alpha;
		x = new double[challenge.getSize()];
		l = new double[challenge.getSize() + 3];
		for (int i = 0; i < x.length; i++) {
			x[i] = 0.5;
			l[i] = 0;
		}

		l[l.length - 1] = l[l.length - 2] = l[l.length - 3] = 0;

	}

	@Override
	public void evaluate() {
		startTime();
		boolean isTheEnd = false;
		double[] x_new = x;
		double[] l_new = l;
		double risk = Double.MAX_VALUE;
		while (!isTheEnd) {
			for (int i = 0; i < x.length; i++) {
				x_new[i] = Math.max(0, x[i] - alpha * difMainOnX(i));
			}
			for (int i = 0; i < l.length; i++) {
				l_new[i] = Math.max(0, l[i] + alpha * difMainOnL(i));
			}

			x = x_new;
			l = l_new;
			isTheEnd = stopCriteria();
			
			
			for (int i=0; i<x.length; i++) System.out.print("x"+i+"="+x[i]+";");
			for (int i=0; i<l.length; i++) System.out.print("l"+i+"="+l[i]+";");
			System.out.println("Current risk =" + getRisk());
		}
		endTime();
		finalProfit = sumProfit();
	}

	private boolean stopCriteria() {
		boolean criteria = true;
		for (int i = 0; i < x.length; i++) {
			criteria = criteria
					&& (((difMainOnX(i) == 0) && (x[i] > 0)) || ((difMainOnX(i) >= 0) && (x[i] <= 0)));
			if (!criteria)
				return false;
		}

		for (int i = 0; i < l.length; i++) {
			criteria = criteria
					&& (((difMainOnL(i) == 0) && (l[i] > 0)) || ((difMainOnL(i) <= 0) && (l[i] <= 0)));
			if (!criteria)
				return false;
		}

		return true;
	}

	private double difMainOnX(int index) {
		double out = 0;
		out += 2 * x[index];
		out += 2 * sumCovarIndex(index);
		out += l[index];
		out += l[x.length];
		out -= l[x.length + 1];
		out -= profit[index] * l[l.length - 1];
		return out;
	}

	private double difMainOnL(int index) {
		if (index < x.length) {
			// ограничение xi<=1
			return x[index] - 1;
		} else {
			if (index == x.length) {
				return sumArray(x, 0) - 1;
			} else if (index == x.length + 1) {
				return 1 - sumArray(x, 0);
			} else {
				return expectedProfit - sumProfit();
			}
		}
	}

}
