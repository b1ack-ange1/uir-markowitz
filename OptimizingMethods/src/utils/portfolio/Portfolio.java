package utils.portfolio;

public class Portfolio {
	private int size;
	private double[] profit;
	private double[][] covariance;
	private double[] weight;

	public Portfolio(int size) {
		this.size = size;
		profit = new double[size];
		covariance = new double[size][size];
		weight = new double[size];
	}

	public int getSize() {
		return size;
	}

	public double[] getWeight() {
		return weight;
	}

	public void setWeight(double[] weight) {
		this.weight = weight;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double[] getProfit() {
		return profit;
	}

	public void setProfit(double[] profit) {
		this.profit = profit;
	}

	public double[][] getCovariance() {
		return covariance;
	}

	public void setCovariance(double[][] covariance) {
		this.covariance = covariance;
	}

}
