import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static File[] getClasses(){
        File path = new File(Paths.get("src/scripts").toAbsolutePath().toString());
        return path.listFiles();
    }
    public static void input(String string){
        ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
        System.setIn(bais);
    }

    public static void main(String[] args) {
        try{

            File[] rawClasses = getClasses();
            String[] classNames = new String[rawClasses.length];
            for(int i = 0; i < rawClasses.length; i++){
                classNames[i] = rawClasses[i].getName();
            }
            for(String className : classNames){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                //New print stream to capture the output
                PrintStream printStream = new PrintStream(out);
                ByteArrayInputStream in = new ByteArrayInputStream(className.getBytes());


                PrintStream originalOut = System.out;


//                System.setOut(printStream);
                Class<?> clazz = Class.forName("scripts."+className.split("\\.")[0]);
//                Tester tester = new Tester(clazz);
                //Calls the constructor of the new class
                Constructor<?> constructor = clazz.getConstructor();
                //This will get a instance of the object were calling
                Object instance = constructor.newInstance();
                //This will call the method
//                input("Hello World");
                System.out.println("\n\n\n\n");
                System.out.println("Testing: "+className);
                Method binaryMethod = clazz.getMethod("findNumBinary", int[].class , int.class);
                Method linearMethod = clazz.getMethod("findNumLinear", int[].class , int.class);
                System.out.println("___Binary test___");
                testBinary(binaryMethod,instance);
                testBinary(binaryMethod,instance);
                testBinary(binaryMethod,instance);
                System.out.println("___Linear test___");
                testLinear(linearMethod,instance);
                testLinear(linearMethod,instance);
                testLinear(linearMethod,instance);

                Method letterSearch = clazz.getMethod("findByLetter", ArrayList.class , String.class);
                System.out.println("___Letter Search___");
                ArrayList<String> testCase = convert("bravo","alpha","delta","wisky","texico");
                System.out.println("Test Case: "+testCase);
                Object obj = letterSearch.invoke(instance, testCase,"w");
                System.out.println("case: w");
                System.out.println("Result: "+(String)obj);
                System.out.println("Actual: wisky");
                if(((String)obj).equals("wisky")){
                    System.out.println("passed");
                } else
                    System.out.println("Failed");

                obj = letterSearch.invoke(instance, testCase,"d");
                System.out.println("case: d");

                System.out.println("Result: "+(String)obj);
                System.out.println("Actual: delta");
                if(((String)obj).equals("delta")){
                    System.out.println("passed");
                } else
                    System.out.println("Failed");

                obj = letterSearch.invoke(instance, testCase,"z");
                System.out.println("case: z");
                System.out.println("Result: "+(String)obj);
                System.out.println("Actual: null");
                if((obj == null)){
                    System.out.println("passed");
                } else
                    System.out.println("Failed");

                Method findWord = clazz.getMethod("findWord", ArrayList.class , String.class);
                System.out.println("___Word test___");
                testCase.sort(String::compareTo);
                System.out.println("Test Case: "+testCase);
                obj = findWord.invoke(instance, testCase,"wisky");
                System.out.println("case: wisky");
                System.out.println("Result: "+(int)obj);
                System.out.println("Actual: 4");
                if(4==(int)obj){
                    System.out.println("passed");
                } else
                    System.out.println("Failed");

                obj = findWord.invoke(instance, testCase,"delta");
                System.out.println("case: delta");
                System.out.println("Result: "+(int)obj);
                System.out.println("Actual: 2");
                if(2==(int)obj){
                    System.out.println("passed");
                } else
                    System.out.println("Failed");

                obj = findWord.invoke(instance, testCase,"oscar");
                System.out.println("case: oscar");
                System.out.println("Result: "+(int)obj);
                System.out.println("Actual: -1");
                if(-1==(int)obj){
                    System.out.println("passed");
                } else
                    System.out.println("Failed");





                new Tester(clazz,instance);

//                System.setOut(originalOut);




            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void testBinary(Method method,Object instance){
        //Make random list
        ArrayList<Integer> arrayList = new ArrayList<>();
        int length = (int)(Math.random()*50)+1;
        int[] arr = new int[length];
        for(int i = 0; i < length; i++){
            arr[i] = (int)(Math.random()*101);
        }
        Arrays.sort(arr);
        for(int i:arr){
            arrayList.add(i);
        }
        int num;
        if(Math.random()<.5){
            num = (int)(Math.random()*101);
        } else
            num = arr[(int)(Math.random()*arr.length)];
        System.out.println("testCase: "+arrayList);
        System.out.println("Num: "+num);

        boolean passed;
        try {
            System.out.println("result: "+(int)method.invoke(instance,arr,num));
            passed = (int)method.invoke(instance,arr,num)==arrayList.indexOf(num);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Actual: "+arrayList.indexOf(num));
        System.out.println("Passed: "+passed);

    }
    public static void testLinear(Method method,Object instance){
        //Make random list
        ArrayList<Integer> arrayList = new ArrayList<>();
        int length = (int)(Math.random()*50)+1;
        int[] arr = new int[length];
        for(int i = 0; i < length; i++){
            arr[i] = (int)(Math.random()*101);
            arrayList.add(arr[i]);
        }
        int num;
        if(Math.random()<.5){
            num = (int)(Math.random()*101);
        } else
            num = arr[(int)(Math.random()*arr.length)];
        System.out.println("testCase: "+arrayList);
        System.out.println("Num: "+num);
        boolean passed;
        try {
            System.out.println("result: "+(int)method.invoke(instance,arr,num));
            passed = (int)method.invoke(instance,arr,num)==arrayList.indexOf(num);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Actual: "+arrayList.indexOf(num));
        System.out.println("Passed: "+passed);

    }
    public static ArrayList<String> convert(String ... strs){
        ArrayList<String> list = new ArrayList<>();
        for(String s : strs){
            list.add(s);
        }
        return list;
    }

}