package utils.methods.nonlinear;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class Zoitendake extends Method {
	private double[] gradient;
	private double[] s; // вектор Sk

	public Zoitendake(Portfolio challenge, double expectedProfit, double epsilon) {
		super(challenge, expectedProfit, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		gradient = new double[xLength];
		s = new double[xLength];

		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}

	}

	public Zoitendake(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		gradient = new double[xLength];
		s = new double[xLength];

		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}
	}

	@Override
	public void evaluate() {
		startTime();
		boolean gradientZero = checkGradient();
		boolean isBorder;
		final ArrayList<Integer> list = new ArrayList<Integer>();

		while (!gradientZero) {
			operations++;
			isBorder = checkActive(list);
			countGradient();

			if (isBorder) {
				// граничная точка
				if (checkDirection(list)) {
					// допустимый градиент
					for (int i = 0; i < gradient.length - 1; i++)
						s[i] = gradient[i];
				} else {
					// ходим по условиям
					try {
						double deltaMax = linearSolution(list);
						if (deltaMax <= 0)
							break;
					} catch (OptimizationException e) {
						e.printStackTrace();
					}

				}

			} else {
				// внутренняя точка
				for (int i = 0; i < gradient.length - 1; i++)
					s[i] = gradient[i];
			}

			double alpha = getMaxAlpha(list);
			for (int i = 0; i < xLength - 1; i++)
				x[i] += alpha * s[i];
			operations+=xLength;
			x[xLength - 1] = 1 - sumWeights();
			operations++;
			if (x[xLength - 1] < 0) {
				for (int i = 0; i < xLength - 1; i++)
					x[i] -= alpha * s[i];
				operations+=xLength;
				x[xLength - 1] = 1 - sumWeights();
				operations++;
				return;
			}
			for (int i = 0; i < xLength; i++)
				System.out.print("x" + i + "=" + x[i] + ";");
			System.out.println("Current risk =" + getRisk());

			list.clear();
			gradientZero = checkGradient();
		}
		endTime();
		finalProfit = sumProfit();
	}

	private double linearSolution(final ArrayList<Integer> list)
			throws OptimizationException {
		double[] temp = new double[xLength - 1];
		for (int i = 0; i < xLength - 1; i++) {
			operations++;
			temp[i] = difMainOnX(i);
		}

		LinearObjectiveFunction f = new LinearObjectiveFunction(temp, 0);
		Collection constraints = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			operations++;
			int index = list.get(i);
			if (index == 0) {
				// доходности
				temp = new double[xLength - 1];
				for (int j = 0; j < xLength - 1; j++) {
					temp[j] = profit[j];
				}
				constraints
						.add(new LinearConstraint(temp, Relationship.GEQ, 0));

			} else if ((index > 0) && (index < xLength)) {
				// xi<=1
				temp = new double[xLength - 1];
				for (int j = 0; j < xLength - 1; j++) {
					if (j == index - 1)
						temp[j] = 1;
					else
						temp[j] = 0;
				}
				constraints
						.add(new LinearConstraint(temp, Relationship.LEQ, 0));

			} else if (index == xLength) {
				// sum(xi)<=1
				temp = new double[xLength - 1];
				for (int j = 0; j < xLength - 1; j++) {
					temp[j] = 1;
				}
				constraints
						.add(new LinearConstraint(temp, Relationship.GEQ, 0));

			} else {
				// xi>=0
				temp = new double[xLength - 1];
				for (int j = 0; j < xLength - 1; j++) {
					if (j == index - 1 + xLength)
						temp[j - xLength] = 1;
					/*
					 * else temp[j - xLength] = 0;
					 */
				}
				constraints
						.add(new LinearConstraint(temp, Relationship.GEQ, 0));

			}

			for (int j = 0; j < xLength - 1; j++) {
				temp = new double[xLength - 1];
				temp[j] = 1;
				constraints
						.add(new LinearConstraint(temp, Relationship.GEQ, -1));
				constraints
						.add(new LinearConstraint(temp, Relationship.LEQ, 1));
			}

		}

		// create and run the solver
		RealPointValuePair solution = new SimplexSolver().optimize(f,
				constraints, GoalType.MAXIMIZE, false);
		operations+=Math.pow(xLength,2);
		for (int i = 0; i < xLength - 1; i++)
			s[i] = solution.getPoint()[i];

		return solution.getValue();
	}

	private double getMaxAlpha(final ArrayList<Integer> list) {
		double alpha1 = 0.001;
		double[] temp = new double[xLength];
		for (int i = 0; i < xLength; i++)
			temp[i] = x[i] + alpha1 * s[i];

		while (checkRestraines(temp, list)) {
			alpha1 += 0.001;
			for (int i = 0; i < xLength; i++)
				temp[i] += alpha1 * s[i];

		}

		double alpha2 = Double.MAX_VALUE;
		for (int i = 0; i < 2 * xLength; i++) {
			if (!list.contains(i)) {
				double down = checkDirectionRestrainedAlpha2S(i, s);
				if (down > 0) {
					double up = checkDirectionRestrainedAlpha2X(i, x);
					alpha2 = Math.min(up / down, alpha2);
				}
			}

		}

		return Math.min(alpha1, alpha2);
	}

	private boolean checkRestraines(double[] temp, ArrayList<Integer> list) {
		for (int i = 0; i < 2 * xLength; i++) {
			if (!list.contains(i))
				if (!checkDirectionRestrained(i, temp))
					return false;
		}
		return true;
	}

	private double checkDirectionRestrainedAlpha2S(int index, double[] temp2) {
		if (index == 0) {
			// доходности
			double temp = 0;
			for (int i = 0; i < temp2.length - 1; i++) {
				temp += temp2[i] * profit[i];
			}

			return temp;
		} else if ((index > 0) && (index < xLength)) {
			// xi<=1
			return temp2[index];
		} else if (index == xLength) {
			// sum(xi)<=1
			double sumWeights = 0.0;
			for (int i = 0; i < temp2.length - 1; i++) {
				sumWeights += temp2[i];
			}

			return sumWeights;
		} else {
			// xi>=0
			return temp2[index - xLength];
		}
	}

	private double checkDirectionRestrainedAlpha2X(int index, double[] temp2) {
		if (index == 0) {
			// доходности
			double temp = 0;
			for (int i = 0; i < temp2.length - 1; i++) {
				operations++;
				temp += temp2[i] * profit[i];
			}

			return temp - expectedProfit;
		} else if ((index > 0) && (index < xLength)) {
			operations++;
			// xi<=1
			return 1 - temp2[index];
		} else if (index == xLength) {
			// sum(xi)<=1
			double sumWeights = 0.0;
			for (int i = 0; i < temp2.length - 1; i++) {
				operations++;
				sumWeights += temp2[i];
			}

			return 1 - sumWeights;
		} else {
			operations++;
			// xi>=0
			return temp2[index - xLength];
		}
	}

	private boolean checkDirectionRestrained(int index, double[] temp2) {
		if (index == 0) {
			// доходности
			double temp = 0;
			for (int i = 0; i < temp2.length - 1; i++) {
				operations++;
				temp += temp2[i] * profit[i];
			}

			if (temp >= expectedProfit) {
				return true;
			} else {
				return false;
			}
		} else if ((index > 0) && (index < xLength)) {
			// xi<=1
			if (temp2[index] <= 1)
				return true;
			else
				return false;
		} else if (index == xLength) {
			// sum(xi)<=1
			double sumWeights = 0.0;
			for (int i = 0; i < temp2.length - 1; i++) {
				operations++;
				sumWeights += temp2[i];
			}

			if (sumWeights <= 1) {
				return true;
			} else {
				return false;
			}
		} else {
			// xi>=0
			if (temp2[index - xLength] >= 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	private void countGradient() {
		for (int i = 0; i < xLength - 1; i++) {
			operations++;
			gradient[i] = difMainOnX(i);
		}
	}

	// допустимое направление
	private boolean checkDirection(final ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			operations++;
			if (!checkDirection(i))
				return false;
		}
		return true;
	}

	private boolean checkDirection(int index) {
		if (index == 0) {
			// доходности
			double temp = 0;
			for (int i = 0; i < gradient.length - 1; i++) {
				operations++;
				temp += gradient[i] * profit[i];
			}

			if (temp >= 0) {
				return true;
			} else {
				return false;
			}
		} else if ((index > 0) && (index < xLength)) {
			// xi<=1
			if (gradient[index] <= 0)
				return true;
			else
				return false;
		} else if (index == xLength) {
			// sum(xi)<=1
			double sumWeights = 0.0;
			for (int i = 0; i < gradient.length - 1; i++) {
				operations++;
				sumWeights += gradient[i];
			}

			if (sumWeights <= 0) {
				return true;
			} else {
				return false;
			}
		} else {
			// xi>=0
			if (gradient[index - xLength] >= 0) {
				return true;
			} else {
				return false;
			}
		}

	}

	private boolean checkGradient() {
		// проверка производной
		boolean gradientZero = true;
		for (int i = 0; i < xLength - 1; i++) {
			if (difMainOnX(i) >= epsilon) {
				operations++;
				gradientZero = false;
				break;
			}
		}
		return gradientZero;
	}

	private double difMainOnX(int index) {
		double out = 0;
		operations++;
		out += 2 * x[index] * covariances[index][index];
		operations++;
		out += 2 * sumCovarIndex(index);
		operations++;
		out += x[xLength - 1]
				* (2 * covariances[xLength - 1][index] - covariances[xLength - 1][xLength - 1]);
		operations++;
		out -= (2 * sumCovarIndex(xLength - 1) + covariances[xLength - 1][xLength - 1]
				* x[xLength - 1]);
		return -out;
	}

	protected double sumCovarIndex(int i) {
		double out = 0;
		for (int j = 0; j < xLength - 1; j++) {
			operations++;
			if (j != i)
				out += covariances[i][j] * x[j];
		}
		return out;
	}

	/*
	 * Метод возвращает true, если точка - граничная. Массив activeList - массив
	 * активных ограничений
	 */
	private boolean checkActive(final ArrayList<Integer> activeList) {
		boolean result = false;
		for (int i = 0; i < 2 * xLength; i++) {
			operations++;

			if (isActive(i)) {
				result = true;
				activeList.add(i);
			}
		}
		return result;
	}

	private boolean isActive(int index) {
		operations++;
		if (index == 0) {
			// доходности
			if (expectedProfit < sumProfit()) {
				return false;
			} else {
				return true;
			}
		} else if ((index > 0) && (index < xLength)) {
			// xi<=1
			if (x[index] < 1)
				return false;
			else
				return true;
		} else if (index == xLength) {
			// sum(xi)<=1
			if (sumWeights() < 1) {
				return false;
			} else {
				return true;
			}
		} else {
			// xi>=0
			if (x[index - xLength] > 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	private double sumWeights() {
		double out = 0;
		for (int i = 0; i < xLength - 1; i++)
			out += x[i];
		operations+=xLength;
		return out;
	}

}
