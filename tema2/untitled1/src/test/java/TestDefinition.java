import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDefinition {
    @Test
    public void testHashCode(){
        Definition def1 = new Definition("aba", "cadabra", 2022, List.of("a", "b"));
        Definition def2 = new Definition("aba", "cadabra", 2022, List.of("a", "b"));
        def2.hashCode();
        assertEquals(5, def1.hashCode());
    }
}
