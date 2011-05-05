package test;

import exceptions.*;
import utils.misc.*;
import utils.maxwealth.*;
import utils.minloss.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] covs = new double[3][3];
		covs[0][0] = 0.0025;
		covs[0][1] = 0.0;
		covs[0][2] = 0.0;
		covs[1][0] = 0.0;
		covs[1][1] = 0.04;
		covs[1][2] = 0.02;
		covs[2][0] = 0.0;
		covs[2][1] = 0.02;
		covs[2][2] = 0.25;
		Matrix covars = new Matrix(covs);
		
		double[][] rets = new double[3][1];
		rets[0][0] = 0.05;
		rets[1][0] = 0.1;
		rets[2][0] = 0.25;
		Matrix returns = new Matrix(rets);
		
		BPTMinLoss dc = BPTMinLoss.getInstance(1);
		dc.setCovariances(covars);
		dc.setExpectedReturns(returns);
		System.out.println((dc.getLagrangeWeights()).toString());
		System.out.println((dc.getOptimalH(dc.getLagrangeWeights())));
	}

}
