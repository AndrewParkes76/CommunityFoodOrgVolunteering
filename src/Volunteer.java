/**
 * Class Volunteer
 * Author: Andrew Parkes
 */
public class Volunteer {

    private String id;
    private String fullName;
    private int age;
    private Location location;
    private String dayAvailable;
    private TimeFrame timeAvailable;
    private double distanceAvailable;
    private boolean needsTransportation;
    private CommunityFoodOrg orgVolunteering;
    private double donation; // Represents the donation in pounds

    public Volunteer(String id, String fullName, int age, Location location, String dayAvailable, TimeFrame timeAvailable, double distanceAvailable, boolean needsTransportation) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.location = location;
        this.dayAvailable = dayAvailable;
        this.timeAvailable = timeAvailable;
        this.distanceAvailable = distanceAvailable;
        this.needsTransportation = needsTransportation;
        this.orgVolunteering = null; // Default to no organization initially
        this.donation = 0; // Default donation is 0
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public Location getLocation() {
        return location;
    }

    public String getDayAvailable() {
        return dayAvailable;
    }

    public TimeFrame getTimeAvailable() {
        return timeAvailable;
    }

    public double getDistanceAvailable() {
        return distanceAvailable;
    }

    public boolean needsTransportation() {
        return needsTransportation;
    }

    public CommunityFoodOrg getOrgVolunteering() {
        return orgVolunteering;
    }

    public double getDonation() {
        return donation;
    }

    public void setDonation(double donation) {
        if (donation >= 0) {
            this.donation = donation;
        } else {
            System.out.println("Donation cannot be negative.");
        }
    }

    public void signUp(CommunityFoodOrg org) {
        if (org.signUpVolunteer(this)) {
            this.orgVolunteering = org;
        }
    }

    public void cancelSignUp() {
        if (this.orgVolunteering != null) {
            this.orgVolunteering.cancelVolunteerSignup(this.getDayAvailable());
            this.orgVolunteering = null;
        } else {
            System.out.println("No volunteer has signed up yet.");
        }
    }

    public boolean matches(CommunityFoodOrg org) {
        int dayIndex = org.getDayIndex(dayAvailable);
        if (dayIndex < 0) return false;

        TimeFrame orgTimeFrame = org.getDailyOpenHours()[dayIndex];
        if (orgTimeFrame == null || !timeAvailable.overlaps(orgTimeFrame)) {
            return false;
        }

        if (needsTransportation && !org.getOffersTransportation()) {
            return false;
        }

        double distance = location.distanceTo(org.getLocation());
        if (distance > distanceAvailable) {
            return false;
        }

        if (donation > 0) { // Interested in FoodBank
            if (!(org instanceof FoodBank)) {
                return false;
            }
            FoodBank foodBank = (FoodBank) org;
            return donation <= foodBank.dailyDonationsNeeded(dayAvailable);
        } else { // Interested in FoodPantry
            if (!(org instanceof FoodPantry)) {
                return false;
            }
            return true;
        }
    }
}
