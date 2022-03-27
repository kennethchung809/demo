/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import demo.param.ParamAPIMethod;
import demo.param.ParamInstrument;
import demo.param.ParamPeriod;
import jsonObject.CandleStick;
import jsonObject.DataCStick;
import jsonObject.DataTrade;
import jsonObject.Trade;

public class MarketDataValidator {

	
	/**
	 * Consideration
	 * 
	 * 
	 * i: Trade JSON is not real time / latest data. My manual check 1-3 mins lag. consistency problem if Candle stick data and trade data are out-sync.
	 * 
     *    Candle stick time:       16482 60420000         -    16482 96360000
     *           Trade time:                                                  16482 96425191      -       16482 96382584
     *
	 * ii:  Time length definition are different between CandleStick and Trade 
	 *      a. Candle time use epoch
	 *      b. Trade DateTime is epoch ,t: unix timeStamp
	 * 
	 * iii. Task, it is not mentioned trade more than 2
	 */

	
	public static void main(String[] args) {

		String jsonString;

		MarketDataValidator marketDataValidator = new MarketDataValidator();

		System.out.println("Downloading the CandleStick JSON ");
		
		// retrieve the CandleStick JsonString object by instrument, period
		jsonString = marketDataValidator.getJsonObjectString(ParamInstrument.BTC_USDT, ParamAPIMethod.CandleStick, ParamPeriod.OneMinutes);

								// Ignore: CandleStick JSON temporarily for testing short period of time, if necessary
								/*
								jsonString = "    	{\"code\":0,\n" + "    		\"method\":\"public/get-candlestick\",\n"
										+ "    		\"result\":{\n" + "	    		\"instrument_name\":\"BTC_USDT\",\n"
										+ "	    		\"depth\":1000,\n" + "	    		\"interval\":\"1m\",\n"
										+ "	    		\"data\":[\n"
										+ "	    		        {\"t\":1648155616,\"o\":44058.31,\"h\":44058.31,\"l\":44058.31,\"c\":44058.31,\"v\":33.111505},\n"
										+ "	    		        {\"t\":1648155697,\"o\":44058.31,\"h\":44071.52,\"l\":44034.06,\"c\":44049.17,\"v\":23.320554},\n"
										+ "	    		        {\"t\":1648155775,\"o\":44059.32,\"h\":44059.32,\"l\":44059.32,\"c\":44059.32,\"v\":11.307559},\n"
										+ "	    		        {\"t\":1648155835,\"o\":44059.32,\"h\":44059.32,\"l\":44059.32,\"c\":44059.32,\"v\":11.307559}\n"
										+ "	    		       ]\n" + "    		}\n" + "    	\n" + "    	}";
								*/
		
							  // System.out.println(" jsonString result: "+jsonString);
		
		// Create the CandleStick java object from the JsonString
		CandleStick candleStick = marketDataValidator.jsonObjectFactoryCS(ParamAPIMethod.CandleStick, jsonString);

		// System.out.println("candleStick: "+candleStick);

		System.out.println("#################################################################################");
		System.out.println("#################################################################################");

		System.out.println("Downloading the Trade JSON ");
		
		// retrieve the Trade JsonString object by instrument
		jsonString = marketDataValidator.getJsonObjectString(ParamInstrument.BTC_USDT, ParamAPIMethod.Trades);

								
								//Ignore:  Trade JSON temporarily for testing short period of time, if necessary
								/*
								jsonString = " \n 	{" + "\n" + "    		\"code\":0,\n" + "    		\"method\":\"public/get-trades\",\n"
										+ "    		\"result\":{\n" + "    			\"instrument_name\":\"BTC_USDT\",\n"
										+ "    			\"data\":[\n"
										+ "	    			        {\"dataTime\":1648155600,\"d\":2360792450652095328,\"s\":\"BUY\",\"p\":44058.31,\"q\":0.014922,\"t\":1648193897704,\"i\":\"BTC_USDT\"},\n"
						
										+ "	    			        {\"dataTime\":1648155660,\"d\":2360792397607909120,\"s\":\"SELL\",\"p\":44058.32,\"q\":0.002669,\"t\":1648193896123,\"i\":\"BTC_USDT\"},\n"
										+ "	    			        {\"dataTime\":1648155661,\"d\":2360792397607909120,\"s\":\"BUY\",\"p\":44059.32,\"q\":0.002669,\"t\":1648193896123,\"i\":\"BTC_USDT\"},\n"
						
										+ "	    			        {\"dataTime\":1648155720,\"d\":2360792397607695552,\"s\":\"SELL\",\"p\":44058.32,\"q\":0.005339,\"t\":1648193896123,\"i\":\"BTC_USDT\"},\n"
										+ "	    			        {\"dataTime\":1648155780,\"d\":2360792076676092352,\"s\":\"BUY\",\"p\":44059.32,\"q\":0.001334,\"t\":1648193886559,\"i\":\"BTC_USDT\"}\n"
										+ "    			       ]\n" + "    	        }\n" + "    	}\n" + "";
							   */
						
							  //System.out.println("Trade json string: "+jsonString);

		// Create the Trade java object from the JsonString
		Trade trade = marketDataValidator.jsonObjectFactoryTrade(ParamAPIMethod.Trades, jsonString);

		System.out.println("#################################################################################");
		System.out.println("#################################################################################");

		// Pass in the trade and candleStick objects to this method and return the problem Trade ID
		Vector vFailedTradeIDList = marketDataValidator.validateTradePass(trade, candleStick);

		System.out.println("#################################################################################");
		System.out.println("#################################################################################");

		for (int i = 0; i < vFailedTradeIDList.size(); i++) {
			System.out.println("Failed trades ID:" + vFailedTradeIDList.get(i));

		}

		System.out.println("* End of validation *");
		// validateForMultiTrade (trade, candleStick);
	}

	


