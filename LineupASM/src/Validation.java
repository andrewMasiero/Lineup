public class Validation {

    private String lineEnd;

    public Validation() {
        this.lineEnd = "\n";
    }

    public Validation(String lineEnd) {
        this.lineEnd = lineEnd;
    }

    public String isPresent(String value, String name) {
        String msg = "";
        if (value.isEmpty()) {
            msg = name + " is required." + lineEnd;
        }
        return msg;
    }

    // validate double
    public String isDouble(String value, String name) {
        String msg = "";
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            msg = name + " must be a valid number." + lineEnd;
        }
        return msg;
    }
    
    // validate double within a range
    public String isDouble(String value, String name, int min, int max) {
        String msg = "";
        double d = 0;
        try {
            d = Double.parseDouble(value);
            if (d <= min) {
                msg = name + " must be greater than " + min + "." + lineEnd;
            } else if (d > max) {
                msg = name + " must be less than or equal to " + max + "." + lineEnd;
            }
        } catch (NumberFormatException e) {
            if (value.isEmpty()) {
                msg = name + " is required." + lineEnd;
                msg += name + " must be a valid number." + lineEnd;
            } else {
                msg = name + " must be a valid number." + lineEnd;
            }
        }
        return msg;
    }

    public String isInteger(String value, String name) {
        String msg = "";
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            msg = name + " must be an integer." + lineEnd;
        }
        return msg;
    }
}