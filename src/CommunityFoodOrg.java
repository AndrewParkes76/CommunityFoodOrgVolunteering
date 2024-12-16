/**
 * Class CommunityFoodOrg
 * author: Andrew Parkes
 */

public class CommunityFoodOrg {
    //attribute definitions
    private String id;
    private String name;
    private Location location;
    private TimeFrame[] dailyOpenHours;
    private int[] dailyVolunteersNeeded;
    private int[] dailyVolunteerSignups;
    private boolean offersTransportation;

    //Constructor definitions
    public CommunityFoodOrg(String id, String name, Location loc, TimeFrame[] dailyOH, int[] dailyVN, int[] dailyVS, boolean offersT) {
        this.id = id;
        this.name = name;
        this.location = loc;
        this.dailyOpenHours = dailyOH != null && dailyOH.length == 7 ? dailyOH : new TimeFrame[7];
        this.dailyVolunteersNeeded = validDailyArray(dailyVN) ? dailyVN : new int[7];
        this.dailyVolunteerSignups = validDailyArray(dailyVS) ? dailyVS : new int[7];
        this.offersTransportation = offersT;
    }

    //Overloaded for food pantry
    public CommunityFoodOrg(String id, String name, Location loc, TimeFrame[] dailyOH, boolean offersT) {
        this.id = id;
        this.name = name;
        this.location = loc;
        this.dailyOpenHours = dailyOH != null && dailyOH.length == 7 ? dailyOH : new TimeFrame[7];
        this.offersTransportation = offersT;
    }

    //Overloaded for FoodBank
    public CommunityFoodOrg(String id, String name, Location loc, TimeFrame[] dailyOH) {
        this.id = id;
        this.name = name;
        this.location = loc;
        this.dailyOpenHours = dailyOH != null && dailyOH.length == 7 ? dailyOH : new TimeFrame[7];
    }


    private boolean validDailyArray(int[] array) {
        if (array == null || array.length != 7) return false;
        for (int value : array) {
            if (value < 0) return false;
        }
        return true;
    }

    //Define setters and getters
    //Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public TimeFrame[] getDailyOpenHours() {
        return dailyOpenHours;
    }

    public int[] getDailyVolunteersNeeded() {
        return dailyVolunteersNeeded;
    }

    public int[] getDailyVolunteerSignups() {
        return dailyVolunteerSignups;
    }

    public boolean getOffersTransportation() {
        return offersTransportation;
    }


    //Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDailyOpenHours(TimeFrame time, String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0) this.dailyOpenHours[index] = time;
    }

    public void setDailyOpenHours(TimeFrame[] times) {
        if (times != null && times.length == 7) this.dailyOpenHours = times;
    }

    public void setDailyVolunteersNeeded(int volunteers, String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0 && volunteers >= 0) this.dailyVolunteersNeeded[index] = volunteers;
    }

    public void setDailyVolunteersNeeded(int[] volunteers) {
        if (validDailyArray(volunteers)) this.dailyVolunteersNeeded = volunteers;
    }

    public void setDailyVolunteerSignups(int signups, String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0 && signups >= 0) this.dailyVolunteerSignups[index] = signups;
    }

    public void setDailyVolunteerSignups(int[] signups) {
        if (validDailyArray(signups)) this.dailyVolunteerSignups = signups;
    }

    public int getDayIndex(String dayName) {
        switch (dayName.toLowerCase()) {
            case "monday": return 0;
            case "tuesday": return 1;
            case "wednesday": return 2;
            case "thursday": return 3;
            case "friday": return 4;
            case "saturday": return 5;
            case "sunday": return 6;
            default: return -1;
        }
    }

    public static int getStaticDayIndex(String dayName) {
        switch (dayName.toLowerCase()) {
            case "monday": return 0;
            case "tuesday": return 1;
            case "wednesday": return 2;
            case "thursday": return 3;
            case "friday": return 4;
            case "saturday": return 5;
            case "sunday": return 6;
            default: return -1; // Invalid day
        }
    }

    //Methods definition
    public int dailyVolunteerSpotsLeft(String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0) {
            return dailyVolunteersNeeded[index] - dailyVolunteerSignups[index];
        }
        return -1;
    }

    public boolean signUpVolunteer(Volunteer volunteer) {
        int index = getDayIndex(volunteer.getDayAvailable());
        if (index >= 0 && dailyVolunteerSignups[index] < dailyVolunteersNeeded[index]) {
            dailyVolunteerSignups[index]++;
            return true;
        }
        return false; // Sign-up not allowed if quota is reached
    }

    public void cancelVolunteerSignup(String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0 && dailyVolunteerSignups[index] > 0) {
            dailyVolunteerSignups[index]--;
        } else {
            System.out.println("No volunteer has signed up yet.");
        }
    }
}