import java.util.*;
import java.io.*;

/**
 * DataManager Class
 * Author Andrew Parkes
 */
public class DataManager {

    public static ArrayList<CommunityFoodOrg> readCommunityFoodOrgs(String fileName) {
        ArrayList<CommunityFoodOrg> orgs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    String type = parts[0]; // "Food Bank" or "Food Pantry"
                    String id = parts[1];
                    String name = parts[2];
                    double latitude = Double.parseDouble(parts[3]);
                    double longitude = Double.parseDouble(parts[4]);
                    String address = parts[5];
                    String city = parts[6];
                    String state = parts[7];
                    String zipCode = parts[8];
                    boolean offersTransportation = parts[9].equalsIgnoreCase("yes");
                    Location location = new Location(latitude, longitude, address, city, state, zipCode);

                    if (type.equals("Food Pantry")) {
                        int[] dailyVolunteersNeeded = new int[7];
                        TimeFrame[] dailyOpenHours = new TimeFrame[7];
                        int[] dailyVolunteerSignups = new int[7];

                        for (int i = 10; i < parts.length; i++) {
                            String[] dayInfo = parts[i].split("@");
                            if (dayInfo.length != 4) {
                                System.out.println("Invalid format for day info: " + Arrays.toString(dayInfo));
                                continue;
                            }
                            String dayName = dayInfo[0];
                            String[] start = dayInfo[1].split(":");
                            String[] end = dayInfo[2].split(":");
                            int volunteersNeeded = Integer.parseInt(dayInfo[3]);

                            int dayIndex = CommunityFoodOrg.getStaticDayIndex(dayName);
                            dailyOpenHours[dayIndex] = new TimeFrame(
                                    Integer.parseInt(start[0]), Integer.parseInt(start[1]),
                                    Integer.parseInt(end[0]), Integer.parseInt(end[1])
                            );
                            dailyVolunteersNeeded[dayIndex] = volunteersNeeded;
                        }

                        orgs.add(new FoodPantry(id, name, location, dailyOpenHours, dailyVolunteersNeeded, dailyVolunteerSignups, offersTransportation));
                    } else if (type.equals("Food Bank")) {
                        double maxCapacity = Double.parseDouble(parts[10]);
                        TimeFrame[] dailyOpenHours = new TimeFrame[7];
                        double[] dailyDonationsNeeded = new double[7];
                        Arrays.fill(dailyDonationsNeeded, maxCapacity);

                        for (int i = 11; i < parts.length; i++) {
                            String[] dayInfo = parts[i].split("@");
                            if (dayInfo.length != 3) {
                                System.out.println("Invalid format for day info: " + Arrays.toString(dayInfo));
                                continue;
                            }
                            String dayName = dayInfo[0];
                            String[] start = dayInfo[1].split(":");
                            String[] end = dayInfo[2].split(":");

                            int dayIndex = CommunityFoodOrg.getStaticDayIndex(dayName);
                            dailyOpenHours[dayIndex] = new TimeFrame(
                                    Integer.parseInt(start[0]), Integer.parseInt(start[1]),
                                    Integer.parseInt(end[0]), Integer.parseInt(end[1])
                            );
                        }

                        orgs.add(new FoodBank(id, name, location, dailyOpenHours, maxCapacity, dailyDonationsNeeded));
                    }
                } catch (Exception e) {
                    System.out.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orgs;
    }

    /**
     * Reads volunteer data from a file and parses them into objects.
     * @param fileName The path to the file containing volunteer data.
     * @return An ArrayList of Volunteer objects.
     */
    public static ArrayList<Volunteer> readVolunteers(String fileName) {
        ArrayList<Volunteer> volunteers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 16) {
                        System.out.println("Invalid line format: " + line);
                        continue;
                    }
                    String id = parts[0];
                    String lastName = parts[1];
                    String firstName = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    double latitude = Double.parseDouble(parts[4]);
                    double longitude = Double.parseDouble(parts[5]);
                    String address = parts[6];
                    String city = parts[7];
                    String state = parts[8];
                    String zipCode = parts[9];
                    String dayAvailable = parts[10];
                    String[] start = parts[11].split(":");
                    String[] end = parts[12].split(":");
                    double distanceAvailable = Double.parseDouble(parts[13]);
                    boolean needsTransportation = parts[14].equalsIgnoreCase("yes");
                    double donation = Double.parseDouble(parts[15]);

                    Location location = new Location(latitude, longitude, address, city, state, zipCode);
                    TimeFrame timeAvailable = new TimeFrame(
                            Integer.parseInt(start[0]), Integer.parseInt(start[1]),
                            Integer.parseInt(end[0]), Integer.parseInt(end[1])
                    );

                    Volunteer volunteer = new Volunteer(id, firstName + " " + lastName, age, location, dayAvailable, timeAvailable, distanceAvailable, needsTransportation);
                    volunteer.setDonation(donation);
                    volunteers.add(volunteer);
                } catch (Exception e) {
                    System.out.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return volunteers;
    }
}
