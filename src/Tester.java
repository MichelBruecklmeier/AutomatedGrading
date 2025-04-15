import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Tester {
    Class<?> clazz;
    private final static PrintStream ORIGINAL_OUT = System.out;
    ByteArrayOutputStream captureOut = new ByteArrayOutputStream();
    Method[] methods;
    Object instance;
    public Tester(Class<?> clazz, Object instance) {
        this.clazz = clazz;
        this.instance = instance;
        methods = getChildMethods(clazz.getMethods());
    }

    private Method[] getChildMethods(Method[] methods){
        //We are doing this because the clazz.getMethods() method gets inhertied methods aswell wich we dont want if we are testing one thing
        //Probably going to add a true or false state disabling and enabling it
        Method[] newMethods = new Method[0];
        for(int i = 0; i < methods.length; i++){
            if(methods[i].getDeclaringClass() == clazz){
                newMethods = Arrays.copyOf(newMethods, newMethods.length + 1);
                newMethods[i] = methods[i];
            } else
                break;
        }
        return newMethods;

    }

    public void addMethods(Method ... method){
        Method[] newMethods = Arrays.copyOf(method, methods.length+method.length);
        int itt=0;
        for(int i = methods.length; i < newMethods.length; i++){
            newMethods[i] = methods[itt];
            itt++;
        }
    }

    public void captureOutput(){
        captureOut.reset();
        System.setOut(new PrintStream(captureOut));
    }

    public String getCapture(){
        System.setOut(ORIGINAL_OUT);
        return captureOut.toString();
    }

    public Method findMethod(String name){
        Method method;
        for(Method m : methods){
            if(m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }

    public Object testMethod(String methodName, Object ... obj) throws InvocationTargetException, IllegalAccessException {
        //Find the method we want
        Method method = findMethod(methodName);
        if(method == null)
            return null;
        //This needs to be done because of the varargs
        captureOutput();
        Object result = switch(obj.length){
            case 1 -> method.invoke(instance, obj[0]);
            case 2 -> method.invoke(instance, obj[0], obj[1]);
            case 3 -> method.invoke(instance, obj[0], obj[1], obj[2]);
            case 4 -> method.invoke(instance, obj[0], obj[1], obj[2], obj[3]);
            case 5 -> method.invoke(instance, obj[0], obj[1], obj[2], obj[3], obj[4]);
            case 6 -> method.invoke(instance, obj[0], obj[1], obj[2], obj[3], obj[4],obj[5]);
            default -> method.invoke(instance);
        };

        if(result == null)
            return getCapture();
        return result;
    }

}
