package utils;

import utils.misc.*;

public abstract class BPTAbstract {
	protected double H;
	protected double Alpha;
	
	protected Matrix Covariances = null;
	protected Matrix ExpectedReturns = null;
	protected Matrix LagrangeWeigths = null;
}
