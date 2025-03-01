package org.microservices34.employeeservice.service.impl;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class ResourceFilterService {
    private final Logger logger = LoggerFactory.getLogger(ResourceFilterService.class);
    private final ObjectMapper objectMapper;

    public ResourceFilterService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Filter list of resources by the specified fields
     *
     * @param resources List of resources to filter
     * @param fields Comma-separated list of field names to include
     * @param resourceClass Class of the resource for type information
     * @return Filtered list of resources
     * @param <T> Type of resource
     */
    public <T> List<T> filterFields(List<T> resources, String fields, Class<T> resourceClass) {
        if (fields == null || fields.isEmpty()) {
            return resources;
        }

        Set<String> requestedFields = parseFields(fields);
        return applyFieldFiltering(resources, requestedFields, resourceClass);
    }

    /**
     * Parse the comma-separated fields parameter into a set of field names
     */
    private Set<String> parseFields(String fields) {
        return Stream.of(fields.split(","))
                .map(String::trim)
                .filter(field -> !field.isEmpty())
                .collect(Collectors.toSet());
    }

    /**
     * Apply field filtering to the list of resources using Jackson filtering
     */
    private <T> List<T> applyFieldFiltering(List<T> resources, Set<String> requestedFields, Class<T> resourceClass) {
        try {
            // Configure Jackson to filter properties based on the requested fields
            objectMapper.setFilterProvider(new SimpleFilterProvider()
                    .addFilter("resourceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(requestedFields)));
            objectMapper.addMixIn(resourceClass, ResourceFilterMixin.class);

            // Serialize and deserialize to apply the filtering
            String filteredJson = objectMapper.writeValueAsString(resources);
            return objectMapper.readValue(
                    filteredJson,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, resourceClass)
            );
        } catch (JsonProcessingException e) {
            logger.error("Error filtering resource fields", e);
            throw new ResourceFilteringException("Failed to filter resource fields", e);
        }
    }

    /**
     * MixIn class to apply the Jackson filter
     */
    @JsonFilter("resourceFilter")
    abstract static class ResourceFilterMixin {
    }

    /**
     * Custom exception for resource filtering errors
     */
    public static class ResourceFilteringException extends RuntimeException {
        public ResourceFilteringException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
