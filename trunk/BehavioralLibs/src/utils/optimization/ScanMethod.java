package utils.optimization;

import utils.misc.Matrix;

public abstract class ScanMethod {
	protected int N;
	protected int maxN = 0;
	protected double Epsilon;
	protected double Delta;
	protected double Step;
	protected double DefStep;
	protected double ValSum = 0.0;

	protected abstract double getAimFunction(Matrix w);
	protected boolean checkWeights(Matrix w) {return true;}

	protected void init(){
		DefStep = (0.5 * Epsilon) / Math.sqrt(N);
		double ints = Math.ceil(1.0 / DefStep);
		DefStep = 1.0 / ints;
		Step = DefStep;
	}

	public Matrix scan(){
		double[] scanVals = new double[N];
		double max = Double.NEGATIVE_INFINITY;
		double tempMax;
		Matrix temp, result = null;
		boolean cont = true;

		while (cont){
			if (Math.round(ValSum * 1000) == 1000){
				if (this.checkWeights(new Matrix(scanVals))){
					temp = new Matrix(scanVals);
					tempMax = this.getAimFunction(temp);
					if (tempMax - max <= 0.0){
						Step += DefStep / 2;
					}
					else if ((tempMax - max) > Delta){
						max = tempMax;
						result = temp.copy();
						Step += DefStep / 2;
					}
					else{
						max = tempMax;
						result = temp.copy();
						Step = DefStep;
					}
				}
			}
			scanVals = this.getNextVal(scanVals);
			if (scanVals == null) cont = false;
		}
		return result;
	}

	protected double[] getNextVal(double[] scanVals){
		int tempN = 0;
		while(true){
			if (((1.0 - ValSum) >= Step) && (scanVals[tempN] < 1.0)){
				if (tempN == 0){
					ValSum -= scanVals[tempN];
					scanVals[tempN] = (1.0 - ValSum);
					ValSum += scanVals[tempN];
					return scanVals;
				}
				scanVals[tempN] += Step;
				ValSum += Step;
				return scanVals;
			}
			else if (tempN == (N-1)){
				return null;
			}
			else{
				ValSum -= scanVals[tempN];
				scanVals[tempN] = 0.0;
				++tempN;
				if (Step > (N * (N/2) * DefStep)) Step = DefStep;
			}
		}
	}
}
