package utils.optimization;

import utils.misc.Matrix;

public abstract class ScanMethod {
	protected int N;
	protected double Epsilon;
	protected double Step;
	
	protected abstract double getAimFunction(Matrix w);
	
	protected void init(){
		Step = Epsilon * Math.pow(N, 0.5);
		double ints = Math.ceil(1.0 / Step);
		Step = 1.0 / ints;
	}
	
	public Matrix scan(){
		double[] scanVals = new double[N];
		double sum = 0.0;
		double max = Double.NEGATIVE_INFINITY;
		double tempMax;
		Matrix temp, result = null;
		boolean cont = true;
		
		while (cont){
			for (int i = 0; i < N; ++i){
				sum += scanVals[i];
			}
			if (Math.round(sum * 1000) == 1000){
				temp = new Matrix(scanVals);
				tempMax = this.getAimFunction(temp);
				if (tempMax > max){
					max = tempMax;
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
}
