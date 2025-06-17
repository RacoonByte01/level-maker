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

/**
 * class DTOUtils
 * Esta clase se encarga de alvergar ciertos metodos que son utiles par el uso
 * de los DTOs
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class DTOUtils {
    /**
     * Este metodo se encarga de crear un String apartir de un objeto Serializable
     * 
     * @param o
     * @return string
     * @throws IOException
     */
    public static String objToString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Este metodo se encarga de crear un objeto apartir de un String
     * 
     * @param s
     * @return Onject
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object stringToObj(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Este metodo devulve la fecha actual
     * 
     * @return Date
     */
    public static String getActualDay() {
        // Se obtiene la hora actual
        Date current = new Date();
        // Se crea un formato para la fecha y la hora
        SimpleDateFormat objSDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return objSDF.format(current);
    }

    /**
     * Este metodo hace un hash de un string en MD5
     * 
     * @param input
     * @return
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
