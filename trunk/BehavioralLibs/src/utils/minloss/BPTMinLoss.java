package utils.minloss;

import java.util.TreeMap;

import exceptions.MatricesNotMatch;
import exceptions.MatrixNotSquare;
import exceptions.MatrixZeroDeterminant;
import exceptions.VarianceOutOfBounds;
import utils.BPTAbstract;
import utils.misc.*;

public class BPTMinLoss extends BPTAbstract{

	private static TreeMap<Integer, BPTMinLoss> _instances = new TreeMap<Integer, BPTMinLoss>();
	public static BPTMinLoss getInstance(int portfolioID){
		if (BPTMinLoss._instances.get(portfolioID) == null){
			BPTMinLoss._instances.put(portfolioID, new BPTMinLoss());
		}
		return BPTMinLoss._instances.get(portfolioID);
	}

	private Matrix Ksi = null;
	private Matrix InvSigmaSums = null;
	private Matrix InvSigmaRetSums = null;
	private double AlphaFunc = Double.NaN;
	private double Beta = Double.NaN;
	private double Sigma = Double.NaN;
	private Double Epsilon;

	private BPTMinLoss(){
		Epsilon = 0.05;
		Alpha = 0.15;
	}

	public void setCovariances(Matrix covs){
		Covariances = covs;
		LagrangeWeights = null;
	}

	public void setExpectedReturns(Matrix costs){
		ExpectedReturns = costs;
		LagrangeWeights = null;
	}

