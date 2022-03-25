package jsonObject;

import java.util.Vector;

public class Trade  {


	private float pTradePrice;
	private float qTradeQty;
	private String sSide;
	private float dTradeID;
	private float tTradeTimeStamp;
	private String method;
	private String instrumentName;
    private Vector<DataTrade> dataSet;

    
	public Vector<DataTrade> getDataSet() {
		return dataSet;
	}
	public void setDataSet(Vector<DataTrade> dataSet) {
		this.dataSet = dataSet;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public float getpTradePrice() {
		return pTradePrice;
	}
	public void setpTradePrice(float pTradePrice) {
		this.pTradePrice = pTradePrice;
	}
	public float getqTradeQty() {
		return qTradeQty;
	}
	public void setqTradeQty(float qTradeQty) {
		this.qTradeQty = qTradeQty;
	}
	public String getsSide() {
		return sSide;
	}
	public void setsSide(String sSide) {
		this.sSide = sSide;
	}
	public float getdTradeID() {
		return dTradeID;
	}
	public void setdTradeID(float dTradeID) {
		this.dTradeID = dTradeID;
	}
	public float gettTradeTimeStamp() {
		return tTradeTimeStamp;
	}
	public void settTradeTimeStamp(float tTradeTimeStamp) {
		this.tTradeTimeStamp = tTradeTimeStamp;
	}

}
