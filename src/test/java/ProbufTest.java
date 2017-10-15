import com.githup.bigminions.probuf.AddressBookProtos;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

/**
 * Created by daren on 12/31/2016.
 */
public class ProbufTest {

    @Test
    public void testCreate() {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder().setId(1000).setEamil("123@qq.com").setName("test").addPhone(
                AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("123123123")
        ).build();
        System.out.println(person.toString());
        byte[] bytes = person.toByteArray();
        try {
            AddressBookProtos.Person turn = AddressBookProtos.Person.parseFrom(bytes);
            System.out.println(turn);
        } catch (InvalidProtocolBufferException e) { 
            e.printStackTrace();
        }
    }
}
