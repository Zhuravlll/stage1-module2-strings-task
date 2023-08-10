package com.epam.mjc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        if(!signatureString.contains("(") || !signatureString.contains(")")) {
            throw new IllegalArgumentException("Signature string has no args!");
        }else if(signatureString.substring(0, signatureString.indexOf("(")-1).split(" ").length < 2){
            throw new IllegalArgumentException("Signature string has no returnType!");
        }
        var args = signatureString.substring(signatureString.indexOf("(")+1, signatureString.indexOf(")")).split(", ");
        var params = signatureString.substring(0, signatureString.indexOf("(")).split(" ");
        List<MethodSignature.Argument> argumentList = new ArrayList<>();
        if(args.length != 1 && !args[0].isEmpty()) {
            for (var arg : args) {
                String[] splitArg = arg.split(" ");
                argumentList.add(new MethodSignature.Argument(splitArg[0], splitArg[1]));
            }
        }
        MethodSignature methodSignature = new MethodSignature(params[params.length-1], argumentList);
        if(params.length == 2){
            methodSignature.setReturnType(params[0]);
        }else {
            methodSignature.setReturnType(params[1]);
            methodSignature.setAccessModifier(params[0]);
        }
        return methodSignature;
    }
}
