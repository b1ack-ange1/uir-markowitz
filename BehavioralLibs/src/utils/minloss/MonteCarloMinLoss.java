package utils.minloss;

import utils.misc.Matrix;
import utils.misc.NormalDistribution;
import utils.optimization.MonteCarloMethod;
import exceptions.MatricesNotMatch;
import exceptions.VarianceOutOfBounds;

public class MonteCarloMinLoss extends MonteCarloMethod{
	private BPTMinLoss DataContainer;
	private double AlphaFunc = Double.NaN;

	public MonteCarloMinLoss(BPTMinLoss bml){
		super();
		DataContainer = bml;
		N = (DataContainer.getExpectedReturns()).getRows();
	}

	protected double getAimFunction(Matrix w){
		double result = 0.0;
		try {
			if (Double.isNaN(AlphaFunc)) AlphaFunc = (NormalDistribution.singleton()).getCoordinate(DataContainer.getFailingProbability());
			
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
