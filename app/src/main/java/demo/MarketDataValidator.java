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
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.joda.time.LocalTime;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import demo.param.ParamAPIMethod;

import com.google.gson.JsonElement;
import com.google.gson.*;
import java.util.*;
import demo.param.*;
import jsonObject.*;

public class MarketDataValidator {
	

    public static void main(String[] args) {
    	     	 
    	 String jsonString ;
    	 
    	 
    	MarketDataValidator marketDataValidator = new MarketDataValidator();
    	
    	jsonString = marketDataValidator.getJsonObjectString(ParamInstrument.BTC_USDT, ParamAPIMethod.CandleStick, ParamPeriod.OneMinutes);
    	
    	
    	// temporarily for testing short period of time
    	jsonString = "    	{\"code\":0,\n"
    			+ "    		\"method\":\"public/get-candlestick\",\n"
    			+ "    		\"result\":{\n"
    			+ "	    		\"instrument_name\":\"BTC_USDT\",\n"
    			+ "	    		\"depth\":1000,\n"
    			+ "	    		\"interval\":\"1m\",\n"
    			+ "	    		\"data\":[\n"
    			+ "	    		        {\"t\":1648155616,\"o\":44058.31,\"h\":44058.31,\"l\":44058.31,\"c\":44058.31,\"v\":33.111505},\n"
    			+ "	    		        {\"t\":1648155697,\"o\":44058.31,\"h\":44071.52,\"l\":44034.06,\"c\":44049.17,\"v\":23.320554},\n"
    			+ "	    		        {\"t\":1648155775,\"o\":44059.32,\"h\":44059.32,\"l\":44059.32,\"c\":44059.32,\"v\":11.307559},\n"
    			+ "	    		        {\"t\":1648155835,\"o\":44059.32,\"h\":44059.32,\"l\":44059.32,\"c\":44059.32,\"v\":11.307559}\n"
    			+ "	    		       ]\n"
    			+ "    		}\n"
    			+ "    	\n"
    			+ "    	}";
    	
    	


    		
    	//System.out.println("Kenneth jsonString result: "+jsonString);
    	
    	CandleStick candleStick = marketDataValidator.jsonObjectFactoryCS(ParamAPIMethod.CandleStick, jsonString);
    	
    	//System.out.println("candleStick: "+candleStick);


    	
    	//#####################
    	
    	System.out.println("#####################################################################################################################################################");
       	System.out.println("#####################################################################################################################################################");
        

    	jsonString = marketDataValidator.getJsonObjectString(ParamInstrument.BTC_USDT, ParamAPIMethod.Trades);
    	
    	
    	
    	jsonString=" \n 	{"
    			+ "\n"
    			+ "    		\"code\":0,\n"
    			+ "    		\"method\":\"public/get-trades\",\n"
    			+ "    		\"result\":{\n"
    			+ "    			\"instrument_name\":\"BTC_USDT\",\n"
    			+ "    			\"data\":[\n"
    			+ "	    			        {\"dataTime\":1648155600,\"d\":2360792450652095328,\"s\":\"BUY\",\"p\":44058.31,\"q\":0.014922,\"t\":1648193897704,\"i\":\"BTC_USDT\"},\n"
    			
    			+ "	    			        {\"dataTime\":1648155660,\"d\":2360792397607909120,\"s\":\"SELL\",\"p\":44058.32,\"q\":0.002669,\"t\":1648193896123,\"i\":\"BTC_USDT\"},\n"
    			+ "	    			        {\"dataTime\":1648155661,\"d\":2360792397607909120,\"s\":\"BUY\",\"p\":44059.32,\"q\":0.002669,\"t\":1648193896123,\"i\":\"BTC_USDT\"},\n"
    			
    			+ "	    			        {\"dataTime\":1648155720,\"d\":2360792397607695552,\"s\":\"SELL\",\"p\":44058.32,\"q\":0.005339,\"t\":1648193896123,\"i\":\"BTC_USDT\"},\n"
    			+ "	    			        {\"dataTime\":1648155780,\"d\":2360792076676092352,\"s\":\"BUY\",\"p\":44059.32,\"q\":0.001334,\"t\":1648193886559,\"i\":\"BTC_USDT\"}\n"
    			+ "    			       ]\n"
    			+ "    	        }\n"
    			+ "    	}\n"
    			+ "";
    	
    	
    	// System.out.println("Trade json string: "+jsonString);
    	  	
    	
    	 
    	Trade trade = marketDataValidator.jsonObjectFactoryTrade(ParamAPIMethod.Trades, jsonString);
    	
    	System.out.println("#####################################################################################################################################################");
       	System.out.println("#####################################################################################################################################################");
        
       
        Vector vcheckResult = marketDataValidator.validateTradePass (trade, candleStick);
       
       
       
	   	System.out.println("#####################################################################################################################################################");
	   	System.out.println("#####################################################################################################################################################");
	    
       for (int i = 0 ; i<vcheckResult.size(); i++) {
    	   System.out.println("ALL failed trades:"+vcheckResult.get(i));
    	
    	}
       
       	//  validateForMultiTrade (trade, candleStick);
    }
    
    
    
