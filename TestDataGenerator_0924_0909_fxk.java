// 代码生成时间: 2025-09-24 09:09:18
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

/**
 * TestDataGenerator is a Quarkus application that generates test data.
 * It provides a REST endpoint to generate random strings.
 */
@Path("/test-data")
@QuarkusMain
public class TestDataGenerator implements QuarkusApplication {

    private static final Random random = new Random();

    // Generates a random string with a specified length
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    // REST endpoint to generate a random string with a default length
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/random-string")
    public String generateRandomString() {
        return "Random String: " + generateRandomString(10);
    }

    // The main method for the application
    @Override
    public int run(String... args) {
        System.out.println("Starting TestDataGenerator...");
        // Additional startup logic can be added here
        return 0;
    }

    // Additional REST endpoints can be added below
    // ...

    /**
     * Adds a role check to the endpoint.
     * @return A string indicating the user's role.
     */
    @GET
    @Path("/role-check")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("test-role")
    public String roleCheck() {
        return "Access granted to test-role";
    }
}
