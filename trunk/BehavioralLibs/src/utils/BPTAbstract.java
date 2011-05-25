package utils;

import utils.misc.*;

public abstract class BPTAbstract {
	protected double H;
	protected double Alpha;
	protected double ScanEpsilon = 0.001;
	protected double ScanDelta = 0.001;
	
	protected Matrix Covariances = null;
	protected Matrix ExpectedReturns = null;
	protected Matrix LagrangeWeights = null;
	protected Matrix ScanWeights = null;
	protected Matrix MCWeights = null;
	
	public double getFailingProbability(){
		return Alpha;
	}
	
	public Matrix getCovariances(){
		return Covariances;
	}
	
	public Matrix getExpectedReturns(){
		return ExpectedReturns;
	}
	
	public double getScanEpsilon(){
		return ScanEpsilon;
	}
	
	public double getScanDelta(){
		return ScanDelta;
	}
	
	public void setScanEpsilon(double epsilon){
		ScanWeights = null;
		ScanEpsilon = epsilon;
	}
	
	public void setScanDelta(double delta){
		ScanWeights = null;
		ScanDelta = delta;
	}
}