    public Vector validateTradePass (Trade trade, CandleStick candleStick) {
    	
    	boolean firstTradePass = true;
    	Vector vFailTradeID = new Vector();
    	Vector vtmpTradeID = new Vector();
    	final float THRESHOLD = .0001f;
    	int count = 0;
    	DataTrade dataTrade =  new DataTrade();
    	
    	Vector vTrade = trade.getDataSet();
    	Vector vCandleStick = candleStick.getDataSet();

    	float firstTradeO =0 ;
    	float firstTradeC =0;
    	float firstTradeH =0;
    	float firstTradeL=0 ;
    	
    	float lastTradeO =0;
    	float lastTradeC =0;
    	float lastTradeH =0;
    	float lastTradeL =0;
    	
    	//Loop the Candle interval
    	for (int i = 0; i < vCandleStick.size()-1; i++) {
    		 DataCStick dCStick1 = (DataCStick) vCandleStick.get(i);
    		 DataCStick dCStick2 = (DataCStick) vCandleStick.get(i+1);
    		
    		 System.out.println( "\n CStick DateTime range: "+ dCStick1.gettUnixTimestamp()    + " and " + dCStick2.gettUnixTimestamp());
    		 
  		    System.out.println("**************************************************");
    		  // Check the price between the interval
             for (int j = 0; j < vTrade.size(); j++) {
            	 dataTrade = (DataTrade) vTrade.get(j);
            	 
            	 if (dCStick1.gettUnixTimestamp()<=dataTrade.getDateTime() && dCStick2.gettUnixTimestamp()>=dataTrade.getDateTime()){
            		
            		   		
         			
            			System.out.println("CandleSTick1-------------Time:>"+dCStick1.gettUnixTimestamp()+" O: "+dCStick1.getOpen() + ", H:"+dCStick1.getHigh() + ", L:"+dCStick1.getLow() + ", C:"+dCStick1.getClose());
            			System.out.println("CandleSTick2-------------Time:>"+dCStick2.gettUnixTimestamp()+" O: "+dCStick2.getOpen() + ", H:"+dCStick2.getHigh() + ", L:"+dCStick2.getLow() + ", C:"+dCStick2.getClose());
            			System.out.println("Trade1 price:"+dataTrade.getpTradePrice());              		
            			
            			if(count==0) {
            				//First trade
            				count ++;
            				if( Math.abs(dataTrade.getpTradePrice() - dCStick1.getOpen() ) < THRESHOLD &&
		        					Math.abs(dataTrade.getpTradePrice() - dCStick1.getHigh() ) < THRESHOLD &&
		        					Math.abs(dataTrade.getpTradePrice() - dCStick1.getLow() ) < THRESHOLD &&
		        					Math.abs(dataTrade.getpTradePrice() - dCStick1.getClose() ) < THRESHOLD
		            			){
		                     		System.out.println("First trade ---> Passed");
		                     		firstTradePass= true;
		                     	}else {
		                     		System.out.println("First trade---> Failed");
		                     		firstTradePass = false;
		                     		vFailTradeID.add(dataTrade.getdTradeID());
		                     	}
            				
            				 firstTradeO =dataTrade.getpTradePrice();
            		    	 firstTradeC =dataTrade.getpTradePrice();
            		    	 firstTradeH =dataTrade.getpTradePrice();
            		    	 firstTradeL =dataTrade.getpTradePrice();
            		    	
            			}else {
            				count++;
            				// more than 1 trade
            			  	
            		    	/*
            		    	 *  i.    O is the price of the first trade
            				 *	ii.   C is the price of the last trade
            				 *	iii.  H is the highest price from the 1st trade and 2nd trade
            				 *	iv.   L is the lowest price from the 1st trade and 2nd trade
            		    	 * */
            				lastTradeO = firstTradeO;
            				lastTradeC = dataTrade.getpTradePrice();
            				
            				if (Math.abs(dataTrade.getpTradePrice() -lastTradeH  ) > THRESHOLD) {
            					lastTradeH = dataTrade.getpTradePrice();
            					
            				}
              				if (Math.abs(dataTrade.getpTradePrice() -lastTradeL  ) < THRESHOLD) {
              					lastTradeL = dataTrade.getpTradePrice();
            					
            				}	

              				vtmpTradeID.add(dataTrade.getdTradeID());
            			
            			}
            	 }
             }
           
             
           if(count>=2) {
        	   
        	   System.out.println(
        			   "CandleStick 1: "+
        			   " dCStick1.getOpen(): " + dCStick1.getOpen() +
        			   " dCStick1.getClose(): " + dCStick1.getClose() +
        			   " dCStick1.getHigh(): " + dCStick1.getHigh() +
        			   " dCStick1.getLow(): " + dCStick1.getLow() 
        	  );
        	   System.out.println(
        			   "Final Trade result: "+
        			   " lastTradeO:"+lastTradeO + 
        			   " lastTradeC:"+lastTradeC + 
        			   " lastTradeH:"+lastTradeH + 
        			   " lastTradeL:"+lastTradeL  
        	   );
        	   
        	   if (
        			   Math.abs(dCStick1.getOpen() -lastTradeO  )  <THRESHOLD	   &&
        			   Math.abs(dCStick1.getClose() -lastTradeC  ) < THRESHOLD	   &&
        			   Math.abs(dCStick1.getHigh() -lastTradeH  ) < THRESHOLD	   &&
        			   Math.abs(dCStick1.getLow() -lastTradeL  ) < THRESHOLD	
        			   
        	   ) {
        		 //do nothing
        	   }else {
        		   System.out.println("Trade more 2 failed in this period ");
        		   vFailTradeID.addAll(vtmpTradeID);
        		   
        			
    		       LinkedHashSet<Integer> hashSet = new LinkedHashSet<Integer>(vFailTradeID);
    		       vFailTradeID.clear();
    		       vFailTradeID.addAll(hashSet);
    	  
    				
        	   }
           }
             //reset counter
             count=0;

  	 
    	}
    
        
        
    	return vFailTradeID;
    }
  
