package utils.optimization;

import java.util.Random;
import utils.misc.Matrix;

public abstract class MonteCarloMethod {
	protected Random Generator;
	protected int N;
	
	protected MonteCarloMethod(){
		Generator = new Random();
	}
	
	protected abstract double getAimFunction(Matrix w);
	protected boolean checkWeights(Matrix w) {return true;}
	
	protected Matrix generatePoint(){
		double[] vals = new double[N];
		double sum = 0.0;
		for (int i = 0; i < N - 1; ++i){
			vals[i] = Generator.nextDouble() * (1.0 - sum);
			sum += vals[i];
		}
		vals[N-1] = 1.0 - sum;
		return new Matrix(vals);
	}
	
	public Matrix scan(){
		Matrix temp;
		double tempMax;
		Matrix result = null;
		double max = Double.NEGATIVE_INFINITY;
		
		for (int i = 0; i < (int)Math.pow(N, 3.0*N); ++i){
			temp = this.generatePoint();
			if (this.checkWeights(temp)){
				tempMax = this.getAimFunction(temp);
				if (tempMax > max){
					max = tempMax;
					result = temp.copy();
				}
			}
		}
		
		return result;
	}
}
