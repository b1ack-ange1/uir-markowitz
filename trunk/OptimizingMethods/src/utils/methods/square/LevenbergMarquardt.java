package utils.methods.square;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.optimization.VectorialPointValuePair;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;

import utils.Methods;
import utils.exceptions.OptimizingException;
import utils.methods.Method;
import utils.portfolio.Portfolio;
import Jama.Matrix;

public class LevenbergMarquardt extends Method {
	private Matrix baseMatrix;
	private Matrix transitionMatrix;

	public LevenbergMarquardt(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		xLength = x.length;
		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}

	}

	public LevenbergMarquardt(Portfolio challenge, double expectedProfit,
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
		startTime();
		LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();
		optimizer.setMaxIterations(1000000000);
		double[] target = new double[xLength + 3];
		double[] weights = new double[xLength + 3];

		for (int i = 0; i < xLength + 3; i++) {
			switch (i) {
			case 0: {
				// risk
				target[i] = 0.0;
				weights[i] = 10.0;
			}
			case 1: {
				// sum(xi)=1;
				target[i] = 1.0;
				weights[i] = 1.0;
			}
			case 2: {
				// profits
				target[i] = Math.log(expectedProfit + 1);
				weights[i] = 1.0;
			}
			default: {
				// xi>=0
				target[i] = Math.log(1.0 / xLength + 1);
				weights[i] = 1.0;
			}
			}
		}
		try {
			VectorialPointValuePair result = optimizer.optimize(
					new QuadraticProblem(), target, weights, x);
			x = result.getPoint();
			normalize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new OptimizingException(e.getMessage(),
					Methods.METOD_LEVENBERGMARQUARDT);
		}
		endTime();
	}

	private class QuadraticProblem implements
			DifferentiableMultivariateVectorialFunction {

		@Override
		public double[] value(double[] arg0)
				throws FunctionEvaluationException, IllegalArgumentException {
			
			double[] out = new double[xLength + 3];
			out[0] = getRisk(arg0);
			out[1] = Math.exp(1000 * Math.pow(sumWeights(arg0) - 1, 2));
			out[2] = Math.log(1 + sumProfit(arg0) - expectedProfit);
			for (int i = 3; i < xLength + 3; i++)
				out[i] = Math.log(1.0 + arg0[i - 3]);
			operations+=xLength;
			return out;
		}

		@Override
		public MultivariateMatrixFunction jacobian() {
			return new MultivariateMatrixFunction() {

				@Override
				public double[][] value(double[] arg0)
						throws FunctionEvaluationException,
						IllegalArgumentException {
					return jacobian(arg0);
				}

			};

		}

		protected double[][] jacobian(double[] arg0) {
			double[][] jacobian = new double[xLength + 3][xLength];
			for (int j = 0; j < xLength; j++) {
				operations++;
				jacobian[0][j] = 2 * arg0[j] * covariances[j][j] + 2
						* sumCovarIndex(j, arg0);
				operations++;
				jacobian[1][j] = 2000.0 * (sumWeights(arg0) - 1)
						* Math.exp(1000 * Math.pow(sumWeights(arg0) - 1, 2));
				operations++;
				jacobian[2][j] = profit[j]
						/ (1.0 + sumProfit(arg0) - expectedProfit);
			}

			for (int i = 3; i < xLength + 3; i++) {
				for (int j = 0; j < xLength; j++) {
					operations++;
					if (i == j + 3)
						jacobian[i][j] = 1.0 / (1.0 + arg0[j]);
				}

			}

			return jacobian;
		}

		public double sumWeights(double[] x) {
			double out = 0;
			for (int i = 0; i < xLength; i++)
				out += x[i];
			operations+=xLength;
			return out;
		}

		public double sumProfit(double[] x) {
			double out = 0;
			for (int i = 0; i < xLength; i++) {
				operations++;
				out += x[i] * profit[i];
			}
			return out;
		}

		private double getRisk(double[] x) {
			double risk = 0;
			for (int i = 0; i < xLength; i++) {
				operations++;
				risk += Math.pow(x[i], 2);
			}

			for (int i = 0; i < xLength; i++) {
				operations++;
				risk += x[i] * sumCovarIndex(i, x);
			}

			return risk;
		}

		protected double sumCovarIndex(int i, double[] x) {
			double out = 0;
			for (int j = 0; j < xLength; j++) {
				operations++;
				if (j != i)
					out += covariances[i][j] * x[j];
			}
			return out;
		}
	}

}