    /**
     *      Consideration
     *      
     *      i: Time length definition are different between CandleStick and Trade
     *         a. Candle time use epoch
     *         b. Trade DateTime is epoch ,t: unix timeStamp
     *     
     *      ii. Trade empty while candleStick exist
     *      iii. candleStick empty while Trade exist
     *      iv. Task, not mentioned trade more than 2 
     * 
     * 
     * */
    
    
    public String getJsonObjectString(String instrument, String method) {
    	String jsonResult="";
 
        
  	  try {

  		// URL url = new URL("https://api.crypto.com/v2/public/get-ticker?instrument_name=BTC_USDT");
  		//URL url = new URL("https://api.crypto.com/v2/public/get-candlestick?instrument_name=BTC_USDT&timeframe=5m");
  		URL url = new URL("https://api.crypto.com/v2/public/get-"+method+"?instrument_name="+instrument);

  		//System.out.println("Trade URL: "+url);
  				
  		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
  		conn.setRequestMethod("GET");
  		conn.setRequestProperty("Accept", "application/json");

  		if (conn.getResponseCode() != 200) {
  			throw new RuntimeException("Failed : HTTP error code : "
  					+ conn.getResponseCode());
  		}

  		BufferedReader br = new BufferedReader(new InputStreamReader(
  			(conn.getInputStream())));

  		String output;
  		//System.out.println("Output Trade from Server .... \n");
  		while ((output = br.readLine()) != null) {
  			
  			jsonResult = jsonResult +output;
  			
  		}


  		System.out.println("\n");
  		
  		
  		conn.disconnect();

		
		
  	  } catch (MalformedURLException e) {

  		e.printStackTrace();

  	  } catch (IOException e) {

  		e.printStackTrace();

  	  }

    	return jsonResult;
    	
    }
    
    
    
    
    /**
     * 
     * 
     * 
     * */
    
    
    public String getJsonObjectString(String instrument, String method, String period) {
    	String jsonResult="";
 
        
  	  try {

  		// URL url = new URL("https://api.crypto.com/v2/public/get-ticker?instrument_name=BTC_USDT");
  		//URL url = new URL("https://api.crypto.com/v2/public/get-candlestick?instrument_name=BTC_USDT&timeframe=5m");
  		URL url = new URL("https://api.crypto.com/v2/public/get-"+method+"?instrument_name="+instrument+"&timeframe="+period);

  				
  		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
  		conn.setRequestMethod("GET");
  		conn.setRequestProperty("Accept", "application/json");

  		if (conn.getResponseCode() != 200) {
  			throw new RuntimeException("Failed : HTTP error code : "
  					+ conn.getResponseCode());
  		}

  		BufferedReader br = new BufferedReader(new InputStreamReader(
  			(conn.getInputStream())));

  		String output;
  		//System.out.println("Output from Server .... \n");
  		while ((output = br.readLine()) != null) {
  			
  			jsonResult = jsonResult +output;
  			// System.out.println(output);
  		}


  		System.out.println("\n");
  		
  		conn.disconnect();

		
		
  	  } catch (MalformedURLException e) {

  		e.printStackTrace();

  	  } catch (IOException e) {

  		e.printStackTrace();

  	  }

    	return jsonResult;
    	
    }
    
    
    
    
    
