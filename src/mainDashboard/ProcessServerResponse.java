package mainDashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utility.arrayOperation;


public class ProcessServerResponse {
	
	private String jsonString;
	private JsonObject jsonObject;
	private static AnalysisReturn analysisReturn;
	
	private Number[][] analysisResult;
	private String analysisName;
	private String[] methodNames;
	private String analysisResultString;
	
	public ProcessServerResponse(String jsonString) {
		this.jsonString = jsonString;
	}
	
	private void setFieldValues() {
		unpackJson();
		setAnalysisName();
		setMethodNames();
		setAnalysisReturnString();
		setAnalysisReturn();
	}
	
	public AnalysisReturn getAnalysisReturn() {
		setFieldValues();
		analysisReturn = new AnalysisReturn();
		analysisReturn.setResult(analysisResult, analysisName, methodNames, analysisResultString);
		return analysisReturn;
	}

	private void unpackJson() {
		JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject();
		jsonObject = object;
	}
	
	private void setAnalysisName() {
		analysisName = jsonObject.get("analysisName").getAsString();
	}
	
	private void setMethodNames() {
		JsonArray namesJsonArray = jsonObject.get("methodNames").getAsJsonArray();
		int size = namesJsonArray.size();
		methodNames = new String[size];
		for(int i = 0; i < size; i++) {
			methodNames[i] = namesJsonArray.get(i).getAsString();
		}
	}
	
	private void setAnalysisReturnString() {
		analysisResultString = jsonObject.get("analysisResultString").getAsString();
	}
	
	private void setAnalysisReturn() {
		
		JsonArray yearsArray = jsonObject.get("years").getAsJsonArray();
		int numberOfYears = yearsArray.size();
		
		JsonArray valuesArray = jsonObject.get("analysisData").getAsJsonArray();
		int seriesLenght = valuesArray.size();
		
		analysisResult = new Number[seriesLenght+1][numberOfYears];
		for(int i = 0; i<numberOfYears; i++) {
			analysisResult[0][i] = yearsArray.get(i).getAsInt();
		}
		
		Number[] temporaryArray = new Number[numberOfYears];
		JsonObject jsonObject;
		
		for(int i = 0; i < seriesLenght; i++) {
			jsonObject = valuesArray.get(i).getAsJsonObject();
			
			for(int k = 0; k<numberOfYears; k++) {
				temporaryArray[k] = Float.parseFloat(jsonObject.get(analysisResult[0][k].toString()).toString());
			}
			analysisResult[i+1] = temporaryArray;
		}
	
	}
	
}
