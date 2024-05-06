package service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Convertions {

    public static Double toDoubleTwoDecimals(Double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat decimal = new DecimalFormat("#.##", symbols);
        return Double.parseDouble(decimal.format(number));
    }

    public static Double bytesParaGb(Double number) {
        return number / 1073741824;
    }

    public static Double hzParaGhz(Double number) {
        return number / 1000000000;
    }
}
