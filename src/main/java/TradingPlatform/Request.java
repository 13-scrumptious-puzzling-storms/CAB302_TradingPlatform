package TradingPlatform;

import java.io.Serializable;

/**
 * Serialisable object used for communication between Client and Server.
 */
public class Request implements Serializable {
    private static final long serialVersionUID = -2289891509187701177L;

    private String className;
    private String methodName;
    private String[] arguments;
    private Boolean response;
    private String[][] doubleString;

    /**
     * Constructor initializes the Request object and sets the
     * desired class and method for the server to access.
     *
     * @param className the class for server to access.
     * @param methodName the method of className for server to use.
     */
    public Request(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        this.arguments = null;
        this.doubleString = null;
        this.response = false;
    }

    /**
     * Constructor sets the Boolean response, indicating to the
     * server whether the Client requires a reply.
     *
     * @param className the class for server to access.
     * @param methodName the method of className for server to use.
     * @param response whether the Client needs a response.
     */
    public Request(String className, String methodName, Boolean response) {
        this(className, methodName);
        this.response = response;
    }

    /**
     * Constructor sets the String[][] value, this is a special type of
     * variable that is only used in certain cases.
     *
     * @param className the class for server to access.
     * @param methodName the method of className for server to use.
     * @param doubleString used for sending or receiving String[][] data.
     */
    public Request(String className, String methodName, String[][] doubleString) {
        this(className, methodName);
        this.doubleString = doubleString;
    }

    /**
     * Constructor sets the String[] arguments, these are the arguments that
     * the server will pass into the method methodName of the class className.
     *
     * @param className the class for server to access.
     * @param methodName the method of className for server to use.
     * @param arguments the arguments of methodName
     */
    public Request(String className, String methodName, String[] arguments) {
        this(className, methodName);
        this.arguments = arguments;
    }

    /**
     * Identical to a previous constructor except with the setting of
     * the response Boolean to true. By default response is false.
     *
     * @param className the class for server to access.
     * @param methodName the method of className for server to use.
     * @param arguments the arguments of methodName
     * @param response whether the Client needs a response.
     */
    public Request(String className, String methodName, String[] arguments, Boolean response) {
        this(className, methodName, arguments);
        this.response = response;
    }

    /**
     * Returns the className of the Request object.
     * @return className.
     */
    public String getClassName() { return className; }

    /**
     * Returns the methodName of the Request object.
     * @return methodName.
     */
    public String getMethodName() { return methodName; }

    /**
     * Returns the arguments of the Request object.
     * @return arguments.
     */
    public String[] getArguments() { return  arguments; }

    /**
     * Returns the value of response.
     * @return response.
     */
    public Boolean getResponse() { return  response; }

    /**
     * Returns the doubleString.
     * @return doubleString.
     */
    public String[][] getDoubleString() { return  doubleString; }
}
