package utils.optimization;

import utils.misc.Matrix;

public abstract class ScanMethod {
	protected int N;
	protected double Epsilon;
	protected double Step;
	
	protected ScanMethod(){
		Step = Epsilon * Math.pow(N, 0.5);
	}
	
	public Matrix scan(){
		double[] scanVals = new double[N];
		double sum = 0.0;
		double min = Double.NEGATIVE_INFINITY;
		double tempMin;
		Matrix temp, result = null;
		boolean cont = true;
		
		while (cont){
			for (int i = 0; i < N; ++i){
				sum += scanVals[i];
			}
			if (Math.round(sum * 1000) == 1000){
				temp = new Matrix(scanVals);
				tempMin = this.getScanAimFunction(temp);
				if (tempMin > min){
					min = tempMin;
					result = temp.copy();
				}
			}
			sum = 0;
			scanVals = this.getNextVal(scanVals);
			if (scanVals == null) cont = false;
		}
		return result;
	}
	
	protected double[] getNextVal(double[] scanVals){
		int tempN = 0;
		while(true){
			if (scanVals[tempN] < 1.0){
				scanVals[tempN] += Step;
				return scanVals;
			}
			else if (tempN == (N-1)){
				return null;
			}
			else{
				scanVals[tempN] = 0.0;
				++tempN;
			}
		}
	}
	
	protected double getScanAimFunction(Matrix w){
		return 0.0;
	}
	
}
