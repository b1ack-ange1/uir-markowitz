package utils.methods.nonlinear;

import java.util.Random;

import utils.methods.Method;
import utils.portfolio.Portfolio;

public class MonteCarlo extends Method {
	private double[] step;
	private final int MAX_STEPS = 100000;

	public MonteCarlo(Portfolio challenge, double epsilon) {
		super(challenge, epsilon);
		x = new double[challenge.getSize()];
		step = new double[challenge.getSize() - 1];
		xLength = x.length;

		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}
	}

	public MonteCarlo(Portfolio challenge, double expectedProfit, double epsilon) {
		super(challenge, expectedProfit, epsilon);
		x = new double[challenge.getSize()];
		step = new double[challenge.getSize() - 1];

		xLength = x.length;

		for (int i = 0; i < xLength; i++) {
			x[i] = 1.0 / xLength;
		}
	}

	@Override
	public void evaluate() {
		startTime();
		double riskLocal = countRisk(x);
		double deltaStep = epsilon + 1;
		while (deltaStep >= epsilon) {

			if (!doStep(riskLocal))
				break;
			
			double extra = 0;
			for (int i = 0; i < xLength - 1; i++) {
				x[i] += step[i];
				extra += x[i];
			}

			x[xLength - 1] = 1 - extra;

			deltaStep = riskLocal - getRisk();
			riskLocal = risk;

			for (int i = 0; i < xLength; i++)
				System.out.print("x" + i + "=" + x[i] + ";");
			System.out.println("Current risk =" + risk);

		}
		endTime();
		finalProfit = sumProfit();
	}

	private boolean doStep(double riskOld) {
		int counter = 0;
		while (counter < MAX_STEPS) {
			generateStep();
			if (checkNewPoint(riskOld))
				return true;

			counter++;
		}
		return false;
	}

	private void generateStep() {
		Random rnd = new Random();
		for (int i = 0; i < step.length; i++) {
			step[i] = (rnd.nextBoolean() ? -1 : 1) * rnd.nextDouble() / 10;
		}
	}

	// проверка точки на допустимость и выигрышность
	private boolean checkNewPoint(double riskOld) {
		double[] temp = new double[xLength];
		double extra = 0;
		for (int i = 0; i < xLength - 1; i++) {
			temp[i] = x[i] + step[i];
			extra += temp[i];
		}

		temp[xLength - 1] = 1 - extra;

		/*if (sumProfit(temp) < expectedProfit)
			return false;*/
		for (int i = 0; i < xLength; i++) {
			if ((temp[i] < 0) || (temp[i] > 1))
				return false;
		}

		if (riskOld <= countRisk(temp))
			return false;
		return true;
	}

	private double sumProfit(double[] temp) {
		double out = 0;
		for (int i = 0; i < temp.length; i++) {
			out += temp[i] * profit[i];
		}
		return out;
	}

	protected double sumCovarIndex(int i, double[] temp) {
		double out = 0;
		for (int j = 0; j < temp.length; j++) {
			if (j != i)
				out += covariances[i][j] * temp[j];
		}
		return out;
	}

	private double countRisk(double[] temp) {
		double risk2 = 0;
		for (int i = 0; i < temp.length; i++) {
			risk2 += Math.pow(temp[i], 2);
		}

		for (int i = 0; i < temp.length; i++) {
			risk2 += temp[i] * sumCovarIndex(i, temp);
		}
		return risk2;
	}

}
