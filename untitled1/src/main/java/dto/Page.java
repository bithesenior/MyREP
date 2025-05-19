package dto;

import anos.FieldXMLElement;
import anos.ObjectXMLElement;

@ObjectXMLElement(name ="page",addMethod = "addPage")
public class Page {
    @FieldXMLElement(name = "value",setMethod = "setValue")
    String value;
    @FieldXMLElement(name = "context",setMethod = "setContext")
    String context;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
