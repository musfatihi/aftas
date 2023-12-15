package ma.fatihii.aftas.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String formatDate(LocalDate date){
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String generatedDate = date.format(outputFormatter);
        return generatedDate.substring(0,6)+generatedDate.substring(8);
    }
}
