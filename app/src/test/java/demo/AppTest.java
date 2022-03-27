/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

import demo.param.ParamAPIMethod;
import demo.param.ParamInstrument;
import demo.param.ParamPeriod;
import jsonObject.CandleStick;
import jsonObject.DataCStick;
import jsonObject.DataTrade;
import jsonObject.Trade;

class AppTest {
	
	/**
	 * 
	 *  Test case for checking CandleStick API JSON records
	 * 
	 */
    @Test void CandleStickAPI_return_validate() {
    	DataCStick dataCstick ;
    	
        MarketDataValidator classUnderTest = new MarketDataValidator();
        
        // retrieve the trade JSON from the Crypto.com API  
        String 	jsonString = classUnderTest.getJsonObjectString(ParamInstrument.BTC_USDT, ParamAPIMethod.CandleStick, ParamPeriod.OneMinutes);
       
        // create problem json to check the CandleStick, if necessary
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
    	
        // Check whether the CandleStick JSON string can create the CandleStick object
        assertNotNull(classUnderTest.jsonObjectFactoryCS(ParamAPIMethod.CandleStick, jsonString), "API should return JSON data");
        
        CandleStick candleStick  = classUnderTest.jsonObjectFactoryCS(ParamAPIMethod.CandleStick, jsonString);
    	
    	
    	//System.out.println("####: jsonString:"+jsonString);
    	
    	Vector<DataCStick> vCStick = candleStick.getDataSet();
    	
    	  // Loop all the CandleStick data and check any empty value from the JSON stream
    	  for (int j = 0; j < vCStick.size(); j++) {
    		  dataCstick = (DataCStick) vCStick.get(j);
    		  
    		  
        	assertNotEquals("", Float.toString(dataCstick.getOpen()).trim());
        	assertNotEquals("", Float.toString(dataCstick.getHigh()).trim());
           	assertNotEquals("", Float.toString(dataCstick.getLow()).trim());
        	assertNotEquals("", Float.toString(dataCstick.getClose()).trim());
     	 
    	  }        
    }
    
    
    /**
     * 
     *  Test case for checking Trade API JSON records
     * 
     */
    @Test void TradeAPI_return_validate() {
    	DataTrade dataTrade ;
    	
        MarketDataValidator classUnderTest = new MarketDataValidator();
        
        // retrieve the trade JSON from the Crypto.com API  
        String 	jsonString = classUnderTest.getJsonObjectString(ParamInstrument.BTC_USDT, ParamAPIMethod.Trades);
       
        // create problem json to check the trades, if necessary
        /*
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
    	
    	*/
    	
        // Check whether the trade JSON string can create the Trade object
        assertNotNull(classUnderTest.jsonObjectFactoryTrade(ParamAPIMethod.Trades, jsonString), "API should return JSON data");
        
    	Trade trade = classUnderTest.jsonObjectFactoryTrade(ParamAPIMethod.Trades, jsonString);
    	
    	
    	//System.out.println("####: jsonString:"+jsonString);
    	
    	Vector<DataTrade> vTrade = trade.getDataSet();
    	
    	  // Loop all the trade data and check any empty value from the JSON stream
    	  for (int j = 0; j < vTrade.size(); j++) {
         	 dataTrade = (DataTrade) vTrade.get(j);
         	 
         	assertNotEquals("", dataTrade.getsSide());
        	assertNotEquals("", Float.toString(dataTrade.getpTradePrice()).trim());
        	assertNotEquals("", Float.toString(dataTrade.getqTradeQty()).trim());
           	assertNotEquals("", Float.toString(dataTrade.getdTradeID()).trim());
        	assertNotEquals("", Float.toString(dataTrade.getDateTime()).trim());
     	 
    	  }        
    }
}
