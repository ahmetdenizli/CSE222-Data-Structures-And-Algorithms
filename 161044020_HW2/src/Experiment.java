public class Experiment {
    public String setup;
    public int day;
    public String time;
    public boolean completed;
    public float accuracy;
    public Experiment next;

    /**
     * Creates a new node with a null next field
     * @param setup explains the experimental setup
     * @param day  Represents the day of start
     * @param time Represents the time of start
     * @param completed Indicates whether it is completed or not
     * @param accuracy Represents the output (not a valid value if the experiment is not completed)
     */
    public Experiment(String setup, int day, String time, boolean completed, float accuracy) {
        this.setup = setup;
        this.day = day;
        this.time = time;
        this.completed = completed;
        this.accuracy = accuracy;
        next = null;
    }

    /**
     * Creates a new node with a null next field
     * @param setup explains the experimental setup
     * @param day  Represents the day of start
     * @param time Represents the time of start
     * @param completed Indicates whether it is completed or not
     * @param accuracy Represents the output (not a valid value if the experiment is not completed)
     */
    public Experiment(String setup, String time, boolean completed, int day, float accuracy) {
        this.setup = setup;
        this.day = day;
        this.time = time;
        this.completed = completed;
        this.accuracy = accuracy;
        next = null;
    }

    /**
     * Constructor: Creates a new node that references another node
     * @param setup explains the experimental setup
     * @param day  Represents the day of start
     * @param time Represents the time of start
     * @param completed Indicates whether it is completed or not
     * @param accuracy Represents the output (not a valid value if the experiment is not completed)
     * @param nodeRef Reference for another node
     */
    public Experiment(String setup, int day, String time, boolean completed, float accuracy, Experiment nodeRef) {
        this.setup = setup;
        this.day = day;
        this.time = time;
        this.completed = completed;
        this.accuracy = accuracy;
        next = nodeRef;
    }

    /**
     * Creates a new node that copies fields from another node
     * @param another Node for copy
     */
    public Experiment (Experiment another){
        this.setup = another.setup;
        this.day = another.day;
        this.time = another.time;
        this.completed = another.completed;
        this.accuracy = another.accuracy;
        next = null;
    }

    /**
     * Method for objects that copies fields from another node
     * @param another Node for copy
     */
    public void copy(Experiment another){
        this.setup = another.setup;
        this.day = another.day;
        this.time = another.time;
        this.completed = another.completed;
        this.accuracy = another.accuracy;
    }

    /**
     * Override of toString method
     */
    @Override
    public String toString() {
        return "Experiment{" +
                "setup='" + setup + '\'' +
                ", day=" + day +
                ", time='" + time + '\'' +
                ", accuracy=" + accuracy +
                ", completed=" + completed +
                '}';
    }

    /**
     * Get method for day
     * @return Value of day
     */
    public int getDay() {
        return day;
    }

}
