import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniAlgorithms {
    // palindrome checker (boolean)
    public static boolean palindrome(String word) {
        try {
            word = word.toLowerCase(); // convert to lowercase
            String reverse = new StringBuffer(word).reverse().toString(); // reverse the word with StringBuffer
            if (word.equals(reverse)) return true; // compare the string and if equals return true
        }
        catch (Exception e) { System.out.println(e); } // catch the error

        return false;
    }

    // numeric to roman convertor
    public static String numberToRoman(int num) {
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"}; // set roman style
        String[] numeric = {"1000", "900", "500", "400", "100", "90", "50", "40", "10", "9", "5", "4", "1"}; // set numeric style
        List<String> result = new ArrayList<>(); // set an arraylist for the result
        int numCopy = num; // copy the num
        try {
            int size = Integer.toString(num).length(); // set the length of the number

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < numeric.length; j++) {
                    if (numCopy >= Integer.parseInt(numeric[j])) { // check if number is ge of each item in numeric style
                        result.add(roman[j]); // add the number to result
                        numCopy -= Integer.parseInt(numeric[j]); // decrease the number with the correct numeric style
                        i--; // decrease the counter until the conditions are not met
                        break;
                    }
                }
            }
        }
        catch (Exception e) {System.out.println(e);}
        // convert result to array, convert to string and replace square brackets and comma with nothing. return the string
        return Arrays.toString(result.toArray()).replaceAll("[^a-zA-Z0-9]","");
    }

    // Caesars cipher
    public static String rot13Cipher(String str) {
        try {
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray(); // char array of alphabet to uppercase
            char[] rot13 = "nopqrstuvwxyzabcdefghijklm".toUpperCase().toCharArray();    // char array of rot13 to uppercase
            char[] charSec = str.toUpperCase().toCharArray();                           // char array of input string to uppercase
            ArrayList<Character> decrypted = new ArrayList<Character>();                // arraylist of the result

            for (int i = 0; i < str.length(); i++) {                                    // loop through input (length)
                for (int j = 0; j < rot13.length; j++) {                                // loop through rot13 (length)
                    if (charSec[i] == rot13[j]) {                                       // check if the chars match
                        decrypted.add(alphabet[j]);                                     // add the decipher char to the result arraylist
                        break;
                    } else if (j == rot13.length - 1) { decrypted.add(charSec[i]); }    // if the char is not in rot13 alphabet, add the special char in the result
                }
            }
            // convert result arraylist to string, remove the square brackets and replace the ", " with "". <-- return this
            return decrypted.toString().substring(1, 3 * decrypted.size() - 1).replaceAll(", ", "");
        }
        catch (Exception e) { return e.toString(); }                                    // return any exception
    }

    // phone number validator
    public static boolean phoneValidator(String phoneNumber) {
        // accepted regex pattern: 555-555-5555 | (555)555-5555 | (555) 555-5555 | 555 555 5555 | 5555555555 | 1 555 555 5555
        Pattern pattern = Pattern.compile("^((1|1\\s)?)((\\d{10})|(\\d{3})|(\\(\\d{3}\\)))((-|\\s)?)(\\d{3})((-|\\s)?)(\\d{4})$");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find(); // if it finds a match return true, else false
    }

    // venting machine
    public static HashMap<HashMap<String, String>, HashMap<String, HashMap<String, BigDecimal>>>
    checkCashRegister(Double price, Double cash, HashMap<String, BigDecimal> cid) {
        if (price > cash) { System.out.println("Not enough cash"); return null; } // checking if there is enough money
        // declaring variables and hashmaps
        HashMap<String,String> status = new HashMap<>(); // a hashmap for the status
        HashMap<String,BigDecimal> changeHashMap = new HashMap<>(cid); // a copy of the cid input
        HashMap<String,HashMap<String,BigDecimal>> change = new HashMap<>(); // a hashmap that has the change that we return
        HashMap<HashMap<String, String>, HashMap<String, HashMap<String, BigDecimal>>> result = new HashMap<>(); // a hashmap that combines everything we need to return
        HashMap<String, BigDecimal> changeReturned = new HashMap<>(cid); // a hashmap for the change that we return (copy the cid hashmap)
        changeReturned.replaceAll((k, v) -> new BigDecimal("0.0")); // replace all values with 0 (we need just the ids)

        BigDecimal changeToReturn = new BigDecimal(cash - price);
        BigDecimal totalCashInDrawer = new BigDecimal("0.0"); // initialize a variable for the sum of the cash in the drawer

        while (changeToReturn.compareTo(new BigDecimal(0)) > 0) { // the loop will run until we can return all the change
            BigDecimal tempCopy = changeToReturn; // a local copy of the cash we need to return
            if (changeToReturn.compareTo(new BigDecimal(100)) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("ONE HUNDRED").compareTo(new BigDecimal(100)) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal(100)); changeHashMap.put("ONE HUNDRED", changeHashMap.get("ONE HUNDRED").subtract(new BigDecimal(100))); // subtract from the change and from the drawer
                    changeReturned.put("ONE HUNDRED", changeReturned.get("ONE HUNDRED").add(new BigDecimal(100))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal(20)) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("TWENTY").compareTo(new BigDecimal(20)) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal(20)); changeHashMap.put("TWENTY", changeHashMap.get("TWENTY").subtract(new BigDecimal(20))); // subtract from the change and from the drawer
                    changeReturned.put("TWENTY", changeReturned.get("TWENTY").add(new BigDecimal(20))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal(10)) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("TEN").compareTo(new BigDecimal(10)) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal(10)); changeHashMap.put("TEN", changeHashMap.get("TEN").subtract(new BigDecimal(10))); // subtract from the change and from the drawer
                    changeReturned.put("TEN", changeReturned.get("TEN").add(new BigDecimal(10))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal(5)) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("FIVE").compareTo(new BigDecimal(5)) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal(5)); changeHashMap.put("FIVE", changeHashMap.get("FIVE").subtract(new BigDecimal(5))); // subtract from the change and from the drawer
                    changeReturned.put("FIVE", changeReturned.get("FIVE").add(new BigDecimal(5))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal(1)) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("ONE").compareTo(new BigDecimal(1)) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal(1)); changeHashMap.put("ONE", changeHashMap.get("ONE").subtract(new BigDecimal(1))); // subtract from the change and from the drawer
                    changeReturned.put("ONE", changeReturned.get("ONE").add(new BigDecimal(1))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal("0.25")) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("QUARTER").compareTo(new BigDecimal("0.25")) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal("0.25")); changeHashMap.put("QUARTER", changeHashMap.get("QUARTER").subtract(new BigDecimal("0.25"))); // subtract from the change and from the drawer
                    changeReturned.put("QUARTER", changeReturned.get("QUARTER").add(new BigDecimal("0.25"))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal("0.1")) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("DIME").compareTo(new BigDecimal("0.1")) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal("0.1")); changeHashMap.put("DIME", changeHashMap.get("DIME").subtract(new BigDecimal("0.1"))); // subtract from the change and from the drawer
                    changeReturned.put("DIME", changeReturned.get("DIME").add(new BigDecimal("0.1"))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal("0.05")) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("NICKEL").compareTo(new BigDecimal("0.05")) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal("0.05")); changeHashMap.put("NICKEL", changeHashMap.get("NICKEL").subtract(new BigDecimal("0.05"))); // subtract from the change and from the drawer
                    changeReturned.put("NICKEL", changeReturned.get("NICKEL").add(new BigDecimal("0.05"))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(new BigDecimal("0.01")) >= 0) { // check if the amount of cash is in n category
                if (changeHashMap.get("PENNY").compareTo(new BigDecimal("0.01")) >= 0) { // checking if the drawer has enough money of n type
                    changeToReturn = changeToReturn.subtract(new BigDecimal("0.01")); changeHashMap.put("PENNY", changeHashMap.get("PENNY").subtract(new BigDecimal("0.01"))); // subtract from the change and from the drawer
                    changeReturned.put("PENNY", changeReturned.get("PENNY").add(new BigDecimal("0.01"))); // add the n amount to the change hashmap
                }
            }
            if (changeToReturn.compareTo(tempCopy) == 0) { break; } // if it can't return with different money types break from the loop
        }

        for (BigDecimal v : changeHashMap.values()) { totalCashInDrawer = totalCashInDrawer.add(v); } // get the total change from the hashmap to a double variable

        if (changeToReturn.compareTo(totalCashInDrawer) > 0 || changeToReturn.compareTo(new BigDecimal(0)) < 0) { // if the change is gt the total cash in drawer of if the change can't be returned
            status.put("status", "INSUFFICIENT_FUNDS"); // set status to insufficient funds
            change.put("change", new HashMap<>()); // return change with empty change hashmap
            result.put(status, change); // combine status and change
            return result; // return the result
        } else if (totalCashInDrawer.compareTo(new BigDecimal(0)) == 0) { // if our transaction removes the last cash from the drawer
            status.put("status", "CLOSE"); // set status to close
            change.put("change", cid); // return everything the drawer has, as change
            result.put(status, change); // combine the status and the change
            return result; // return the result
        } else if (totalCashInDrawer.compareTo(new BigDecimal(0)) > 0) { // if we can return the change and there is more in the drawer
            status.put("status", "OPEN"); // set status to open
            change.put("change", changeReturned); // set change with the change amount
            result.put(status, change); // combine status and change
            return result; // return the result
        } else return null; // else return nothing
    }
}
