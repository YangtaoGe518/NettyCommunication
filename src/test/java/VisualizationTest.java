import org.yangtao.ge.NettyLogin.Model.User;
import org.yangtao.ge.NettyLogin.Model.UserFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class VisualizationTest {
    public static void main(String[] args){
        User user1 = new User("YangtaoGe", "abcd");
        User user2 = new User("TerryGe", "abcd");
        User user3 = new User("GavinGe","abcd");

        UserFactory.addUsers(user1);
        UserFactory.addUsers(user2);
        UserFactory.addUsers(user3);

        ArrayList<User> users = UserFactory.getUsers();

        for(User user : users){
            System.out.println(user);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user name: ");
        String userName = scanner.nextLine();
        System.out.println("Enter the password: ");
        String password = scanner.nextLine();

        User user4 = new User(userName, password);
        UserFactory.addUsers(user4);

        System.out.println("After add a new User");

        for(User user : users){
            System.out.println(user);
        }

    }
}
