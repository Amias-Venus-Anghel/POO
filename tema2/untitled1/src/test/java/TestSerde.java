import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestSerde {
    @Test
    public void testToJson(){
        assertEquals("{\"dict\":\"aba\",\"dictType\":\"cadabra\",\"year\":2022,\"text\":[\"a\",\"b\"]}", Serde.toJson());
    }
    @Test
    public void testFromJson(){
        Definition def1 = new Definition("aba", "cadabra", 2022, List.of("a", "b"));
        assertEquals(def1 , Serde.fromJson());
    }
    @Test
    public void testFromJsonFile(){
        Definition def1 = new Definition("aba", "cadabra", 2022, List.of("a", "b"));
        assertEquals(def1, Serde.fromJsonFile("../../../../test.json"));
    }

}
