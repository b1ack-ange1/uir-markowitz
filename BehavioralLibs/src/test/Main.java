package test;

import utils.maxwealth.BPTMaxWealth;
import utils.minloss.BPTMinLoss;
import utils.misc.Matrix;
import java.util.Random;

public class Main {

	public static Random Generator = new Random();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*double[][] covs = new double[3][3];
		covs[0][0] = 0.0025;
		covs[0][1] = 0.03;
		covs[0][2] = -0.02;
		covs[1][0] = 0.03;
		covs[1][1] = 0.04;
		covs[1][2] = 0.02;
		covs[2][0] = -0.02;
		covs[2][1] = 0.02;
		covs[2][2] = 0.25;
		Matrix covars = new Matrix(covs);
		
		double[][] rets = new double[3][1];
		rets[0][0] = 0.05;
		rets[1][0] = 0.1;
		rets[2][0] = 0.25;
		Matrix returns = new Matrix(rets);
		
		//BPTMinLoss dc = BPTMinLoss.getInstance(1, 0.35);
		BPTMaxWealth dc = BPTMaxWealth.getInstance(1, -0.55, 0.15);
		dc.setCovariances(covars);
		dc.setExpectedReturns(returns);
		
		//System.out.println("Function: "+dc.getOptimalSecurityLevel(dc.getLagrangeWeights()));
		System.out.println("Function :"+dc.getExpectedWealth(dc.getLagrangeWeights()));
		System.out.println("Lagrange weigths:");
		System.out.println(dc.getLagrangeWeights());
		
		//System.out.println("Function: "+dc.getOptimalSecurityLevel(dc.getScanWeights()));
		System.out.println("Function :"+dc.getExpectedWealth(dc.getScanWeights()));
		System.out.println("Scan weigths:");
		System.out.println(dc.getScanWeights());
		
		//System.out.println("Function: "+dc.getOptimalSecurityLevel(dc.getMonteCarloWeights()));
		System.out.println("Function :"+dc.getExpectedWealth(dc.getMonteCarloWeights()));
		System.out.println("Monte-Carlo weigths:");
		System.out.println(dc.getMonteCarloWeights());
		*/
		test(19);
	}
	
	public static void test(int dim){
		DBConnector conn = new DBConnector();
		conn.getCovariances(dim);
		long timer;
		BPTMinLoss dc = BPTMinLoss.getInstance(1, 0.35);
		//BPTMaxWealth dc = BPTMaxWealth.getInstance(1, -0.1, 0.1);
		dc.setCovariances(conn.getCovs());
		dc.setExpectedReturns(conn.getRets());
		
		System.out.println("Lagrange weigths:");
		timer = System.currentTimeMillis();
		//System.out.println(dc.getLagrangeWeights());
		System.out.println("Time: "+(System.currentTimeMillis() - timer));
		//System.out.println("Function: "+dc.getOptimalSecurityLevel(dc.getLagrangeWeights()));
		//System.out.println("Function: "+dc.getExpectedWealth(dc.getLagrangeWeights()));
		System.out.println("-------------------------------");
		
		System.out.println("Scan weigths:");
		timer = System.currentTimeMillis();
		System.out.println(dc.getScanWeights());
		System.out.println("Time: "+(System.currentTimeMillis() - timer));
		System.out.println("Function: "+dc.getOptimalSecurityLevel(dc.getScanWeights()));
		//System.out.println("Function: "+dc.getExpectedWealth(dc.getScanWeights()));
		System.out.println("-------------------------------");
		
		System.out.println("Monte-Carlo weigths:");
		timer = System.currentTimeMillis();
		System.out.println(dc.getMonteCarloWeights());
		System.out.println("Time: "+(System.currentTimeMillis() - timer));
		System.out.println("Function: "+dc.getOptimalSecurityLevel(dc.getMonteCarloWeights()));
		//System.out.println("Function: "+dc.getExpectedWealth(dc.getMonteCarloWeights()));
		System.out.println("-------------------------------");
	}
}
