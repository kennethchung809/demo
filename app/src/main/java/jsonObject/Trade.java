package jsonObject;

public class Trade {

	private float pTradePrice;
	private float qTradeQty;
	private String sSide;
	private float dTradeID;
	private float tTradeTimeStamp;
	
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
