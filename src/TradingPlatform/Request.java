package TradingPlatform;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = -2289891509187701177L;

    private String className;
    private String methodName;
    private String[] arguments;
    private Boolean response;
    private String[][] doubleString;

    public Request(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        this.arguments = null;
        this.doubleString = null;
        this.response = false;
    }

    public Request(String className, String methodName, Boolean response) {
        this(className, methodName);
        this.response = response;
    }

    public Request(String className, String methodName, String[] arguments) {
        this(className, methodName);
        this.arguments = arguments;
    }

    public Request(String className, String methodName, String[][] doubleString) {
        this(className, methodName);
        this.doubleString = doubleString;
    }

    public Request(String className, String methodName, String[] arguments, Boolean response) {
        this(className, methodName, arguments);
        this.response = response;
    }

    public void setDoubleString(String[][] doubleString) { this.doubleString = doubleString; }

    public String getClassName() { return className; }
    public String getMethodName() { return methodName; }
    public String[] getArguments() { return  arguments; }
    public Boolean getResponse() { return  response; }
    public String[][] getDoubleString() { return  doubleString; }
}
