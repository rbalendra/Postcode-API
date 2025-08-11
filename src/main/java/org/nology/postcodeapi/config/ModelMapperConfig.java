package org.nology.postcodeapi.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Skip mapping for null values — avoids overwriting existing values with null during updates
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        // Apply a converter that trims all incoming/outgoing String fields automatically
        modelMapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        // Spring will inject this wherever ModelMapper is needed
        return modelMapper;
    }

    //      ╭──────────────────────────────────────────────────────────╮
    //      │  Converter implementation that removes leading/trailing  │
    //      │  spaces from Strings                                     │
    //      │  This ensures consistent and clean string values when    │
    //      │  mapping between objects.                                │
    //      ╰──────────────────────────────────────────────────────────╯

    private class StringTrimConverter implements Converter<String, String> {

        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null; // If the source is null, keep it null (avoids NullPointerException)
            }
            return context.getSource().trim(); // Remove leading/trailing spaces
        }

    }
}
