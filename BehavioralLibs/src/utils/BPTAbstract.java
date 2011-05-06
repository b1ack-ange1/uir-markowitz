package utils;

import utils.misc.*;

public abstract class BPTAbstract {
	protected double H;
	protected double Alpha;
	
	protected Matrix Covariances = null;
	protected Matrix ExpectedReturns = null;
	protected Matrix LagrangeWeights = null;
	protected Matrix ScanWeights = null;
	
	public double getH(){
		return H;
	}
	
	public double getAlpha(){
		return Alpha;
	}
	
	public Matrix getCovariances(){
		return Covariances;
	}
	
	public Matrix getExpectedReturns(){
		return ExpectedReturns;
	}
}
