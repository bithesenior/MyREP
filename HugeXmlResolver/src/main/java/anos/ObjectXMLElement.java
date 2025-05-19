package anos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ObjectXMLElement {
    String name() default "##default";
    
    //在父节点上添加子节点的方法
    String addMethod() default "##default";
    
    //如果此节点为根节点，此节点解析完成时调用的方法，默认为加入resultList
    String rootMethod() default "##default";
}