	/**
	 * Retrieve the JSON from crypto.com API (For Trade)
	 * 
	 * @param instrument
	 * @param method
	 * @return
	 */
	public String getJsonObjectString(String instrument, String method) {
		String jsonResult = "";
		String output;
		
		try {

			URL url = new URL("https://api.crypto.com/v2/public/get-" + method + "?instrument_name=" + instrument);

			// System.out.println("Trade URL: "+url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));


			while ((output = br.readLine()) != null) {
				jsonResult = jsonResult + output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return jsonResult;

	}


	/**
	 * Retreive the JSON from crypto.com API (For Candle stick)
	 * 
	 * @param instrument
	 * @param method
	 * @param period
	 * @return
	 */
	public String getJsonObjectString(String instrument, String method, String period) {
		String jsonResult = "";

		try {

			URL url = new URL("https://api.crypto.com/v2/public/get-" + method + "?instrument_name=" + instrument + "&timeframe=" + period);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				jsonResult = jsonResult + output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return jsonResult;
	}


	/**
	 * Generate the CandleStick object by JSON 
	 * 
	 * @param method
	 * @param jsonString
	 * @return
	 */
	public CandleStick jsonObjectFactoryCS(String method, String jsonString) {

		CandleStick candleStick = new CandleStick();
		JsonObject dataObj;
		long tUnixTimestamp;
		float open;
		float high;
		float low;
		float close;
		float volume;

		if (ParamAPIMethod.CandleStick.equals(method)) {

			System.out.println("*** Generating CandleStick object");

			Gson gson = new Gson();

			// get json object from the json string
			JsonObject csJsonObject = gson.fromJson(jsonString, JsonObject.class);

			candleStick.setMethod(csJsonObject.get("method").getAsString());

			JsonObject result = csJsonObject.getAsJsonObject("result");

			candleStick.setInstrumentName(result.get("instrument_name").getAsString());
			candleStick.setInstrumentName(result.get("interval").getAsString());

			JsonArray dataArray = result.getAsJsonArray("data");

			Vector<DataCStick> dataSet = new Vector<>();

			for (JsonElement d : dataArray) {

				dataObj = d.getAsJsonObject();
				tUnixTimestamp = dataObj.get("t").getAsLong();
				open = dataObj.get("o").getAsFloat();
				high = dataObj.get("h").getAsFloat();
				low = dataObj.get("l").getAsFloat();
				close = dataObj.get("c").getAsFloat();
				volume = dataObj.get("v").getAsFloat();

				/*
				 * System.out.println( tUnixTimestamp + " @ " + open + " @ " + high+ " @ " + low
				 * + " @ " + close + " @ " + volume + " @ " );
				 */

				DataCStick data = new DataCStick();

				data.settUnixTimestamp(tUnixTimestamp);
				data.setOpen(open);
				data.setHigh(high);
				data.setLow(low);
				data.setClose(close);
				data.setVolume(volume);

				dataSet.add(data);
			}

			candleStick.setDataSet(dataSet);

		}

		return candleStick;
	}


	/**
	 * Generate the Trade object by JSON 
	 * 
	 * @param method
	 * @param jsonString
	 * @return
	 */
	public Trade jsonObjectFactoryTrade(String method, String jsonString) {

		Trade trade = new Trade();
		JsonObject dataObj;

		float pTradePrice;
		float qTradeQty;
		String sSide;
		long dTradeID;
		long tTradeTimeStamp;
		long dateTime;

		if (ParamAPIMethod.Trades.equals(method)) {

			System.out.println("********** Generating Trade object");

			Gson gson = new Gson();

			// get json object from the json string
			JsonObject tradeJsonObject = gson.fromJson(jsonString, JsonObject.class);

			trade.setMethod(tradeJsonObject.get("method").getAsString());
			
			JsonObject result = tradeJsonObject.getAsJsonObject("result");

			trade.setInstrumentName(result.get("instrument_name").getAsString());

			JsonArray dataArray = result.getAsJsonArray("data");

			Vector<DataTrade> dataSet = new Vector<>();

				for (JsonElement d : dataArray) {
	
					dataObj = d.getAsJsonObject();
	
					pTradePrice = dataObj.get("p").getAsFloat();
					qTradeQty = dataObj.get("q").getAsFloat();
					sSide = dataObj.get("s").getAsString();
					dTradeID = dataObj.get("d").getAsLong();
					tTradeTimeStamp = dataObj.get("t").getAsLong();
					dateTime = dataObj.get("dataTime").getAsLong();
	
	
					DataTrade data = new DataTrade();
	
					data.setpTradePrice(pTradePrice);
					data.setqTradeQty(qTradeQty);
					data.setsSide(sSide);
					data.setdTradeID(dTradeID);
					data.settTradeTimeStamp(tTradeTimeStamp);
					data.setDateTime(dateTime);
	
					dataSet.add(data);
				}

			trade.setDataSet(dataSet);

		}

		return trade;
	}
	

	/**
	 * Loop the CandleStick records to validate the trade records 
	 * 
	 * @param trade
	 * @param candleStick
	 * @return
	 */
	public Vector validateTradePass(Trade trade, CandleStick candleStick) {

		boolean firstTradePass = true;
		Vector vFailedTradeID = new Vector();
		Vector vtmpTradeID = new Vector();
		final float THRESHOLD = .0001f;
		int count = 0;
		DataTrade dataTrade = new DataTrade();

		Vector vTrade = trade.getDataSet();
		Vector vCandleStick = candleStick.getDataSet();

		float firstTradeO = -1;
		float firstTradeC = -1;
		float firstTradeH = -1;
		float firstTradeL = -1;

		float lastTradeO = -1;
		float lastTradeC = -1;
		float lastTradeH = -1;
		float lastTradeL = -1;

		// Loop the Candle interval
		for (int i = 0; i < vCandleStick.size() - 1; i++) {
			DataCStick dCStick1 = (DataCStick) vCandleStick.get(i);
			DataCStick dCStick2 = (DataCStick) vCandleStick.get(i + 1);

			System.out.println("Checking CStick DateTime range: " + dCStick1.gettUnixTimestamp() + " and " + dCStick2.gettUnixTimestamp());
			System.out.println("**************************************************");
			
			// Loop the trade records
			for (int j = 0; j < vTrade.size(); j++) {
				dataTrade = (DataTrade) vTrade.get(j);

				// If the trade is within the candleStick interval
				if (dCStick1.gettUnixTimestamp() <= dataTrade.getDateTime() && dCStick2.gettUnixTimestamp() >= dataTrade.getDateTime()) {

					System.out.println("CandleSTick1-------------Time:>" + dCStick1.gettUnixTimestamp() + " O: "
							+ dCStick1.getOpen() + ", H:" + dCStick1.getHigh() + ", L:" + dCStick1.getLow() + ", C:"
							+ dCStick1.getClose());
					System.out.println("CandleSTick2-------------Time:>" + dCStick2.gettUnixTimestamp() + " O: "
							+ dCStick2.getOpen() + ", H:" + dCStick2.getHigh() + ", L:" + dCStick2.getLow() + ", C:"
							+ dCStick2.getClose());
					System.out.println("Trade1 price:" + dataTrade.getpTradePrice());

					if (count == 0) {
						// First trade
						count++;
						
						//If it is match the one trade criteria
						if (Math.abs(dataTrade.getpTradePrice() - dCStick1.getOpen()) < THRESHOLD
								&& Math.abs(dataTrade.getpTradePrice() - dCStick1.getHigh()) < THRESHOLD
								&& Math.abs(dataTrade.getpTradePrice() - dCStick1.getLow()) < THRESHOLD
								&& Math.abs(dataTrade.getpTradePrice() - dCStick1.getClose()) < THRESHOLD) {
							System.out.println("First trade ---> Passed");
							firstTradePass = true;
						} else {
							System.out.println("First trade---> Failed");
							firstTradePass = false;
							vtmpTradeID.add(dataTrade.getdTradeID());
						}

						//save the First trade details
						firstTradeO = dataTrade.getpTradePrice();
						firstTradeC = dataTrade.getpTradePrice();
						firstTradeH = dataTrade.getpTradePrice();
						firstTradeL = dataTrade.getpTradePrice();

					} else {
						count++;
						// more than 1 trade

						// Last trade = First Trade Open price
						lastTradeO = firstTradeO;
						
						// Last close price = last trade price
						lastTradeC = dataTrade.getpTradePrice();

						// Compare it is the Trade high price
						if (Math.abs(dataTrade.getpTradePrice() - lastTradeH) > THRESHOLD) {
							lastTradeH = dataTrade.getpTradePrice();

						}// Compare it is the Trade low price
						if (Math.abs(dataTrade.getpTradePrice() - lastTradeL) < THRESHOLD) {
							lastTradeL = dataTrade.getpTradePrice();

						}

						vtmpTradeID.add(dataTrade.getdTradeID());

					}
				}
			}
			
			// trades loop Finish, in the period of two CandleSticks

			
			if (count == 1 && !firstTradePass) {		//If only exist one trade
				
				vFailedTradeID.addAll(vtmpTradeID);		//Add the single failed trade into final result
				
				
			}else if (count >= 2) {     				// if exist more than one trade

				System.out.println("CandleStick 1:      " + " dCStick1.getOpen(): " + dCStick1.getOpen()
						+ " dCStick1.getClose(): " + dCStick1.getClose() + " dCStick1.getHigh(): " + dCStick1.getHigh()
						+ " dCStick1.getLow(): " + dCStick1.getLow());
				
				System.out.println("Final Trade result: " + " lastTradeO:" + lastTradeO + " lastTradeC:" + lastTradeC
						+ " lastTradeH:" + lastTradeH + " lastTradeL:" + lastTradeL);

				if (Math.abs(dCStick1.getOpen() - lastTradeO) < THRESHOLD
						&& Math.abs(dCStick1.getClose() - lastTradeC) < THRESHOLD
						&& Math.abs(dCStick1.getHigh() - lastTradeH) < THRESHOLD
						&& Math.abs(dCStick1.getLow() - lastTradeL) < THRESHOLD

				) {
					// do nothing, since it matchs the creteria 2
				} else {
					System.out.println("** Trade more than 2,  failed in this period ");
					
					//Get distinct TradeID
					LinkedHashSet<Integer> hashSet = new LinkedHashSet<Integer>(vtmpTradeID);							
					vFailedTradeID.addAll(hashSet);			//Add the failed trade records into final result

				}
			}
			
			count = 0;						// reset counter
			vtmpTradeID.clear();			//Clear the temp records
		}

		return vFailedTradeID;
	}
}




