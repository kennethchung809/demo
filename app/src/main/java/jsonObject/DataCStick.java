package jsonObject;

public class DataCStick {

	private long tUnixTimestamp;
	private float open;
	private float high;
	private float low;
	private float close;
	private float volume;
	
	
	public long gettUnixTimestamp() {
		return tUnixTimestamp;
	}
	public void settUnixTimestamp(long tUnixTimestamp) {
		this.tUnixTimestamp = tUnixTimestamp;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}

}


