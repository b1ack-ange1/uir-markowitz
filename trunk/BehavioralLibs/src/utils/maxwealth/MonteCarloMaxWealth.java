package utils.maxwealth;

import utils.misc.Matrix;
import utils.misc.NormalDistribution;
import utils.optimization.MonteCarloMethod;
import exceptions.MatricesNotMatch;
import exceptions.VarianceOutOfBounds;

public class MonteCarloMaxWealth extends MonteCarloMethod {
	private BPTMaxWealth DataContainer;
	private double AlphaFunc = Double.NaN;

	public MonteCarloMaxWealth(BPTMaxWealth bmw) {
		super();
		DataContainer = bmw;
		N = (DataContainer.getExpectedReturns()).getRows();
	}

	protected boolean checkWeights(Matrix w) {
		try {
			if (Double.isNaN(AlphaFunc)) {
				AlphaFunc = (NormalDistribution.singleton())
						.getCoordinate(DataContainer.getFailingProbability());
			}
			double chk = (((w.getTransposed()).multiply(DataContainer
					.getExpectedReturns())).getValues())[0][0];
			chk += AlphaFunc
					* Math.sqrt(((((w.getTransposed()).multiply(DataContainer
							.getCovariances())).multiply(w)).getValues())[0][0]);
			return (chk >= DataContainer.getSecurityLevel());
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VarianceOutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	protected double getAimFunction(Matrix w) {
		double result = 0.0;
		try {
			result = (((w.getTransposed()).multiply(DataContainer
					.getExpectedReturns())).getValues())[0][0];
			// result -= (DataContainer.getImpliedRisk() / 2.0) *
			// ((((w.getTransposed()).multiply(DataContainer.getCovariances())).multiply(w)).getValues())[0][0];
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
