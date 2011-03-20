package utils;

import java.util.Vector;

public class Constants {
	public final static int NEED_PREFETCH = -301;

	public final static Vector<String> realizedClasses = new Vector<String>();

	static {
		realizedClasses.add("TWIFScenario");
		realizedClasses.add("TMDMDimData");
		realizedClasses.add("TBCLSQLQuery");
		realizedClasses.add("TBCLEditableQuery");
		realizedClasses.add("TBCLEditableTable");
		realizedClasses.add("TBCLStoredProc");
		realizedClasses.add("TPUBReportTemplate");
		realizedClasses.add("TPUBReport");
		realizedClasses.add("TLDRTableWHASSC");
		realizedClasses.add("TLDRTableWHDim");
		realizedClasses.add("TLDRTableWHFact");
		realizedClasses.add("TLDRTableWHFactAbs");
		realizedClasses.add("TLDRTableWHFactExt");
		realizedClasses.add("TLDRTableWHNFact");
		realizedClasses.add("TLDRTableBD");
		realizedClasses.add("TLDRTableGT");
		realizedClasses.add("TLDRTableBF");
		realizedClasses.add("TMDMDimTime");
		realizedClasses.add("TMDMDomainDimData");
		realizedClasses.add("TMDMDimTimeHierarchy");
		realizedClasses.add("TWFLProcessScheme");
		realizedClasses.add("TWFLDocument");
	}
}
