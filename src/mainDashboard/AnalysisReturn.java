package mainDashboard;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AnalysisReturn {

	private Number[][] analysisResult;
	private String analysisName;
	private String[] methodNames;
	private String analysisResultString;

	public void setResult(Number[][] analysisResult, String analysisName, String[] methodNames, String resultString) {
		this.analysisResult = analysisResult;
		this.analysisName = analysisName;
		this.methodNames = methodNames;
		this.analysisResultString = resultString;
	}

	public Number[][] getAnalysisResult() {
		return analysisResult;
	}

	public String getAnalysisName() {
		return analysisName;
	}

	public String[] getMethodNames() {
		return methodNames;
	}

	public String getAnalysisResultString() {
		return analysisResultString;
	}
}
