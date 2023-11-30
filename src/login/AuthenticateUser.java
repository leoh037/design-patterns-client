
package login;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import javax.swing.JOptionPane;

public class AuthenticateUser{
    MainWindow obj = new MainWindow();

    private final static String FILE_PATH = "./userData.json";
    
    public boolean fromJson(String username, String password) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            List<?> userList;
            userList = new ObjectMapper().readValue(new File(FILE_PATH), List.class);

            boolean flag = false;

            for (Object O : userList) {
                User users = mapper.convertValue(O, User.class);
                if (Objects.equals(users.getUsername(), username) && Objects.equals(users.getPassword(), password)) {
                    flag = true;
                    return flag;
                }
            }

            return flag;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

