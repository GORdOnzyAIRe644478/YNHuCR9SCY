// 代码生成时间: 2025-10-03 02:56:22
import io.quarkus.runtime.Quarkus;
    import io.quarkus.runtime.annotations.QuarkusMain;
    import javax.enterprise.context.ApplicationScoped;
    import javax.inject.Inject;
    import javax.sql.DataSource;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    /**
     * Main class for the Database Version Control application.
     * This class uses Quarkus to manage the application's lifecycle and interact with the database.
     */
    @QuarkusMain
    public class DatabaseVersionControl {

        /**
         * Injects the data source configured by Quarkus.
         */
        @Inject
        DataSource dataSource;

        public static void main(String... args) {
            Quarkus.run(DatabaseVersionControl.class, args);
        }

        /**
         * Initializes the database version control.
         * This method creates a table to store database version information if it does not already exist.
         */
        public void initializeDatabaseVersionControl() {
            String createTableSql = "CREATE TABLE IF NOT EXISTS db_version (
" +
                    "  version BIGINT NOT NULL PRIMARY KEY
" +
                    ")";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(createTableSql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error initializing database version control: " + e.getMessage());
                // Handle the exception as needed
            }
        }

        /**
         * Updates the database version.
         * This method increments the version by 1.
         *
         * @param currentVersion The current version of the database.
         */
        public void updateDatabaseVersion(long currentVersion) {
            String updateSql = "INSERT INTO db_version (version) VALUES (?) ON DUPLICATE KEY UPDATE version = version + 1";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setLong(1, currentVersion);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error updating database version: " + e.getMessage());
                // Handle the exception as needed
            }
        }

        /**
         * Retrieves the current database version.
         * This method queries the db_version table to get the current version.
         *
         * @return The current database version, or 0 if the table is empty.
         */
        public long getCurrentDatabaseVersion() {
            String selectSql = "SELECT version FROM db_version ORDER BY version DESC LIMIT 1";

            long version = 0;

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(selectSql);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    version = rs.getLong("version");
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving database version: " + e.getMessage());
                // Handle the exception as needed
            }

            return version;
        }
    }