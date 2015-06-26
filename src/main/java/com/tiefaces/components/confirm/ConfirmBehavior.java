package com.tiefaces.components.confirm;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;

import org.primefaces.behavior.base.AbstractBehavior;
import org.primefaces.component.api.Confirmable;
import org.primefaces.json.JSONObject;
@FacesBehavior("tie.ConfirmBehavior")
public class ConfirmBehavior extends AbstractBehavior {

    public final static String BEHAVIOR_ID = "tie.ConfirmBehavior";

    @Override
    public String getScript(ClientBehaviorContext behaviorContext) {
        FacesContext context = behaviorContext.getFacesContext();
        UIComponent component = behaviorContext.getComponent();
        String source = component.getClientId(context);
        String headerText = JSONObject.quote(this.getHeader());
        String messageText = JSONObject.quote(this.getMessage());

        if (component instanceof Confirmable) {
            String script = "PrimeFaces.confirm({source:\"" + source + "\",header:" + headerText + ",message:"
                    + messageText + ",icon:\"" + getIcon() + "\"});return false;";
            ((Confirmable) component).setConfirmationScript(script);

            return null;
        } else {
            throw new FacesException("Component " + source + " is not a Confirmable. ConfirmBehavior can only be "
                    + "attached to components that implement org.primefaces.component.api.Confirmable interface");
        }
    }

    public String getHeader() {
        return eval(PropertyKeys.header, null);
    }

    public void setHeader(String header) {
        setLiteral(PropertyKeys.header, header);
    }

    public String getMessage() {
        return eval(PropertyKeys.message, null);
    }

    public void setMessage(String message) {
        setLiteral(PropertyKeys.message, message);
    }

    public String getIcon() {
        return eval(PropertyKeys.icon, null);
    }

    public void setIcon(String icon) {
        setLiteral(PropertyKeys.icon, icon);
    }

    public enum PropertyKeys {
        header(String.class), message(String.class), icon(String.class);

        final Class<?> expectedType;

        PropertyKeys(Class<?> expectedType) {
            this.expectedType = expectedType;
        }
    }

    @Override
    protected Enum<?>[] getAllProperties() {
        return PropertyKeys.values();
    }

}