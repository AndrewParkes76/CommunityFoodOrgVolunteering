/**
 * Class CommunityFoodVolunteeringTester
 * Author: Andrew Parkes
 */

import java.util.ArrayList;
import java.io.File;

public class CommunityFoodVolunteeringTester {

    public static void main(String[] args) {
        // File names for community food organizations and volunteers


        String orgsFileName = "data/orgs.txt";
        String volunteersFileName = "data/volunteers.txt";

        File orgFile = new File("data/orgs.txt");
        File volFile = new File("data/volunteers.txt");


        System.out.println("community_food_organizations.txt exists? " + orgFile.exists());
        System.out.println("volunteers.txt exists? " + volFile.exists());
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        System.out.println("orgsFileName exists? " + new File(orgsFileName).exists());
        System.out.println("volunteersFileName exists? " + new File(volunteersFileName).exists());
        // Initialize the VolunteeringManager with data from files
        VolunteeringManager volManager = new VolunteeringManager(orgsFileName, volunteersFileName);

        // Print all volunteers and their matches with organizations
        ArrayList<Volunteer> volunteers = volManager.getVolunteers();
        ArrayList<CommunityFoodOrg> organizations = volManager.getOrgs();

        for (Volunteer volunteer : volunteers) {
            System.out.println("Volunteer: " + volunteer.getFullName());
            ArrayList<CommunityFoodOrg> matches = volManager.findMatches(volunteer);

            System.out.println("Matches:");
            for (CommunityFoodOrg org : matches) {
                System.out.println(" - " + org.getName());
            }
            System.out.println();
        }

        // Launch the GUI with the volunteering manager
        new CommunityFoodOrgVolunteeringGUI(volManager);
        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        // Check if files exist

    }
}