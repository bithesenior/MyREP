package dto;

import anos.FieldXMLElement;
import anos.ObjectXMLElement;


@ObjectXMLElement(addMethod = "setHolder")
public class Holder {

    @FieldXMLElement(name = "name",setMethod = "setName")
    String name;

    @FieldXMLElement(name = "holder",setMethod = "setComment")
    String comment ;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
