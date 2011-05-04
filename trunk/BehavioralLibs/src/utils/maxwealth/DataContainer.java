package utils.maxwealth;
import utils.*;
import utils.misc.*;
import java.util.TreeMap;

import exceptions.MatricesNotMatch;
import exceptions.MatrixNotSquare;
import exceptions.MatrixZeroDeterminant;
import exceptions.VarianceOutOfBounds;

public class DataContainer extends BPTAbstract{
	private static TreeMap<Integer, DataContainer> _instances = new TreeMap();
	
	public static DataContainer getInstance(int portfolioID){
		if (DataContainer._instances.get(portfolioID) == null){
			DataContainer._instances.put(portfolioID, new DataContainer());
		}
		return DataContainer._instances.get(portfolioID);
	}
	
	private double ImpliedRisk = Double.NaN;
	private Matrix LagrangeWeigths = null;
	
	private DataContainer(){
		
	}
	
	public void setCovariances(Matrix covs){
		Covariances = covs;
		ImpliedRisk = Double.NaN;
	}
	
	public void setExpectedReturns(Matrix costs){
		ExpectedReturns = costs;
		ImpliedRisk = Double.NaN;
	}
	
	public double getImpliedRisk(){
		if (!Double.isNaN(ImpliedRisk)) return ImpliedRisk;
		if ((Covariances == null) || (ExpectedReturns == null)) return Double.NaN;
	
		double beta;
		double sigma;
		Matrix single = new Matrix(Covariances.getRows(), 1, 1.0, true);
		Matrix temp;
		double kapa1;
		double kapa2;
		double kapa3;
		double kapa4;
		double kapa5;
		try {
			//выкладки на листочке
			Matrix InvCovariances = Covariances.getInvertible();
			Matrix ksi = InvCovariances.multiply(ExpectedReturns);
			Matrix ro = InvCovariances.multiply(single);
			Matrix ksi_;
			
			temp = (single.getTransposed()).multiply(InvCovariances);
			beta = ((temp.multiply(ExpectedReturns)).getValues())[0][0];
			sigma = ((temp.multiply(single)).getValues())[0][0];
			ksi_ = ksi.sub(ro.multiplyNumber(beta / sigma));
			
			kapa1 = (((ksi_.getTransposed()).multiply(ExpectedReturns)).getValues())[0][0];
			kapa2 = (((ro.getTransposed()).multiply(ExpectedReturns)).getValues())[0][0] / sigma;
			
			temp = (single.getTransposed()).multiply(ksi_);
			kapa3 = kapa1 - ((beta/sigma) * (temp.getValues())[0][0]);
			kapa5 = (((single.getTransposed()).multiply(ro)).getValues())[0][0] / (sigma * sigma);
			kapa4 = ((temp.getValues())[0][0] / sigma - kapa5 * beta) + kapa2;
			
			H = - 0.15;
			Alpha = 0.2;
			double funcAlphaOrig = (NormalDistribution.singleton()).getCoordinate(Alpha);
			double funcAlpha = funcAlphaOrig * funcAlphaOrig;
			//это именно квадратное уравнение
			double a = ((kapa2 - H) * (kapa2 - H) - kapa5 * funcAlpha);
			double b = (2.0 * kapa1 * (kapa2 - H) - kapa4 * funcAlpha);
			double c = kapa1 * kapa1 - kapa3 * funcAlpha;
			double D = Math.sqrt(b * b - 4.0 * a * c);
			double solve1 = (-b + D) / (2.0 * a);
			double solve2 = (-b - D) / (2.0 * a);
			
			//решение должно быть больше 0 и удовлетворять уравнению (там был корень)
			double check;
			if (solve1 > 0.0){
				check = (1.0/solve1)*kapa1 + kapa2 + funcAlphaOrig * Math.sqrt((1.0/(solve1*solve1))*kapa3 + (1.0/solve1)*kapa4 + kapa5);
				if (Math.round(check * 100) == Math.round(H * 100)) ImpliedRisk = solve1;
			}
			if (solve2 > 0.0){
				check = (1.0/solve2)*kapa1 + kapa2 + funcAlphaOrig * Math.sqrt((1.0/(solve2*solve2))*kapa3 + (1.0/solve2)*kapa4 + kapa5);
				if (Math.round(check * 100.0) == Math.round(H * 100.0)) ImpliedRisk = solve2;
			}
		} catch (MatrixNotSquare e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixZeroDeterminant e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VarianceOutOfBounds e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ImpliedRisk;
	}
	
	public Matrix getLagrangeWeights(){
		if (LagrangeWeigths != null) return LagrangeWeigths;
		if ((Covariances == null) || (ExpectedReturns == null)) return null;
		if (Double.isNaN(ImpliedRisk)){
			ImpliedRisk = this.getImpliedRisk();
			if (Double.isNaN(ImpliedRisk)) return null;
		}
		
		Matrix result = new Matrix(ExpectedReturns.getRows(), 1);
		Matrix single = new Matrix(ExpectedReturns.getRows(), 1, 1.0, true);
		Matrix temp;
		try {
			Matrix InvCovariances = Covariances.getInvertible();
			//все вычисления можно записать в одну строчку, но я это потом не прочитаю
			temp = (single.getTransposed()).multiply(InvCovariances);
			double numerator = ((temp.multiply(ExpectedReturns)).getValues())[0][0] - ImpliedRisk;
			double denominator = ((temp.multiply(single)).getValues())[0][0];
			if (denominator == 0.0) return null;
			
			Matrix inner = ExpectedReturns.sub(new Matrix(ExpectedReturns.getRows(), 1, numerator / denominator, true));
			result = (InvCovariances.multiply(inner)).multiplyNumber(1.0 / ImpliedRisk);
		} catch (MatrixNotSquare e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatrixZeroDeterminant e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatricesNotMatch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
