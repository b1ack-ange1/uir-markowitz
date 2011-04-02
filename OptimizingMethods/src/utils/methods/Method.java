package utils.methods;

import utils.portfolio.Portfolio;

public abstract class Method {
	protected double[] profit;
	protected double[][] covariances;
	private int time;
	private int memory;
	private int operations;

	public Method(final Portfolio challenge) {
		time = 0;
		memory = 0;
		operations = 0;
		profit = challenge.getProfit();
		covariances = challenge.getCovariance();
	}

	public int getTime() {
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

	public abstract void evaluate();
}
