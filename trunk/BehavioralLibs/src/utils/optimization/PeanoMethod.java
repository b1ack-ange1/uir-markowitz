package utils.optimization;

import utils.misc.*;

/**
 * @todo Придумать алгоритм расстановки номеров на гиперкубы. Ну и тада заработает каэша.
 */
public abstract class PeanoMethod {
	
	protected int S;
	protected int N;
	protected double Epsilon;
	
	protected Matrix getPeaks(double v){ 
		double[][] resVals = new double[S][1];
		double temp;
		for (int i = 0; i < S; ++i){
			resVals[i][0] = 0;
			for (int j =0; j < N; ++j){
				v *= 2;
				temp = Math.floor(v);
				resVals[i][0] += Math.pow(temp, j);
				v -= temp;
			}
		}
		return new Matrix(resVals);
	}
	
	protected Matrix getHyperCubeCenter(Matrix peaks){
		double[][] resVals = new double[N][1];
		double[] peakVals = peaks.getCol(0);
		for (int i = 0; i < N; ++i){
			resVals[i][0] = -0.5 + (peakVals[i] * Math.pow(2.0, N)) + Math.pow(2.0, -(N+1));  
		}
		
		return new Matrix(resVals);
	}
	
	public Matrix scan(){
		double temp;
		double min = this.getScanAimFunction(0.0);
		double minI = 0.0;
		for (double i = 0.0; i <= 1.0; i+= Epsilon){
			temp = this.getScanAimFunction(i);
			System.out.println(temp);
			if (temp < min){
				min = temp;
				minI = i;
			}
		}
		return this.getHyperCubeCenter(this.getPeaks(minI));
	}
	
	protected double getScanAimFunction(double v){
		return this.getAimFunction(this.getHyperCubeCenter(this.getPeaks(v)));
	}
	
	protected double getAimFunction(Matrix x){
		return 0.0;
	}
}
