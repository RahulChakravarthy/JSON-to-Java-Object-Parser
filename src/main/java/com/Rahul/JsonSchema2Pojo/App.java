package com.Rahul.JsonSchema2Pojo;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Application : This application parses JSON raw data and creates a file outlining the equivalent java object structure
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        String destinationUrl = args[0];
        String className = args[1];
        String packageName = args[2];

        JCodeModel codeModel = new JCodeModel();
        URL source = new URL(destinationUrl);
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }
            public SourceType getSourceType(){ //Passing in Json Data
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, className, packageName, source);
        codeModel.build(new File("src/main/java/"));
    }
}
