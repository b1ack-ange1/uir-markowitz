package utils.minloss;

import utils.misc.*;
import utils.optimization.ScanMethod;
import exceptions.MatricesNotMatch;
import exceptions.VarianceOutOfBounds;

public class ScanMinLoss extends ScanMethod{
	private BPTMinLoss DataContainer;
	private double AlphaFunc = Double.NaN;

	public ScanMinLoss(BPTMinLoss bml){
		DataContainer = bml;
		N = (DataContainer.getExpectedReturns()).getRows();
		Epsilon = 0.02;
	}

	protected double getScanAimFunction(Matrix w){
		double result = 0.0;
		try {
			if (!Double.isNaN(AlphaFunc)) AlphaFunc = (NormalDistribution.singleton()).getCoordinate(DataContainer.getAlpha());
			
			result = (((w.getTransposed()).multiply(DataContainer.getExpectedReturns())).getValues())[0][0];
			result += AlphaFunc * Math.sqrt(((((w.getTransposed()).multiply(DataContainer.getCovariances())).multiply(w)).getValues())[0][0]);
		} catch (VarianceOutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
