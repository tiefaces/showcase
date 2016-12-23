package com.tiefaces.components.confirm;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.BehaviorConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

import org.primefaces.behavior.base.AbstractBehaviorHandler;

public class ConfirmBehaviorHandler extends AbstractBehaviorHandler<ConfirmBehavior> {

    private final TagAttribute header;
    private final TagAttribute message;
    private final TagAttribute icon;

    public ConfirmBehaviorHandler(BehaviorConfig config) {
        super(config);
        this.header = this.getAttribute(ConfirmBehavior.PropertyKeys.header.name());
        this.message = this.getAttribute(ConfirmBehavior.PropertyKeys.message.name());
        this.icon = this.getAttribute(ConfirmBehavior.PropertyKeys.icon.name());
    }


	@Override
	protected ConfirmBehavior createBehavior(FaceletContext ctx,
			String eventName, UIComponent parent) {
        Application application = ctx.getFacesContext().getApplication();
        ConfirmBehavior behavior = (ConfirmBehavior) application.createBehavior(ConfirmBehavior.BEHAVIOR_ID);

        setBehaviorAttribute(ctx, behavior, this.header, ConfirmBehavior.PropertyKeys.header.expectedType);
        setBehaviorAttribute(ctx, behavior, this.message, ConfirmBehavior.PropertyKeys.message.expectedType);
        setBehaviorAttribute(ctx, behavior, this.icon, ConfirmBehavior.PropertyKeys.icon.expectedType);

        return behavior;
	}

}