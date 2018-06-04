package ie.myinfinity.demo.creditcard.utils;

public class CreditCardsUtils {

    private CreditCardsUtils(){}


    /**
     * Validate credit card number
     * @param creditCard
     * @return
     */
    public static boolean isVCalidateCreditCardNumber(String creditCard) {
        if (creditCard==null)
            return false;
        String str=creditCard.trim();
        if (str.length()<15)
            return false;
        if (!str.matches("^[0-9]{15,16}$"))
            return false;
        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;

        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return true;
        }

        return false;
    }
}
