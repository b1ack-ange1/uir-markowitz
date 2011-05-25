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

import utils.Methods;
import utils.exceptions.OptimizingException;
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
	public void evaluate() throws OptimizingException {
		startTime();
		final double[] z = new double[xLength - 1];
		double[] x_new;
		boolean flag = true;
		Random rand = new Random();
		double delta = 0;
		double extra = 0;
		while (flag) {
			operations++;
			extra = 0;
			x_new = new double[xLength];
			for (int i = 0; i < xLength; i++) {
				x_new[i] = x[i];
			}
			alpha = rand.nextDouble();
			try {
				solveLinear(z, getDifArray());
			} catch (OptimizationException e) {
				e.printStackTrace();
				throw (new OptimizingException("Can't solve linear problem",
						Methods.METOD_WOLFFRANK));

			}

			delta = 0;
			for (int i = 0; i < xLength - 1; i++) {
				operations++;
				x_new[i] += alpha * (z[i] - x_new[i]);
				operations++;
				extra += x_new[i];
				operations++;
				delta += Math.pow(x_new[i] - x[i], 2);
			}
			operations++;
			x_new[xLength - 1] = 1 - extra;
			operations++;
			delta += Math.pow(x_new[xLength - 1] - x[xLength - 1], 2);
			x = x_new;

			for (int i = 0; i < x.length; i++) {
				System.out.print("X" + i + " = " + x[i] + "; ");
			}
			System.out.println("risk = " + getRisk());

			if (delta < epsilon)
				flag = false;
		}
		endTime();
	}

	private double[] getDifArray() {
		double[] out = new double[xLength - 1];
		for (int i = 0; i < xLength - 1; i++)
			out[i] = -difMainOnX(i);
		return out;
	}

	private double difMainOnX(int index) {
		double out = 0;
		out += 2 * x[index] * covariances[index][index];
		out += 2 * sumCovarIndex(index);
		out -= 2 * x[xLength - 1] * covariances[xLength - 1][xLength - 1];
		out -= 2 * sumCovarIndex(xLength - 1);
		out += 2 * (x[xLength - 1] - x[index])
				* covariances[index][xLength - 1];
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
		for (int i = 0; i < xLength - 1; i++) {
			temp[i] = 1.0;
		}
		constraints.add(new LinearConstraint(temp, Relationship.LEQ, 1));

		temp = new double[xLength - 1];
		for (int i = 0; i < xLength - 1; i++) {
			operations++;
			temp[i] = profit[i] - profit[xLength - 1];
			constraints.add(new LinearConstraint(temp, Relationship.GEQ,
					expectedProfit - profit[xLength - 1]));
		}
		// create and run the solver
		operations+=Math.pow(xLength,2);
		RealPointValuePair solution = new SimplexSolver().optimize(f,
				constraints, GoalType.MAXIMIZE, true);

		for (int i = 0; i < xLength - 1; i++)
			z[i] = solution.getPoint()[i];
	}
}