    /**
     * 
     * */
    
    public CandleStick jsonObjectFactoryCS(String method, String jsonString) {
    	
    	CandleStick candleStick = new CandleStick();
    	JsonObject dataObj;
		 long tUnixTimestamp ;
		 float open;
		 float high ;
		 float low ;
		 float close;
		 float volume ;
    	
    	if (ParamAPIMethod.CandleStick.equals(method)){
    		
    		System.out.println("*** Generating CandleStick object");
    		
    		
    		Gson gson = new Gson();
    		
    		//get json object from the json string
    		JsonObject csJsonObject = gson.fromJson(jsonString, JsonObject.class);
    
    			candleStick.setMethod(csJsonObject.get("method").getAsString());
    		//System.out.println("result: "+candleStick.getMethod()+"\n");
    		
    		

    		JsonObject result = csJsonObject.getAsJsonObject("result");
    		
    		candleStick.setInstrumentName(result.get("instrument_name").getAsString());;
    		//System.out.println("candleStick.getInstrumentName(): "+candleStick.getInstrumentName()+"\n");
    		
    		candleStick.setInstrumentName(result.get("interval").getAsString());;
    		//System.out.println("candleStick.getInterval(): "+candleStick.getInterval() + "\n");
    		
    		
    		JsonArray dataArray = result.getAsJsonArray("data");
    		
    		
    		Vector<DataCStick> dataSet= new Vector<>();
    		
    		for (JsonElement d : dataArray) {
    			
    		    dataObj = d.getAsJsonObject();
    			tUnixTimestamp = dataObj.get("t").getAsLong();
    			open = dataObj.get("o").getAsFloat();
    			high = dataObj.get("h").getAsFloat();
    			low = dataObj.get("l").getAsFloat();
    			close = dataObj.get("c").getAsFloat();
    			volume = dataObj.get("v").getAsFloat();
    			 
    			/*
    			 System.out.println(
    					  tUnixTimestamp + " @ " +
    	    			  open + " @ " +
    	    			  high+ " @ " +
    	    			  low + " @ " +
    	    			  close + " @ " +
    	    			  volume + " @ " 
    			 );
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
    		
    		
    		//System.out.println("candleStick.getDataSet().get(0).gettUnixTimestamp: "+candleStick.getDataSet().get(0).gettUnixTimestamp());

    		
    	}

    	return candleStick;
    }
 
    
    
    
    
    /**
     * 
     * */
    
    public Trade jsonObjectFactoryTrade(String method, String jsonString) {
    	
    	Trade trade = new Trade();
    	JsonObject dataObj;

    	 float pTradePrice;
    	 float qTradeQty;
    	 String sSide;
    	 long dTradeID;
    	 long tTradeTimeStamp;
    	 long dateTime;
    	
    	if (ParamAPIMethod.Trades.equals(method)){
    		
    		System.out.println("********** Generating Trade object");
    		
    		
    		Gson gson = new Gson();
    		
    		//get json object from the json string
    		JsonObject tradeJsonObject = gson.fromJson(jsonString, JsonObject.class);
    
    		trade.setMethod(tradeJsonObject.get("method").getAsString());
    		System.out.println("result: "+trade.getMethod()+"\n");
    		
    		

    		JsonObject result = tradeJsonObject.getAsJsonObject("result");
    		
    		trade.setInstrumentName(result.get("instrument_name").getAsString());;
    		//System.out.println("trade.getInstrumentName(): "+trade.getInstrumentName()+"\n");
    	
    		
    		JsonArray dataArray = result.getAsJsonArray("data");
    		
    		
    		Vector<DataTrade> dataSet= new Vector<>();
    		
    		for (JsonElement d : dataArray) {
    		
    			
    	
    			
    			
    		    dataObj = d.getAsJsonObject();
    			
    			pTradePrice = dataObj.get("p").getAsFloat();
    			qTradeQty = dataObj.get("q").getAsFloat();
    			sSide = dataObj.get("s").getAsString();
    			dTradeID = dataObj.get("d").getAsLong();
    			tTradeTimeStamp = dataObj.get("t").getAsLong();
    			dateTime = dataObj.get("dataTime").getAsLong();
    			 
    			/*
    			 System.out.println(
    					 pTradePrice + " @ " +
    							 qTradeQty + " @ " +
    							 sSide+ " @ " +
    							 dTradeID + " @ " +
    							 tTradeTimeStamp + " @ " +
    							 dateTime
    			 );
    			 */
    			
    			 
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
    		
    		
    		//]System.out.println("____________________@$#@&$(*@#&(*$&@#*($&*(@#&*($### : "+   Float.toString(trade.getDataSet().get(0).getpTradePrice())  );

    		
    	}
  		
    	return trade;
    }
}