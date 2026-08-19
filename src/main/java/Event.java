public class Event extends Task {

    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString();
    }
}