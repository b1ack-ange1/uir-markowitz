package utils.maxwealth;

import exceptions.MatricesNotMatch;
import utils.misc.Matrix;
import utils.optimization.PeanoMethod;

/**
 * @todo доделать родителя. когда-нибудь :)
 */

public class PeanoMaxWealth extends PeanoMethod{
	private BPTMaxWealth DataContainer;
	private final double OptConst = 200.0;
	
	public PeanoMaxWealth(BPTMaxWealth bmw){
		DataContainer = bmw;
		N = (DataContainer.getExpectedReturns()).getRows();
		Epsilon = 0.01;
		S = (int)(Math.ceil(Math.log(N/Epsilon) / Math.log(2)));
	}
	
	protected double getAimFunction(Matrix w){
		double result = 0.0;
		double sum = 0.0;
		double[] wVals; 
		try {
			result = (DataContainer.getImpliedRisk() / 2.0) * ((((w.getTransposed()).multiply(DataContainer.getCovariances())).multiply(w)).getValues())[0][0];
			result -= (((w.getTransposed()).multiply(DataContainer.getExpectedReturns())).getValues())[0][0];
			
			wVals = w.getCol(0);
			for (int i = 0; i < w.getRows(); ++i){
				sum += wVals[i];
			}
			result += OptConst * (sum - (w.getRows() / 2)) * (sum - (w.getRows() / 2));
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
