package db.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTOUtils {
    /**
     * Write the object to a Base64 string.
     * 
     * @param o
     * @return
     * @throws IOException
     */
    public static String toString(Serializable o) throws IOException {
        // If we dont use ByteArrayOutputStream we must to use BLOB in the db
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Read the object from Base64 string.
     * 
     * @param s
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object fromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * This function return the actual day
     * 
     * @return Date
     */
    public static String getActualDay() {
        // Get the actual day
        Date current = new Date();
        // And set the format day/month/year hour:minute
        // Example format 18/04/2024 23:39
        SimpleDateFormat objSDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return objSDF.format(current);
    }

    /**
     * This function return MD5 of String value
     * 
     * @param input The String to get the MD5
     * @return the String in MD5
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
