package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.entities.Train;
import org.example.entities.User;
import org.example.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER_PATH = "app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public List<User> loadUsers() throws IOException {
        File users = new File(USER_PATH);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> user1.getName().equalsIgnoreCase(user.getName())
                && UserServiceUtil.checkPassword(user.getPassword(), user1.getPassword())).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File userFile = new File(USER_PATH);
        objectMapper.writeValue(userFile, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public List<Train> getTrains(String source, String destination){
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrain(source, destination);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

}
