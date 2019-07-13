import org.yangtao.ge.NettyLogin.Model.User;
import org.junit.Assert;
import org.junit.Test;
import org.yangtao.ge.NettyLogin.Model.UserFactory;

public class test {
    @Test
    public void testing(){
        User user1 = new User("YangtaoGe", "abcd");
        User user2 = new User("TerryGe", "abcd");
        User user3 = new User("GavinGe","abcd");

        UserFactory.addUsers(user1);
        UserFactory.addUsers(user2);
        UserFactory.addUsers(user3);

    }
}
