package test;

import utils.methods.nonlinear.Zoitendake;
import utils.portfolio.Portfolio;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Portfolio port = new Portfolio(4);
		double[] data = { 5.0, 3.2, 0.2, 9.0 };
		port.setProfit(data);
		double[][] data2 = { { 1.0, 0.7, -0.3, -0.6 }, { 0.7, 1.0, 0.2, 0.9 },
				{ -0.3, 0.2, 1.0, -0.45 }, { -0.6, 0.9, -0.45, 1.0 } };
		port.setCovariance(data2);
		try {
			Zoitendake method = new Zoitendake(port, 6, 0.000001);
			method.evaluate();
			double[] x = method.getX();
			for (int i = 0; i < x.length; i++) {
				System.out.println("X" + i + " = " + x[i]);
			}

			System.out.println("Final Profit = " + method.getFinalProfit());
			System.out.println("Final Risk = " + method.getRisk());
			System.out.println("Time = " + method.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
