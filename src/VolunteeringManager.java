/**
 * Class VolunteeringManager
 * Author: Andrew Parkes
 * */

import java.util.ArrayList;
public class VolunteeringManager {

    private ArrayList<CommunityFoodOrg> orgs;
    private ArrayList<Volunteer> volunteers;

    public VolunteeringManager(String orgsFileName, String volunteersFileName) {
        this.orgs = DataManager.readCommunityFoodOrgs(orgsFileName);
        this.volunteers = DataManager.readVolunteers(volunteersFileName);
    }

    public ArrayList<CommunityFoodOrg> getOrgs() {
        return orgs;
    }

    public ArrayList<Volunteer> getVolunteers() {
        return volunteers;
    }

    public ArrayList<CommunityFoodOrg> findMatches(Volunteer volunteer) {
        ArrayList<CommunityFoodOrg> matches = new ArrayList<>();

        for (CommunityFoodOrg org : orgs) {
            if (volunteer.matches(org)) {
                matches.add(org);
            }
        }

        // Sort matches by priority (descending)
        matches.sort((org1, org2) -> {
            if (org1 instanceof FoodBank && org2 instanceof FoodBank) {
                FoodBank fb1 = (FoodBank) org1;
                FoodBank fb2 = (FoodBank) org2;
                return Double.compare(fb2.dailyDonationsNeeded(volunteer.getDayAvailable()),
                        fb1.dailyDonationsNeeded(volunteer.getDayAvailable()));
            } else if (org1 instanceof FoodPantry && org2 instanceof FoodPantry) {
                FoodPantry fp1 = (FoodPantry) org1;
                FoodPantry fp2 = (FoodPantry) org2;
                return Integer.compare(fp2.dailyVolunteerSpotsLeft(volunteer.getDayAvailable()),
                        fp1.dailyVolunteerSpotsLeft(volunteer.getDayAvailable()));
            } else if (org1 instanceof FoodBank) {
                return -1; // FoodBank has higher priority than FoodPantry
            } else {
                return 1; // FoodPantry has lower priority
            }
        });

        return matches;
    }
}
