package utils.methods.square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class WolfFrank extends Method {
	private double alpha;

	public WolfFrank(Portfolio challenge, double expectedProfit, double epsilon) {
		super(challenge, expectedProfit, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		int number = 0;
		double maxProfit = 0;
		for (int i = 0; i < xLength; i++) {
			if (maxProfit < profit[i]) {
				number = i;
				maxProfit = profit[i];
			}
			x[i] = 0.0;
		}
		x[number] = 1.0;

	}

	public WolfFrank(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		int number = 0;
		double maxProfit = 0;
		for (int i = 0; i < xLength; i++) {
			if (maxProfit < profit[i]) {
				number = i;
				maxProfit = profit[i];
			}
			x[i] = 0.0;
		}
		x[number] = 1.0;
	}

	@Override
	public void evaluate() {
		final double[] z = new double[xLength - 1];
		double[] x_new;
		boolean flag = true;
		Random rand = new Random();
		double delta = 0;
		double extra = 0;
		while (flag) {
			x_new = new double[xLength];
			for (int i = 0; i < xLength; i++) {
				x_new[i] = x[i];
			}
			alpha = rand.nextDouble();
			try {
				solveLinear(z, getDifArray());
			} catch (OptimizationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			delta = 0;
			for (int i = 0; i < xLength - 1; i++) {
				x_new[i] += alpha * (z[i] - x_new[i]);
				extra += x_new[i];
				delta += Math.pow(x_new[i] - x[i], 2);
			}
			x_new[xLength - 1] = 1 - extra;
			delta += Math.pow(x_new[xLength - 1] - x[xLength - 1], 2);
			x = x_new;
			if (delta < epsilon)
				flag = false;
		}

	}

	private double[] getDifArray() {
		double[] out = new double[xLength - 1];
		for (int i = 0; i < xLength - 1; i++)
			out[i] = difMainOnX(i);
		return out;
	}

	private double difMainOnX(int index) {
		double out = 0;
		out += 2 * x[index];
		out += 2 * sumCovarIndex(index);
		out -= 2 * x[xLength - 1];
		out -= 2 * sumCovarIndex(xLength - 1);
		out += 2 * x[xLength - 1] * covariances[index][xLength - 1];
		return out;
	}

	private void solveLinear(double[] z, double[] difArray)
			throws OptimizationException {
		LinearObjectiveFunction f = new LinearObjectiveFunction(difArray, 0);

		Collection constraints = new ArrayList();
		for (int i = 0; i < xLength - 1; i++) {
			double[] temp = new double[xLength - 1];
			temp[i] = 1.0;
			constraints.add(new LinearConstraint(temp, Relationship.LEQ, 1));
		}

		double[] temp = new double[xLength - 1];
		for (int i = 0; i < xLength - 1; i++)
			temp[i] = 1.0;
		constraints.add(new LinearConstraint(temp, Relationship.LEQ, 1));

		for (int i = 0; i < xLength - 1; i++)
			temp[i] = profit[i] - profit[xLength - 1];
		constraints.add(new LinearConstraint(temp, Relationship.GEQ,
				expectedProfit - profit[xLength - 1]));

		// create and run the solver
		RealPointValuePair solution = new SimplexSolver().optimize(f,
				constraints, GoalType.MINIMIZE, false);

		for (int i = 0; i < xLength - 1; i++)
			z[i] = solution.getPoint()[i];
	}
}
