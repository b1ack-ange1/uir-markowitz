package utils.minloss;

import java.util.TreeMap;
import utils.BPTAbstract;
import utils.misc.Matrix;

public class BPTMinLoss extends BPTAbstract{

	private static TreeMap<Integer, BPTMinLoss> _instances = new TreeMap();
	public static BPTMinLoss getInstance(int portfolioID){
		if (BPTMinLoss._instances.get(portfolioID) == null){
			BPTMinLoss._instances.put(portfolioID, new BPTMinLoss());
		}
		return BPTMinLoss._instances.get(portfolioID);
	}
	
	private BPTMinLoss(){
		
	}
}
