package utils.maxwealth;

import exceptions.MatricesNotMatch;
import utils.misc.Matrix;
import utils.optimization.ScanMethod;

public class ScanMaxWealth extends ScanMethod{
	private BPTMaxWealth DataContainer;
	
	public ScanMaxWealth(BPTMaxWealth bmw){
		DataContainer = bmw;
		N = (DataContainer.getExpectedReturns()).getRows();
		Epsilon = DataContainer.getScanEpsilon();
		
		init();
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
