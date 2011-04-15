import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class quoteGetter {	
	public quoteGetter(){
		
	}
	
	private void getQuote(int tickerID, String tickerName, Calendar calend) {
		try {
			URL url = new URL("http://195.128.78.52/export9.out");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			DataOutputStream outStream = new DataOutputStream( conn.getOutputStream() );

			// ��������� ������
			// ���������� ��������. ������� ���� ������ �� ������������ :)
			String requestText = "d=d";
			// �������� ����� ����
			requestText += "&market=1";
			// �������� �������
			requestText += "&em="+tickerID;
			// ����, ����� (������� � 0), ��� ���� ������ �������
			requestText += "&df="+calend.get(Calendar.DAY_OF_MONTH);
			requestText += "&mf="+calend.get(Calendar.MONTH);
			requestText += "&my="+calend.get(Calendar.YEAR);
			// ����, ����� (������� � 0), ��� ���� ����� �������
			requestText += "&dt="+calend.get(Calendar.DAY_OF_MONTH);
			requestText += "&mt="+calend.get(Calendar.MONTH);
			requestText += "&yt="+calend.get(Calendar.YEAR);
			// ������������� 1 ���
			requestText += "&p=7";
			// ��� � ���������� ����������� �����
			requestText += "&f=myfile&e=.txt";
			// ������������� ������
			requestText += "&cn="+tickerName;
			// ������ ���� (��/��/��) � ������ ������� (��:��)
			requestText += "&dtf=4&tmf=4";
			// �������� ����� ���� ������ �����
			requestText += "&MSOR=0";
			// ����������� ����� �������
			requestText += "&sep=1";
			// ����������� �������� ������
			requestText += "&sep2=1";
			// ������ ������ � ����: ��� ������
			requestText += "&datf=1";
			// �� ��������� ��������� �����
			requestText += "&at=0";
			// ��������� ������� ��� ������
			requestText += "&fsp=1";
			
			outStream.writeBytes(requestText);
			outStream.flush();

			// Get the response
			StringBuffer answer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				answer.append(line);
				answer.append("\n");
			}
			outStream.close();
			reader.close();

			try {
				//������� ���� ��� ���������� ���������� � ������ append
				FileWriter fstream = new FileWriter("D:\\logs\\quoteGetter\\import_"+calend.get(Calendar.DAY_OF_MONTH)+"_"+calend.get(Calendar.MONTH)+"_"+calend.get(Calendar.YEAR)+".log", true);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(answer.toString());
				out.close();
			} catch (Exception e) {// Catch exception if any
				//System.out.println("Error: " + e.getMessage());
			}
		} catch (IOException ex) {
			System.out.println("catched:" + ex.toString());
		}
	}
	
	public void getQuotes(){
		String[] tickers = {"AVAZ","AFLT","PKBA","MMBM","GAZP","GMKN","SIBN","KMAZ","CMST",
				"KSGR","LKOH","MVID","MGNT","PLZL","ROSB","HYDR","SBER","SNGS","CTLK","YKEN"};
		int[] tickerIDs = {39,29,17375,15914,16842,795,2,15544,20707,75094,8,19737,17086,17123,
				17273,20266,3,4,16052,15903};
		Calendar calend = Calendar.getInstance();
		calend.add(Calendar.DATE, -1);
		for (int i = 0; i < 20; ++i){
			getQuote(tickerIDs[i], tickers[i], calend);
		}
		
		DBConnector dbConn = new DBConnector();
		dbConn.importFile("D:\\logs\\quoteGetter\\import_"+calend.get(Calendar.DAY_OF_MONTH)+"_"+calend.get(Calendar.MONTH)+"_"+calend.get(Calendar.YEAR)+".log");
	}
}