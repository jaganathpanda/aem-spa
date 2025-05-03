package com.adobe.aem.guides.wknd.spa.react.core.models.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AmountSum {


    public static void main(String[] args) throws JSONException {
        String json = "[{\"amount\": 100}, {\"amount\": 150}, {\"amount\": 50}]";
        int totalSum = sumAmounts(json);
                System.out.println("Total Sum: " + totalSum);  // Output: 300
            }
        
            private static int sumAmounts(String json) throws JSONException {
                int total=0;
             JSONArray jsonArray = new JSONArray(json);
             for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                total += jsonObject.getInt("amount");

             }
             return total;

            }
           
}
