package scripts;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Search2 {
    public Search2(){

    }
    public int findNumLinear(int[] arr,int num){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == num){
                return i;
            }
        }
        return -1;
    }
    public static String findByLetter(ArrayList<String> strs, String target){
        AtomicReference<String> s = new AtomicReference<>();
        strs.forEach( (n) -> {if(n.toLowerCase().substring(0,1).equals(target)) s.set(n);});
        return s.get();
    }

    public int findNumBinary(int[] arr,int num){

        int high = arr.length-1;
        int low = 0;
        int mid = (high+low) /2;
        while(low<=high){
//            System.out.println("BFR: "+high+" "+mid+" "+low);
            if(arr[mid] == num)
                return 5;
            if(arr[mid]>num)
                high = mid-1;
            else
                low = mid+1;
//            System.out.println("AFT: "+high+" "+mid+" "+low);
            mid = (high+low) /2;
        }
        return -1;
    }


    public int findWord(ArrayList<String> strs, String target){
        int[] arr = new int[strs.size()];
        for(int i = 0; i < strs.size(); i++){
            arr[i] = strs.get(i).toLowerCase().hashCode();
        }
        return findNumBinary(arr,target.hashCode());
    }
}
