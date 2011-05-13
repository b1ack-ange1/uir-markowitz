package utils.methods;

import utils.exceptions.OptimizingException;
import utils.portfolio.Portfolio;

public abstract class Method {
	public double[] profit;
	public double[][] covariances;
	public double[] x;
	public double expectedProfit;
	public double finalProfit;
	public double risk;
	public double epsilon;
	public int xLength;

	private long time;
	private int memory;
	private int operations;

	public double[] getX() {
		return x;
	}

	public double getFinalProfit() {
		finalProfit = sumProfit();
		return finalProfit;
	}

	public Method(final Portfolio challenge, double epsilon) {
		time = 0;
		memory = 0;
		operations = 0;
		profit = challenge.getProfit();
		covariances = challenge.getCovariance();
		expectedProfit = 0;
		this.epsilon = epsilon;
	}

	public Method(final Portfolio challenge, double expectedProfit,
			double epsilon) {
		time = 0;
		memory = 0;
		operations = 0;
		profit = challenge.getProfit();
		covariances = challenge.getCovariance();
		this.expectedProfit = expectedProfit;
		this.epsilon = epsilon;
	}

	public long getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getOperations() {
		return operations;
	}

	public void setOperations(int operations) {
		this.operations = operations;
	}

	public double[] getProfit() {
		return profit;
	}

	public void setProfit(double[] profit) {
		this.profit = profit;
	}

	public double[][] getCovariances() {
		return covariances;
	}

	public void setCovariances(double[][] covariances) {
		this.covariances = covariances;
	}

	public abstract void evaluate() throws OptimizingException;

	public void startTime() {
		time = System.nanoTime();
	}

	public void endTime() {
		time = System.nanoTime() - time;
	}

	protected double sumArray(double[] arr, int j) {
		double out = 0;
		for (int i = 0; i < arr.length - 3; i++)
			out += arr[i];
		return out;
	}

	protected double sumProfit() {
		double out = 0;
		for (int i = 0; i < x.length; i++) {
			out += x[i] * profit[i];
		}
		return out;
	}

	protected double sumCovarIndex(int i) {
		double out = 0;
		for (int j = 0; j < x.length; j++) {
			if (j != i)
				out += covariances[i][j] * x[j];
		}
		return out;
	}

	public double getRisk() {
		countRisk();
		return risk;
	}

	private void countRisk() {
		risk = 0;
		for (int i = 0; i < x.length; i++) {
			risk += Math.pow(x[i], 2);
		}

		for (int i = 0; i < x.length; i++) {
			risk += x[i] * sumCovarIndex(i);
		}
	}

	protected boolean isSumCorrect() {
		double temp = 0.0;
		for (int i = 0; i < xLength; i++) {
			temp += x[i];
		}
		return (temp == 1);
	}

	protected void normalize() {
		double sum = 0;
		for (int i = 0; i < xLength; i++)
			sum += x[i];

		for (int i = 0; i < xLength; i++)
			x[i] /= sum;
	}
}
