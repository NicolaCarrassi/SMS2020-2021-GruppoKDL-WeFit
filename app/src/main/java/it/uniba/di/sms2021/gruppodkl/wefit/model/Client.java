package it.uniba.di.sms2021.gruppodkl.wefit.model;


public class Client extends User {

    public interface ClientKeys extends UserKeys {
        String HEIGHT = "height";
        String WEIGHT = "weight";
        String OBJECTIVE = "objective";
        String COACH = "coach";
        String HAS_PENDING_REQUESTS = "pendingRequests";
        String WEIGHT_DATE = "weightDate";
    }

    public float weight;
    public int height;
    public String objective;
    public String coach = null;
    public  boolean pendingRequests;

    public Client(){
        super();
    }

    public Client(String fullName, String email, String birthDate, String gender,String role,
                  int height, float weight, String objective, boolean pendingRequests){
        super(fullName, email, birthDate, gender, role);
        this.height = height;
        this.weight = weight;
        this.objective = objective;
        this.pendingRequests = pendingRequests;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}
