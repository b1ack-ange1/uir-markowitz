package utils.maxwealth;

import utils.misc.Matrix;
import utils.optimization.MonteCarloMethod;
import exceptions.MatricesNotMatch;

public class MonteCarloMaxWealth extends MonteCarloMethod{
	private BPTMaxWealth DataContainer;
	
	public MonteCarloMaxWealth(BPTMaxWealth bmw){
		super();
		DataContainer = bmw;
		N = (DataContainer.getExpectedReturns()).getRows();
	}
	
	protected double getAimFunction(Matrix w){
		double result = 0.0;
		try {
			result = (((w.getTransposed()).multiply(DataContainer.getExpectedReturns())).getValues())[0][0];
			result -= (DataContainer.getImpliedRisk() / 2.0) * ((((w.getTransposed()).multiply(DataContainer.getCovariances())).multiply(w)).getValues())[0][0];
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