	private Matrix getLagrangeJacobian(Matrix x){
		if ((x.getRows() != ExpectedReturns.getRows()) || (x.getCols() != 1)) return null;
		double[][] vals = new double[Covariances.getRows()][Covariances.getCols()];
		Matrix single = new Matrix(ExpectedReturns.getRows(), 1, 1.0, true);
		double underSqr = 0.0;
		double[] ksiVals;
		double[] invSigmaVals;
		double[] invSigmaRetVals;
		double[] xVals = x.getCol(0);
		try {
			if (Ksi == null){
				Ksi = (single.getTransposed()).multiply(Covariances);
			}
			if (Double.isNaN(AlphaFunc)){
				AlphaFunc = (NormalDistribution.singleton()).getCoordinate(Alpha);
			}
			if (InvSigmaSums == null){
				InvSigmaSums = (Covariances.getInvertible()).multiply(single);
			}
			if (InvSigmaRetSums == null){
				InvSigmaRetSums = (Covariances.getInvertible()).multiply(ExpectedReturns);
			}
			if (Double.isNaN(Sigma)){
				Sigma = ((((single.getTransposed()).multiply(Covariances.getInvertible())).multiply(single)).getValues())[0][0];
			}
			if (Double.isNaN(Beta)){
				Beta = ((((single.getTransposed()).multiply(Covariances.getInvertible())).multiply(ExpectedReturns)).getValues())[0][0];
			}
			ksiVals = Ksi.getRow(0);
			invSigmaVals = InvSigmaSums.getCol(0);
			invSigmaRetVals = InvSigmaRetSums.getCol(0);

			for (int i = 0; i < ExpectedReturns.getRows(); ++i){
				underSqr += ksiVals[i] * xVals[i] * xVals[i];
			}
			underSqr = Math.sqrt(underSqr);

			for (int i = 0; i < Covariances.getRows(); ++i){
				for (int j = 0; j < Covariances.getCols(); ++j){
					vals[i][j] = ((xVals[j] * ksiVals[j]) / (underSqr * AlphaFunc)) * (invSigmaRetVals[i] - ((Beta * invSigmaVals[i]) / Sigma));
					if (i == j){
						vals[i][j] -= 1.0;
					}
				}
			}
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VarianceOutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixNotSquare e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixZeroDeterminant e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Matrix result = new Matrix(vals);
		return result;
	}

	private Matrix getLagrangeFunction(Matrix x){
		if ((x.getRows() != ExpectedReturns.getRows()) || (x.getCols() != 1)) return null;
		double[][] vals = new double[Covariances.getRows()][1];
		Matrix single = new Matrix(ExpectedReturns.getRows(), 1, 1.0, true);
		double underSqr = 0.0;
		double funcHead;
		double[] ksiVals;
		double[] xVals = x.getCol(0);
		try {
			if (Ksi == null){
				Ksi = (single.getTransposed()).multiply(Covariances);
			}
			if (Double.isNaN(AlphaFunc)){
				AlphaFunc = (NormalDistribution.singleton()).getCoordinate(Alpha);
			}
			if (InvSigmaSums == null){
				InvSigmaSums = (Covariances.getInvertible()).multiply(single);
			}
			if (InvSigmaRetSums == null){
				InvSigmaRetSums = (Covariances.getInvertible()).multiply(ExpectedReturns);
			}
			if (Double.isNaN(Sigma)){
				Sigma = ((((single.getTransposed()).multiply(Covariances.getInvertible())).multiply(single)).getValues())[0][0];
			}
			if (Double.isNaN(Beta)){
				Beta = ((((single.getTransposed()).multiply(Covariances.getInvertible())).multiply(ExpectedReturns)).getValues())[0][0];
			}
			ksiVals = Ksi.getRow(0);

			for (int i = 0; i < ExpectedReturns.getRows(); ++i){
				underSqr += ksiVals[i] * xVals[i] * xVals[i];
			}
			underSqr = Math.sqrt(underSqr);

			funcHead = (underSqr / AlphaFunc);

			for (int i = 0; i < ExpectedReturns.getRows(); ++i){
				vals[i][0] = funcHead * ((InvSigmaRetSums.getValues())[i][0] - ((InvSigmaSums.getValues())[i][0] * ((Beta / Sigma) + (AlphaFunc / (Sigma * underSqr))))) - xVals[i];
			}
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VarianceOutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixNotSquare e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixZeroDeterminant e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Matrix result = new Matrix(vals);
		return result;
	}

	public Matrix getLagrangeWeights(){
		if (LagrangeWeights != null) return LagrangeWeights;
		if ((Covariances == null) || (ExpectedReturns == null)) return null;

		int iterations = 0;
		boolean exit;
		double[] diffVals;
		Matrix diff;
		LagrangeWeights = new Matrix(ExpectedReturns.getRows(), 1, 1.5, true);
		try {
			while (true){
				Matrix prev = LagrangeWeights.copy();
				LagrangeWeights = prev.sub(((this.getLagrangeJacobian(prev)).getInvertible()).multiply(this.getLagrangeFunction(prev)));
				diff = LagrangeWeights.sub(prev);
				diffVals = diff.getCol(0);
				exit = true;
				for (int i = 0; i < diff.getRows(); ++i){
					exit = exit & (Math.abs(diffVals[i]) < Epsilon);
					if (!exit) break;
				}
				if (exit) break;
				if (++iterations > 10000){
					System.out.println("Enough!");
					break;
				}
			}
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixNotSquare e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixZeroDeterminant e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return LagrangeWeights;
	}

	public double getOptimalH(Matrix weights){
		if ((Covariances == null) || (ExpectedReturns == null)) return Double.NaN;
		if ((weights.getRows() != ExpectedReturns.getRows()) || (weights.getCols() != 1)) return Double.NaN;
		if (weights.getRows() != Covariances.getCols()) return Double.NaN;

		double result = Double.NaN;
		try {
			if (Double.isNaN(AlphaFunc)){
				AlphaFunc = (NormalDistribution.singleton()).getCoordinate(Alpha);
			}
			result = (((weights.getTransposed()).multiply(ExpectedReturns)).getValues())[0][0] + AlphaFunc * Math.sqrt(((((weights.getTransposed()).multiply(Covariances)).multiply(weights)).getValues())[0][0]);
		} catch (VarianceOutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public Matrix getScanWeights(){
		if (ScanWeights != null) return ScanWeights;
		if ((Covariances == null) || (ExpectedReturns == null)) return null;
		
		ScanMinLoss scw = new ScanMinLoss(this);
		ScanWeights = scw.scan();
		
		return ScanWeights;
	}
}
