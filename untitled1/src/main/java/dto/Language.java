package dto;

import anos.FieldXMLElement;
import anos.ObjectXMLElement;


/**
 * @author: oooooooooldbi
 * @date: 2025/4/17 10:05
 * @email: bithesenior@163.com
 */
@ObjectXMLElement(name="language",addMethod = "setLanguage")
public class Language {

    @FieldXMLElement(name = "language",setMethod = "setLanguage")
    String language;
    @FieldXMLElement(name = "i:nil",setMethod = "setiNill")
    String iNill;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getiNill() {
        return iNill;
    }

    public void setiNill(String iNill) {
        this.iNill = iNill;
    }
}
