package jarexample;

public class StringUtil {
    public String reverse(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }
    
    public String toUpperCase(String input) {
        return input != null ? input.toUpperCase() : null;
    }
    
    public String toLowerCase(String input) {
        return input != null ? input.toLowerCase() : null;
    }
    
    public boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }
        String cleaned = input.replaceAll("\\s+", "").toLowerCase();
        return cleaned.equals(reverse(cleaned));
    }
}