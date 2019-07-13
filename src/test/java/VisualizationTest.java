import org.yangtao.ge.NettyLogin.Model.User;
import org.yangtao.ge.NettyLogin.Model.UserFactory;

import java.util.ArrayList;

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
    }
}
