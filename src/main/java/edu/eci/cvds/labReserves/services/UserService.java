package edu.eci.cvds.labReserves.services;
import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.*;
import edu.eci.cvds.labReserves.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The {@code UserService} class provides business logic for managing reserves within the application.
 * It handles operations related to User, including creating, updating, deleting, and retrieving.
 */
@Service
public class UserService{
    @Autowired
    private UserMongoRepository userRepo;


    /**
     * Save a user
     * @param user
     * @return the saved user
     */
    public UserMongodb createUser(User user) throws LabReserveException {
        try{
            UserMongodb userMongo = new UserMongodb(user);
            return userRepo.save(userMongo);
    
        } catch(Exception e){
            e.printStackTrace();
            throw new LabReserveException("Error al crear el usuario: " + e.getMessage());
        }
    }

    /**
     * Search a user by id
     * @param user
     * @return the user that owns that
     */
    public Optional<User> findUserById(int user){
        return Optional.ofNullable(userRepo.findById(user));
    }

    /**
     * Search a user by mail
     * @param email
     * @return the user or null
     */
    public Optional<User> findUserByMail(String email){
        return Optional.ofNullable(userRepo.findByMail(email));
    }

    /**
     * Search a user by name
     * @param name
     * @return the user or null
     */
    public Optional<User> findUserByName(String name){
        return Optional.ofNullable(userRepo.findByName(name));
    }



    /**
     * Change the password for a new one
     * @param newPassword
     * @param user
     * @return
     */
    public User changeUserPassword(String newPassword, User user) throws LabReserveException {
        String cleanPassword = newPassword.trim().replaceAll("\\s+", "");
        user.setPassword(cleanPassword);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Change the mail for a new one
     * @param newMail
     * @param user
     * @return
     */
    public User changeUserMail(String newMail, User user) throws LabReserveException {
        user.setMail(newMail);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Change the name for a new one
     * @param newName
     * @param user
     * @return
     */
    public User changeUserName(String newName, User user) throws LabReserveException {
        user.setMail(newName);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * Change the rol for a new one
     * @param newRol
     * @param user
     * @return
     */
    public User changeUserRol(String newRol, User user) throws LabReserveException {
        user.setRol(newRol);
        UserMongodb userMongodb = new UserMongodb(user);
        return userRepo.save(userMongodb);
    }

    /**
     * delete a user
     * @param user
     *
     */
    public void deleteUser(User user) throws LabReserveException {
        userRepo.deleteById(user.getId());
    }










}