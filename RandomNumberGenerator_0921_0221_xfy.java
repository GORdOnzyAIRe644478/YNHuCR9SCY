// 代码生成时间: 2025-09-21 02:21:49
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Random;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RandomNumberGenerator class is a REST endpoint that generates a random number between 1 and 100.
 */
@Path("/random")
@QuarkusMain
public class RandomNumberGenerator {

    // REST endpoint to generate a random number
    @GET
    @Path("/number")
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateRandomNumber() {
        try {
            // Generate a random number between 1 and 100
            int randomNumber = new Random().nextInt(100) + 1;

            // Return the random number as a response
            return Response.ok(String.valueOf(randomNumber)).build();
        } catch (Exception e) {
            // Handle any exceptions that may occur during the generation of the random number
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error generating random number: " + e.getMessage()).build();
        }
    }
}