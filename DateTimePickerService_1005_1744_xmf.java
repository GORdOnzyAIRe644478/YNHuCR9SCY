// 代码生成时间: 2025-10-05 17:44:02
package com.yourcompany.yourapp;

import io.quarkus.runtime.Quarkus;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Path("/date-time-picker")
public class DateTimePickerService {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    // Default date-time value for demonstration purposes
    private static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.now();

    /*
     * Returns the current date and time in the specified format.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getCurrentDateTime() {
        Map<String, String> response = new HashMap<>();
        response.put("currentDateTime", DATE_TIME_FORMATTER.format(DEFAULT_DATE_TIME));
        return response;
    }

    /*
     * Returns the formatted date and time based on the input string.
     * If the input string does not match the expected pattern, an error message is returned.
     *
     * @param dateTimeString The input date and time string to be parsed.
     */
    @GET
    @Path("/format/{dateTimeString}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getFormattedDateTime(@PathParam("dateTimeString") String dateTimeString) {
        Map<String, String> response = new HashMap<>();
        try {
            LocalDateTime dateTime = DATE_TIME_FORMATTER.parse(dateTimeString, LocalDateTime::from);
            response.put("formattedDateTime", DATE_TIME_FORMATTER.format(dateTime));
        } catch (DateTimeParseException e) {
            response.put("error", "Invalid date-time format. Please use: 