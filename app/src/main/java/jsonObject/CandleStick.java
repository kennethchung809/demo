package jsonObject;

import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;


public class CandleStick {

	private String code ;
	private String method;
	private String instrumentName;
	private String interval;
    private Vector<Data> dataSet;

   
    
    
	public Vector<Data> getDataSet() {
		return dataSet;
	}
	public void setDataSet(Vector<Data> dataSet) {
		this.dataSet = dataSet;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}

	

	
}
